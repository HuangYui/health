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
import java.util.stream.Collectors;

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

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(@RequestBody QueryPageBean queryPageBean) {
        PageResult byParams = setmealService.findByParams(queryPageBean);
        List<Setmeal> rows = byParams.getRows();
        List<Setmeal> collect = rows.stream().map(setmeal -> {
            setmeal.setImg(FileUtils.getUrl() + setmeal.getImg());
            return setmeal;
        }).collect(Collectors.toList());
        byParams.setRows(collect);
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,byParams);
    }

    @RequestMapping("/findDetailById")
    public Result findDetailById(Integer id){
        Setmeal setmeal=setmealService.findDetailById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }


}
