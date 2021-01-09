package com.hy.service;

import com.hy.CustomException.InUseException;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckItem;

import java.util.List;

/**
 * @author HY
 * @ClassName CheckItemService
 * @Description TODE
 * @DateTime 2021/1/6 17:26
 * Version 1.0
 */

public interface CheckItemService {
    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加一个检查项
     * @param checkItem
     */
    void addOne(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult getListByParam(QueryPageBean queryPageBean);

    /**
     * 删除检查项
     * @param id
     * @throws InUseException
     */
    void deleteCheckItem(Integer id) throws InUseException;

    /**
     * 根据id查询检查项
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 更新检查项
     * @param checkItem
     */
    void updateCheckItem(CheckItem checkItem);
}
