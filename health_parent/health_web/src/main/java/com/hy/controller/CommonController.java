package com.hy.controller;

import com.hy.constant.MessageConstant;
import com.hy.entity.Result;
import com.hy.utils.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author HY
 * @ClassName CommonController
 * @Description TODE
 * @DateTime 2021/1/8 23:57
 * Version 1.0
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/fileUpload")
    public Result fileUpload(MultipartFile imgFile) throws Exception {
        FileUtils.init();
        //- 获取原有图片名称，截取到后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //获得唯一id
        String fileName=(UUID.randomUUID().toString()+extension).replaceAll("-","");
        //文件上传
        FileUtils.upLoadFile(imgFile.getInputStream(),fileName);
        //封装返回
        Map map=new HashMap(16);
        map.put("domain",FileUtils.getUrl());
        map.put("fileName",fileName);
        FileUtils.close();
        return new Result(true, MessageConstant.UPLOAD_SUCCESS,map);
    }
}
