package com.hy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.constant.MessageConstant;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.entity.Result;
import com.hy.pojo.CheckGroup;
import com.hy.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HY
 * @ClassName CheckGroupController
 * @Description TODE
 * @DateTime 2021/1/8 17:07
 * Version 1.0
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;


    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds ) {
        checkGroupService.addCheckGroup(checkitemIds,checkGroup);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult=checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @RequestMapping("/findById")
    public Result findById(Integer checkGroupId) {
        CheckGroup checkGroup=checkGroupService.findById(checkGroupId);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        List<Integer> integerList=checkGroupService.findCheckItemById(checkGroupId);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,integerList);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        checkGroupService.updateOne(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("deleteById")
    public Result delete(Integer id) {
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroups=checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
    }
}
