package com.hy.dao;

import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckGroup;
import com.hy.pojo.CheckItem;

import java.util.List;

/**
 * @author HY
 * @ClassName CheckItemDao
 * @Description TODE
 * @DateTime 2021/1/6 17:36
 * Version 1.0
 */
public interface CheckItemDao {
    /**
     * 查询所有检查项
      * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加一个检查项
     * @param checkItem
     */
    void insertOne(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    List<CheckItem> findByPageBean(QueryPageBean queryPageBean);

    /**
     * 根据条件查询总条数
     * @param queryPageBean
     * @return
     */
    Long countByParams(QueryPageBean queryPageBean);

    /**
     * 根据检查项id查找包含的检查组
     * @param id
     * @return
     */
    CheckItem findCheckGroupById(Integer id);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 更新检查项
     * @param checkItem
     */
    void updateOne(CheckItem checkItem);


    /**
     * 根据检查组id查询所有检查项
     * @param id
     * @return
     */
    List<CheckItem> findByGroupId(Integer id);
}
