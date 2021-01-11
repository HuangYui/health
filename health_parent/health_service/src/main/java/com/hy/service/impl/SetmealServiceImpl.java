package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.CustomException.InUseException;
import com.hy.dao.SetmealDao;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.Setmeal;
import com.hy.service.SetmealService;
import com.hy.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HY
 * @ClassName SetmealServiceImpl
 * @Description TODE
 * @DateTime 2021/1/9 18:02
 * Version 1.0
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOne(Setmeal setmeal, Integer[] checkgroupIds) {
        //先新增套餐才有id
        setmealDao.insert(setmeal);
        //获得id
        Integer setmealId = setmeal.getId();
        //插入关系表
        for (Integer checkgroupId : checkgroupIds) {
            setmealDao.insertSetmealCheckgroup(setmealId,checkgroupId);
        }
    }

    @Override
    public PageResult findByParams(QueryPageBean queryPageBean) {
        if (queryPageBean!=null) {
            PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        }
        Page<Setmeal> page=setmealDao.findSetmealByParams(queryPageBean);
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(Integer groupId) {
        return setmealDao.findCheckgroupIdsBySetmealId(groupId);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        Integer setmealId = setmeal.getId();
        //先删除关系
        setmealDao.deleteRelationship(setmealId);
        //然后更新
        setmealDao.updateOne(setmeal);
        //再添加关系
        for (Integer checkgroupId : checkgroupIds) {
            setmealDao.insertSetmealCheckgroup(setmealId,checkgroupId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        //看下是否被订单使用
       Long count= setmealDao.countUsedBySetmealId(id);
       if (count!=0){
           throw new InUseException("该套餐被订单使用了");
       }
       //删除与检查组的关系
        setmealDao.deleteRelationship(id);
        setmealDao.delete(id);
    }

    @Override
    public Setmeal findDetailById(Integer id) {
        Setmeal detailById = setmealDao.findDetailById(id);
        detailById.setImg(FileUtils.getUrl()+detailById.getImg());
        return detailById;
    }

    @Override
    public List<String> getImgKeyList() {

        return setmealDao.findAllImg();
    }


}
