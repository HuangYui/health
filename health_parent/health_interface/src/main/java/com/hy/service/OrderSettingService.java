package com.hy.service;

import com.hy.CustomException.BusinessException;
import com.hy.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author HY
 * @ClassName OrderSettingService
 * @Description TODE
 * @DateTime 2021/1/10 0:20
 * Version 1.0
 */
public interface OrderSettingService {
    /**
     * 批量插入
     * @param readExcel
     * @throws BusinessException
     */
    void insertList(List<String[]> readExcel) throws BusinessException;

    /**
     * 查询当前月份的预约信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 更新可预约数
     * @param orderSetting
     */
    void updateNumberByDate(OrderSetting orderSetting) throws BusinessException;
}
