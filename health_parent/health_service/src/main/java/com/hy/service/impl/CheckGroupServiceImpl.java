package com.hy.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.CustomException.InUseException;
import com.hy.dao.CheckGroupDao;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckGroup;
import com.hy.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author HY
 * @ClassName CheckGroupServiceImpl
 * @Description TODE
 * @DateTime 2021/1/8 17:16
 * Version 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup) {
        checkGroupDao.insertOne(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        //先添加检查组,因为关系表有外键依赖
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String queryString = queryPageBean.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            // 有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<CheckGroup> pageByParam = checkGroupDao.findPageByParam(queryPageBean);
        return new PageResult(pageByParam.getTotal(), pageByParam);

    }

    @Override
    public CheckGroup findById(Integer checkGroupId) {
       return checkGroupDao.findById(checkGroupId);
    }

    @Override
    public List<Integer> findCheckItemById(Integer checkGroupId) {
        return checkGroupDao.findCheckItemById(checkGroupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(CheckGroup checkGroup, Integer[] checkitemIds) {
        Integer checkGroupId = checkGroup.getId();
        //先删除所有关系
        checkGroupDao.deleteAllRelation(checkGroupId);
        //再更新数据
        checkGroupDao.updateOne(checkGroup);
        //再添加
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        //检查有没有被套餐使用
        Long count=checkGroupDao.countByCheckGroupId(id);
        if (count!=0){
            throw new InUseException("检查项被套餐被使用了!无法删除");
        }
        //再删除依赖关系
        checkGroupDao.deleteAllRelation(id);
        //再删除检查组
        checkGroupDao.deleteOne(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


}
