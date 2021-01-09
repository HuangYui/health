package com.hy.service;

import com.hy.CustomException.InUseException;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.Setmeal;

import java.util.List;

/**
 * @author HY
 * @ClassName SetmealService
 * @Description TODE
 * @DateTime 2021/1/9 18:01
 * Version 1.0
 */
public interface SetmealService {

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void addOne(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult findByParams(QueryPageBean queryPageBean);

    /**
     * 根据Id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询被套餐使用了的检查组
     * @param groupId
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(Integer groupId);

    /**
     * 更新套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 删除套餐
     * @param id
     * @throws InUseException
     */
    void delete(Integer id) throws InUseException;
}
