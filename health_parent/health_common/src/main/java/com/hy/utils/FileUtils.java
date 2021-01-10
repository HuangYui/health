package com.hy.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.hy.CustomException.SysException;

import java.io.*;
import java.sql.Statement;

/**
 * @author HY
 * @ClassName FileUtils
 * @Description TODE
 * @DateTime 2021/1/8 23:59
 * Version 1.0
 */
public class FileUtils {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String protocol ="http://";
    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    //bucketName
    private static String bucketName = "bucket100860";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    private static String accessKeyId = "LTAI4G3zaQJo33B56qibK6Hd";
    private static String accessKeySecret = "NCLYMonKymS5aPmNKDfGK9ULjjg6Jw";
    private static OSS ossClient;

    public static String getUrl() {
        return protocol+bucketName+"."+endpoint+"/";
    }

    /**
     * 初始化
     */
    public static void init() {
        ossClient = new OSSClientBuilder().build(protocol+endpoint, accessKeyId, accessKeySecret);
    }
    public static String upLoadFile(InputStream file, String fileName) {
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            PutObjectResult putResult = ossClient.putObject(bucketName, fileName, file, objectMetadata);
            String ret = putResult.getETag();
            return ret;
        } catch (IOException e) {
            throw new SysException("系统出错了，上传失败");
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase(".jpeg") || filenameExtension.equalsIgnoreCase(".jpg")
                || filenameExtension.equalsIgnoreCase(".png") || filenameExtension.equalsIgnoreCase(".JPG")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase(".pptx") || filenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase(".docx") || filenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (filenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        return "image/jpeg";
    }

    /**
     * 关闭
     */
    public static void close() {
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
