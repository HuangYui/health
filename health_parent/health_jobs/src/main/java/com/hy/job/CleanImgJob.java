package com.hy.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.service.SetmealService;
import com.hy.utils.FileUtils;
import com.hy.utils.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author HY
 * @ClassName CleanImgJob
 * @Description TODE
 * @DateTime 2021/1/11 20:42
 * Version 1.0
 */
@Component
public class CleanImgJob {

    protected Logger logger = LoggerFactory.getLogger(CleanImgJob.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Reference
    private SetmealService setmealService;

    @Scheduled(initialDelay = 1000, fixedDelay = 30000)
    private void cleanImgJob() {
        //数据库的imgKeys
        List<String> imgKeyList = setmealService.getImgKeyList();
        //获得云端的imgKeys
        List<String> cloudKeys = FileUtils.getFileList();
        //不同的就是可以删的
        List<String> listDiff = ListUtils.getListDiff(imgKeyList, cloudKeys);
        if (listDiff.size() != 0) {
            logger.debug("开始清理，当前时间为:" + simpleDateFormat.format(new Date()));
            listDiff.stream().forEach(key -> {
                logger.debug("清理文件:" + key);
                FileUtils.deleteRootFile(key);
            });
            logger.debug("开始完毕，当前时间为:" + simpleDateFormat.format(new Date()));
        }else {
            logger.debug("云空间无垃圾");
        }
    }
}
