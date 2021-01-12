package com.hy.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.CustomException.BusinessException;
import com.hy.CustomException.SysException;
import com.hy.pojo.Setmeal;
import com.hy.service.SetmealService;
import com.hy.utils.FileUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.xmlbeans.impl.xb.xsdschema.OpenAttrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HY
 * @ClassName GenerateHtmlJob
 * @Description TODE
 * @DateTime 2021/1/11 23:42
 * Version 1.0
 */
@Component
@PropertySource("classpath:freemarker.properties")
public class GenerateHtmlJob {

    protected Logger logger = LoggerFactory.getLogger(GenerateHtmlJob.class);
    /**
     * 标识，redis中的任务的key
     */
    private final String STATIC_HTML_KEY = "static_setmeal";
    /**
     * 生成页面
     */
    private final String STATIC_HTML_GENERATE = "1";
    /**
     * 删除页面
     */
    private final String STATIC_HTML_DELETE = "0";


    @Autowired
    private JedisPool jedisPool;
    /**
     * freemarker配置类
     */
    @Autowired
    private Configuration configuration;

    /**
     * 生成的文件生成目录
     */
    @Value("${out_put_path}")
    private String outPutPath;

    /**
     * 模板目录
     */
    @Value("${template_path}")
    private String templatePath;

    @Reference
    private SetmealService setmealService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 任务类启动时，先执行生成套餐和套餐详情页
     * @PostConstruck spring创建对象后执行的方法
     */
    @PostConstruct
    public void initFreeMarker() {
        //1.设置模板目录位置
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            //2.设置字符集
            configuration.setDefaultEncoding("utf-8");
            logger.debug("初始化文件列表");
            generateSetmealList();
        } catch (Exception e) {
        }
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void doGenerateHtml() throws Exception {

        Jedis jedis = jedisPool.getResource();
        if (jedis.exists(STATIC_HTML_KEY)) {
            logger.debug("发现{}存在任务，开始执行时间:" + simpleDateFormat.format(new Date()), STATIC_HTML_KEY);
            //拿到所有任务后。
            Set<String> zrange = jedis.zrange(STATIC_HTML_KEY, 0, -1);
            zrange.forEach(member -> {
                logger.debug("开始执行任务:{}", member);
                String[] split = member.split("\\|");
                //如果是第二位是1，执行生成文件
                if (STATIC_HTML_GENERATE.equals(split[1])) {
                    //先获得套餐id
                    Integer setMealId = Integer.valueOf(split[0]);
                    logger.debug("开始执行生成文件任务:{}", member);
                    //生成套餐详细页面
                    try {
                        generateSetmealDetail(setMealId);
                        logger.debug("执行完成:{},删除key中的member:", member);
                        //任务完成，移除member
                        jedis.zrem(STATIC_HTML_KEY,member);
                    } catch (Exception e) {
                        logger.debug("执行出错:{}", e);
                        throw new SysException("生成套餐详情错误");
                    }
                }
                //如果是0就表示套餐被删除
                else if (STATIC_HTML_DELETE.equals(split[1])){
                    //删除套餐对应的详情页
                    String path=outPutPath+"setmeal_"+split[0]+".html";
                    File file=new File(path);
                    if (file.exists()){
                        file.delete();
                        jedis.zrem(STATIC_HTML_KEY,member);
                        logger.debug("删除路径为{}的文件", path);
                    }

                }
            });
            logger.debug("任务执行完成,当前时间:" + simpleDateFormat.format(new Date()));
            //数据变更，重新生成套餐列表
            generateSetmealList();
        } else {
            logger.debug("当前时间" + "没有任务");
        }

    }

    /**
     * 生成套餐列表以及对应的详情页
     * @throws Exception
     */
    public void generateSetmealList() throws Exception {
//        initFreeMarker();
        List<Setmeal> setmealList = setmealService.findAll();
        setmealList.forEach(setmeal->{
            setmeal.setImg(FileUtils.getUrl()+setmeal.getImg());
            try {
                generateSetmealDetail(setmeal.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (setmealList.size()!=0){
            //加载模板
            Template template = configuration.getTemplate("mobile_setmeal.ftl");
            //创建数据模型
            Map map = new HashMap(16);
            map.put("setmealList", setmealList);
            //输出流
            FileWriter fileWriter = new FileWriter( outPutPath + "mobile_setmeal" + ".html");
            //生成文件
            template.process(map, fileWriter);
            fileWriter.close();
        }
    }

    /**
     * 生成套餐详情页
     * @param id
     * @throws Exception
     */
    public void generateSetmealDetail(Integer id) throws Exception {
//        initFreeMarker();
        Setmeal detailById = setmealService.findDetailById(id);
        //模板
        Template template = configuration.getTemplate("mobile_setmeal_detail.ftl");
        Map map = new HashMap(16);
        map.put("setmeal", detailById);
        FileWriter fileWriter=new FileWriter(outPutPath+"setmeal_"+id+".html");
        template.process(map,fileWriter);
        fileWriter.close();
    }


}
