package com.hy.dao;

import com.hy.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HY
 * @ClassName OrderSettingDao
 * @Description TODE
 * @DateTime 2021/1/10 0:40
 * Version 1.0
 */
public interface OrderSettingDao {

    /**
     * 插入一条OrderSetting
     * @param orderSetting
     */
    void insert(OrderSetting orderSetting);

    /**
     * 根据日期查找OrderSetting
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新一条数据
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);

    /**
     * 查询月份订单列表
     * @param month
     * @return
     */
    List<Map<String, Integer>> findMonthList(String month);
}
