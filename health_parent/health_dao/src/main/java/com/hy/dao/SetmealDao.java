package com.hy.dao;

import com.github.pagehelper.Page;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HY
 * @ClassName SetmealDao
 * @Description TODE
 * @DateTime 2021/1/9 18:09
 * Version 1.0
 */
public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     */
    void insert(Setmeal setmeal);

    /**
     * 添加套餐与检查组关系
     * @param setmealId
     * @param checkgroupId
     */
    void insertSetmealCheckgroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    Page<Setmeal> findSetmealByParams(QueryPageBean queryPageBean);

    /**
     * 根据Id查找套餐
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询被套餐使用了的检查组
     * @param groupId
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(Integer setmealId);

    /**
     * 删除套餐与检查组的关系
     * @param setmealId
     */
    void deleteRelationship(Integer setmealId);

    /**
     * 更新一个套餐
     * @param setmeal
     */
    void updateOne(Setmeal setmeal);

    /**
     * 查询套餐是否被订单使用
     * @param id
     * @return
     */
    Long countUsedBySetmealId(Integer id);

    /**
     * 删除套餐
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据查询套餐详细信息
     * @param id
     * @return
     */
    Setmeal findDetailById(Integer id);

    /**
     * 获得所有被使用了的img与对应的id
     * @return
     */
    List<String> findAllImg();

    /**
     * 查找所有套餐
     * @return
     */
    List<Setmeal> findAll();
}
