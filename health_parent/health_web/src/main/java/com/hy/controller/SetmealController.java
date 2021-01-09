package com.hy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.constant.MessageConstant;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.entity.Result;
import com.hy.pojo.Setmeal;
import com.hy.service.SetmealService;
import com.hy.utils.FileUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HY
 * @ClassName SetMealController
 * @Description TODE
 * @DateTime 2021/1/9 17:57
 * Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        setmealService.addOne(setmeal,checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult=setmealService.findByParams(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,pageResult);
    }

    /**
     * 根据Id查询套餐
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Setmeal setmeal=setmealService.findById(id);
        Map map=new HashMap(16);
        map.put("setmeal",setmeal);
        map.put("domain", FileUtils.getUrl());
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

    @RequestMapping("/findCheckgroupIdsBySetmealId")
    private Result findCheckgroupIdsBySetmealId(Integer groupId) {
        List<Integer> integers=setmealService.findCheckgroupIdsBySetmealId(groupId);
        return new Result(true,"查询成功",integers);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true,"编辑套餐成功");
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        setmealService.delete(id);
        return new Result(true,"删除套餐成功");
    }
}
