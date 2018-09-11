package com.ifast.sys.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.annotation.Log;
import com.ifast.common.base.AdminBaseController;
import com.ifast.common.domain.DictDO;
import com.ifast.common.domain.Tree;
import com.ifast.common.service.DictService;
import com.ifast.common.utils.MD5Utils;
import com.ifast.common.utils.Result;
import com.ifast.sys.domain.DeptDO;
import com.ifast.sys.domain.RoleDO;
import com.ifast.sys.domain.UserDO;
import com.ifast.sys.service.RoleService;
import com.ifast.sys.service.UserService;
import com.ifast.sys.vo.UserVO;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/sys/user")
@Controller
public class UserController extends AdminBaseController {
    private String prefix = "sys/user";
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    DictService dictService;
    
    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/user";
    }
    
    @GetMapping("/list")
    @ResponseBody
    public Result<Page<UserDO>> list(UserDO userDTO) {
        // 查询列表数据
        Page<UserDO> page = userService.selectPage(getPage(UserDO.class), userService.convertToEntityWrapper("name", userDTO.getName(), "deptId", userDTO.getDeptId()));
        return Result.ok(page);
    }

    
    @RequiresPermissions("sys:user:add")
    @GetMapping("/add")
    String add(Model model) {
        List<RoleDO> roles = roleService.selectList(null);
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @RequiresPermissions("sys:user:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        UserDO userDO = userService.selectById(id);
        model.addAttribute("user", userDO);
        List<RoleDO> roles = roleService.findListStatusByUserId(id);
        model.addAttribute("roles", roles);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:user:add")
    @Log("保存用户")
    @PostMapping("/save")
    @ResponseBody
    Result<String> save(UserDO user) {
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        userService.insert(user);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/update")
    @ResponseBody
    Result<String> update(UserDO user) {
        userService.updateById(user);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/updatePeronal")
    @ResponseBody
    Result<String> updatePeronal(UserDO user) {
        userService.updatePersonal(user);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:remove")
    @Log("删除用户")
    @PostMapping("/remove")
    @ResponseBody
    Result<String> remove(Long id) {
        userService.deleteById(id);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:batchRemove")
    @Log("批量删除用户")
    @PostMapping("/batchRemove")
    @ResponseBody
    Result<String> batchRemove(@RequestParam("ids[]") Long[] userIds) {
        userService.deleteBatchIds(Arrays.asList(userIds));
        return Result.ok();
    }
    
    @PostMapping("/exist")
    @ResponseBody
    boolean exist(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exist(params);
    }

    @RequiresPermissions("sys:user:resetPwd")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id") Long userId, Model model) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        model.addAttribute("user", userDO);
        return prefix + "/reset_pwd";
    }

    @Log("提交更改用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    Result<String> resetPwd(UserVO userVO) {
        userService.resetPwd(userVO, getUser());
        return Result.ok();
    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("admin提交更改用户密码")
    @PostMapping("/adminResetPwd")
    @ResponseBody
    Result<String> adminResetPwd(UserVO userVO) {
        userService.adminResetPwd(userVO);
        return Result.ok();

    }
    
    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree() {
        Tree<DeptDO> tree = new Tree<DeptDO>();
        tree = userService.getTree();
        return tree;
    }
    
    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/userTree";
    }
    
    @GetMapping("/personal")
    String personal(Model model) {
        UserDO userDO = userService.selectById(getUserId());
        model.addAttribute("user", userDO);
        List<DictDO> hobbyList = dictService.getHobbyList(userDO);
        model.addAttribute("hobbyList", hobbyList);
        List<DictDO> sexList = dictService.getSexList();
        model.addAttribute("sexList", sexList);
        return prefix + "/personal";
    }
    
    @Log("上传头像")
    @ResponseBody
    @PostMapping("/uploadImg")
    Result<?> uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        result = userService.updatePersonalImg(file, avatar_data, getUserId());
        return Result.ok(result);
    }
}
