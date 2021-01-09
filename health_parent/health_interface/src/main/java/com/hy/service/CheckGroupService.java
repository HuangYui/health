package com.hy.service;

import com.hy.CustomException.InUseException;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckGroup;

import java.util.List;

/**
 * @author HY
 * @ClassName CheckGroupService
 * @Description TODE
 * @DateTime 2021/1/8 17:14
 * Version 1.0
 */
public interface CheckGroupService {

    /**
     * 新增
     * @param checkitemIds
     * @param checkGroup
     */
    void addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据id获得检查组
     * @param checkGroupId
     * @return
     */
    CheckGroup findById(Integer checkGroupId);

    /**
     * 根据id获得检查项
     * @param checkGroupId
     * @return
     */
    List<Integer> findCheckItemById(Integer checkGroupId);

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void updateOne(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组
     * @param id
     * @throws InUseException
     */
    void deleteById(Integer id)throws InUseException;

    /**
     * 获得所有检查组
     * @return
     */
    List<CheckGroup> findAll();


}
