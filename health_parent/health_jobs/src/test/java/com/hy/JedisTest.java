package com.hy;

import com.hy.job.GenerateHtmlJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author HY
 * @ClassName JedisTest
 * @Description TODE
 * @DateTime 2021/1/12 0:55
 * Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-job.xml") //配置文件
public class JedisTest {

    @Autowired
    JedisPool JedisPool;

    @Autowired
    GenerateHtmlJob generateHtmlJob;

    @Test
    public void testRedis() {
        Jedis resource = JedisPool.getResource();
        Double aDouble = Double.valueOf(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        resource.zadd("static_setmeal",aDouble,"vvv");
    }

    @Test
    public void testGenerateList() throws Exception {
        generateHtmlJob.generateSetmealList();
    }
}
