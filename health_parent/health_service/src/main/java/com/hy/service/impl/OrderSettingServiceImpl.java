package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.CustomException.BusinessException;
import com.hy.dao.OrderSettingDao;
import com.hy.pojo.OrderSetting;
import com.hy.service.OrderSettingService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author HY
 * @ClassName OrderSettingServiceImpl
 * @Description TODE
 * @DateTime 2021/1/10 0:26
 * Version 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertList(List<String[]> readExcel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<OrderSetting> orderSettings = readExcel.stream().map(arr -> {
            OrderSetting orderSetting = new OrderSetting();
            try {
                orderSetting.setOrderDate(sdf.parse(arr[0]));
                orderSetting.setNumber(Integer.valueOf(arr[1]));
            } catch (ParseException e) {
            }
            return orderSetting;
        }).collect(Collectors.toList());

        for (OrderSetting orderSetting : orderSettings) {
            //判断是否存在该日期数据
            OrderSetting dbOrderSetting = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());

            //如果有数据
            if (dbOrderSetting != null) {
                //判断设置的可预约数是否小于已预约数
                if (orderSetting.getNumber() < dbOrderSetting.getReservations()) {
                    throw new BusinessException(sdf.format(orderSetting.getOrderDate()) + "设置的预约数不可小于已预约数");
                }
                //更新最大预约数
                orderSettingDao.update(orderSetting);
            } else {
                //没数据就插入
                orderSettingDao.insert(orderSetting);
            }
        }
    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        return orderSettingDao.findMonthList(month);
    }

    @Override
    public void updateNumberByDate(OrderSetting orderSetting) {
        OrderSetting byOrderDate = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        //如果有数据
        if (byOrderDate != null) {
            //判断设置的可预约数是否小于已预约数
            if (orderSetting.getNumber() < byOrderDate.getReservations()) {
                throw new BusinessException(sdf.format(orderSetting.getOrderDate()) + "设置的预约数不可小于已预约数");
            }
            //更新最大预约数
            orderSettingDao.update(orderSetting);
        } else {
            //没数据就插入
            orderSettingDao.insert(orderSetting);
        }
    }
}
