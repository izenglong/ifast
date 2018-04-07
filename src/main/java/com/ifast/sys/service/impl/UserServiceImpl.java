package com.ifast.sys.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ifast.common.domain.Tree;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.BuildTree;
import com.ifast.common.utils.FileType;
import com.ifast.common.utils.FileUtil;
import com.ifast.common.utils.ImageUtils;
import com.ifast.common.utils.MD5Utils;
import com.ifast.oss.domain.FileDO;
import com.ifast.oss.service.FileService;
import com.ifast.sys.dao.DeptDao;
import com.ifast.sys.dao.UserDao;
import com.ifast.sys.dao.UserRoleDao;
import com.ifast.sys.domain.DeptDO;
import com.ifast.sys.domain.UserDO;
import com.ifast.sys.domain.UserRoleDO;
import com.ifast.sys.service.UserService;
import com.ifast.sys.vo.UserVO;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserDO> implements UserService {
    @Autowired
    UserRoleDao userRoleMapper;
    @Autowired
    DeptDao deptMapper;
    @Autowired
    private FileService sysFileService;

    @Override
    public UserDO selectById(Serializable id) {
        List<Long> roleIds = userRoleMapper.listRoleId(id);
        UserDO user = baseMapper.selectById(id);
        user.setDeptName(deptMapper.selectById(user.getDeptId()).getName());
        user.setroleIds(roleIds);
        return user;
    }

    @Transactional
    @Override
    public boolean insert(UserDO user) {
        int count = baseMapper.insert(user);
        Long userId = user.getId();
        List<Long> roles = user.getroleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return retBool(count);
    }

    @Override
    public boolean updateById(UserDO user) {
        int r = baseMapper.updateById(user);
        Long userId = user.getId();
        List<Long> roles = user.getroleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return retBool(r);
    }

    @Override
    public boolean deleteById(Serializable userId) {
        userRoleMapper.removeByUserId(userId);
        return retBool(baseMapper.deleteById(userId));
    }

    @Override
    public boolean exit(Map<String, Object> params) {
        return retBool(baseMapper.selectByMap(params).size());
    }

    @Override
    public Set<String> listRoles(Long userId) {
        return null;
    }

    @Override
    public int resetPwd(UserVO userVO, UserDO userDO) {
        if (Objects.equals(userVO.getUserDO().getId(), userDO.getId())) {
            if (Objects.equals(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdOld()), userDO.getPassword())) {
                userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
                return baseMapper.updateById(userDO);
            } else {
                throw new IFastException("输入的旧密码有误！");
            }
        } else {
            throw new IFastException("你修改的不是你登录的账号！");
        }
    }

    @Override
    public int adminResetPwd(UserVO userVO) {
        UserDO userDO = selectById(userVO.getUserDO().getId());
        if ("admin".equals(userDO.getUsername())) {
            throw new IFastException(EnumErrorCode.userUpdatePwd4adminNotAllowed.getCodeStr());
        }
        userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
        return baseMapper.updateById(userDO);

    }

    @Transactional
    @Override
    public boolean deleteBatchIds(List<? extends Serializable> idList) {
        int count = baseMapper.deleteBatchIds(idList);
        userRoleMapper.deleteBatchIds(idList);
        return retBool(count);
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptMapper.selectList(null);
        Long[] pDepts = deptMapper.listParentDept();
        Long[] uDepts = baseMapper.listAllDept();
        Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts, dept.getId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(dept.getId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "dept");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> users = baseMapper.selectList(null);
        for (UserDO user : users) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(user.getId().toString());
            tree.setParentId(user.getDeptId().toString());
            tree.setText(user.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public int updatePersonal(UserDO userDO) {
        return baseMapper.updateById(userDO);
    }

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        String url = "";

        // 获取图片后缀
        String prefix = fileName.substring((fileName.lastIndexOf(".") + 1));
        String[] str = avatar_data.split(",");
        // 获取截取的x坐标
        int x = (int) Math.floor(Double.parseDouble(str[0].split(":")[1]));
        // 获取截取的y坐标
        int y = (int) Math.floor(Double.parseDouble(str[1].split(":")[1]));
        // 获取截取的高度
        int h = (int) Math.floor(Double.parseDouble(str[2].split(":")[1]));
        // 获取截取的宽度
        int w = (int) Math.floor(Double.parseDouble(str[3].split(":")[1]));
        // 获取旋转的角度
        int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
        try {
            BufferedImage cutImage = ImageUtils.cutImage(file, x, y, w, h, prefix);
            BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(rotateImage, prefix, out);
            // 转换后存入数据库
            byte[] b = out.toByteArray();
            url = sysFileService.upload(b, fileName);
        } catch (Exception e) {
            throw new IFastException("图片裁剪错误！！");
        }
        Map<String, Object> result = new HashMap<>();
        FileDO sysFile = new FileDO(FileType.fileType(fileName), url, new Date());
        if (sysFileService.insert(sysFile)) {
            UserDO userDO = new UserDO();
            userDO.setId(userId);
            userDO.setPicId(sysFile.getId());
            if (retBool(baseMapper.updateById(userDO))) {
                result.put("url", sysFile.getUrl());
            }
        }
        return result;
    }

}
