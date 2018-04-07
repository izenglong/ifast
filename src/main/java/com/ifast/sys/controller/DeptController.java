package com.ifast.sys.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ifast.common.base.BaseController;
import com.ifast.common.config.Constant;
import com.ifast.common.domain.Tree;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.Result;
import com.ifast.sys.domain.DeptDO;
import com.ifast.sys.service.DeptService;

import io.swagger.annotations.ApiOperation;

/**
 * <pre>
 * 部门管理
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/sys/dept")
public class DeptController extends BaseController {
    private String prefix = "sys/dept";
    @Autowired
    private DeptService sysDeptService;

    @GetMapping()
    @RequiresPermissions("system:sysDept:sysDept")
    String dept() {
        return prefix + "/dept";
    }

    @ApiOperation(value = "获取部门列表", notes = "")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:sysDept:sysDept")
    public List<DeptDO> list() {
        return sysDeptService.selectList(null);
    }

    @GetMapping("/add/{pId}")
    @RequiresPermissions("system:sysDept:add")
    String add(@PathVariable("pId") Long pId, Model model) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "总部门");
        } else {
            model.addAttribute("pName", sysDeptService.selectById(pId).getName());
        }
        return prefix + "/add";
    }

    @GetMapping("/edit/{deptId}")
    @RequiresPermissions("system:sysDept:edit")
    String edit(@PathVariable("deptId") Long deptId, Model model) {
        DeptDO sysDept = sysDeptService.selectById(deptId);
        model.addAttribute("sysDept", sysDept);
        if (Constant.Sys.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
            model.addAttribute("parentDeptName", "无");
        } else {
            DeptDO parDept = sysDeptService.selectById(sysDept.getParentId());
            model.addAttribute("parentDeptName", parDept.getName());
        }
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:sysDept:add")
    public Result<String> save(DeptDO sysDept) {
        sysDeptService.insert(sysDept);
        return Result.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:sysDept:edit")
    public Result<String> update(DeptDO sysDept) {
        sysDeptService.updateById(sysDept);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:sysDept:remove")
    public Result<String> remove(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parent_id", id);
        int size = sysDeptService.selectByMap(map).size();
        if (size > 0) {
            return Result.build(EnumErrorCode.deptUpdateErrorExistChilds.getCode(),
                    EnumErrorCode.deptUpdateErrorExistChilds.getMsg());
        }
        if (sysDeptService.checkDeptHasUser(id)) {
            sysDeptService.deleteById(id);
            return Result.ok();
        } else {
            return Result.build(EnumErrorCode.deptDeleteErrorExistUsers.getCode(),
                    EnumErrorCode.deptDeleteErrorExistUsers.getMsg());
        }
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:sysDept:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] deptIds) {
        sysDeptService.deleteBatchIds(Arrays.asList(deptIds));
        return Result.ok();
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree() {
        Tree<DeptDO> tree = new Tree<DeptDO>();
        tree = sysDeptService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/deptTree";
    }

}
