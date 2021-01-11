package com.hy.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.hy.CustomException.SysException;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HY
 * @ClassName FileUtils
 * @Description TODE
 * @DateTime 2021/1/8 23:59
 * Version 1.0
 */
public class FileUtils {

    private static   Logger logger=LoggerFactory.getLogger(FileUtils.class);

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String protocol ="http://";
    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    //bucketName
    private static String bucketName = "bucket100860";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    private static String accessKeyId = "LTAI4G3zaQJo33B56qibK6Hd";
    private static String accessKeySecret = "NCLYMonKymS5aPmNKDfGK9ULjjg6Jw";
    private static OSS ossClient;

    //返回仓库url
    public static String getUrl() {
        return protocol+bucketName+"."+endpoint+"/";
    }



    /**
     * 初始化
     */
    static  {
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

    /**
     * 创建模拟文件夹
     *
     * param ossClient oss连接
     * param bucketName 存储空间
     * param folder 模拟文件夹名如"qj_nanjing/"
     * return 文件夹名
     */
    public static String createFolder(String bucketName, String folder) {
        // 文件夹名
        final String keySuffixWithSlash = folder;
        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            logger.info("创建文件夹成功");
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }


    /**
     * 根据key删除OSS服务器上的文件
     *
     * param ossClient oss连接
     * param bucketName 存储空间
     * param folder 模拟文件夹名 如"qj_nanjing/"
     * param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    private static void deleteFile(String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    public static void  deleteRootFile(String key) {
        deleteFile(bucketName,"",key);
    }

    public static List<String> getFileList() {
        // 列举文件。如果不设置KeyPrefix，则列举存储空间下的所有文件。如果设置KeyPrefix，则列举包含指定前缀的文件。
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums.stream().map(OSSObjectSummary::getKey).collect(Collectors.toList());
    }


}
