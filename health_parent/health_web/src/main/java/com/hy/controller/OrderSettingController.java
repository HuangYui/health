package com.hy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.CustomException.BusinessException;
import com.hy.constant.MessageConstant;
import com.hy.entity.Result;
import com.hy.pojo.OrderSetting;
import com.hy.service.OrderSettingService;
import com.hy.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author HY
 * @ClassName OrderSettingController
 * @Description TODE
 * @DateTime 2021/1/9 23:54
 * Version 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);


    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) throws IOException {
        POIUtils.checkFile(excelFile);
        //读取内容
        try {
            List<String[]> readExcel = POIUtils.readExcel(excelFile);
            log.debug("导入预约设置读取到了{}条记录",readExcel.size());
            orderSettingService.insertList(readExcel);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("导入失败",e.getMessage());
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        List<Map<String,Integer>> orderSettingList=orderSettingService.getOrderSettingByMonth(month);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderSettingList);
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        orderSettingService.updateNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}
