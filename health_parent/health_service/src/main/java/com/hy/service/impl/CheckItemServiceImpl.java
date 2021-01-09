package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.CustomException.InUseException;
import com.hy.dao.CheckItemDao;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckItem;
import com.hy.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HY
 * @ClassName CheckItemServiceImpl
 * @Description TODE
 * @DateTime 2021/1/6 17:29
 * Version 1.0
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;



    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOne(CheckItem checkItem) {
        checkItemDao.insertOne(checkItem);
    }

    @Override
    public PageResult getListByParam(QueryPageBean queryPageBean) {
        List<CheckItem> checkItemList=checkItemDao.findByPageBean(queryPageBean);
        Long count = checkItemDao.countByParams(queryPageBean);
        return new PageResult(count,checkItemList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCheckItem(Integer id) {
        //删除前显示进行判断，查看当前检查项是否被检查组使用
        CheckItem checkGroupById = checkItemDao.findCheckGroupById(id);
        if (checkGroupById.getCheckGroups().size()!=0){
            throw new InUseException("当前检查项被使用中");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        //可复用
        CheckItem checkItem = checkItemDao.findCheckGroupById(id);
        return checkItem;
    }

    @Override
    public void updateCheckItem(CheckItem checkItem) {
        checkItemDao.updateOne(checkItem);
    }
}
