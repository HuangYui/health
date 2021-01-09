package com.hy.dao;

import com.github.pagehelper.Page;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HY
 * @ClassName CheckGroupDao
 * @Description TODE
 * @DateTime 2021/1/7 15:56
 * Version 1.0
 */
public interface CheckGroupDao {
    /**
     * 根据检查项id查找检查组
     * @param id
     * @return
     */
    List<CheckGroup> findCheckGroupByItemId(Integer id);

    /**
     * 添加检查组
     * @param checkGroup
     */
    void insertOne(CheckGroup checkGroup);

    /**
     * 检查组检查项关系添加
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    Page<CheckGroup> findPageByParam(QueryPageBean queryPageBean);

    /**
     * 根据id获得检查组
     * @param checkGroupId
     * @return
     */
    CheckGroup findById(Integer checkGroupId);

    /**
     * 根据检查组id查询所有检查项id
     * @param checkGroupId
     * @return
     */
    List<Integer> findCheckItemById(Integer checkGroupId);

    /**
     * 根据检查组id删除关系
     * @param checkGroupId
     */
    void deleteAllRelation(Integer checkGroupId);

    /**
     * 更新检查组
     * @param checkGroup
     */
    void updateOne(CheckGroup checkGroup);

    /**
     * 删除一个
     * @param id
     */
    void deleteOne(Integer id);

    /**
     * 检查是否被套餐使用
     * @param id
     * @return
     */
    Long countByCheckGroupId(Integer id);

    /**
     * 获得所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
