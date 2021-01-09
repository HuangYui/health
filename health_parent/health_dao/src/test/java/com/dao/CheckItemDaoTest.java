package com.dao;

import com.hy.dao.CheckItemDao;
import com.hy.pojo.CheckItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.stream.Location;
import java.io.IOException;

/**
 * @author HY
 * @ClassName CheckItemDaoTest
 * @Description TODE
 * @DateTime 2021/1/8 11:35
 * Version 1.0
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext-dao.xml") //配置文件
public class CheckItemDaoTest {

    private  CheckItemDao checkItemDao;

    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-dao.xml");
        checkItemDao= classPathXmlApplicationContext.getBean(CheckItemDao.class);
    }

    @Test
    public void testFindCheckGroupById() {
        CheckItem checkItem = new CheckItemDaoTest().getCheckItem();
        System.out.println(checkItem.getName());
    }

    public CheckItem getCheckItem() {
        return checkItemDao.findCheckGroupById(85);
    }


}
