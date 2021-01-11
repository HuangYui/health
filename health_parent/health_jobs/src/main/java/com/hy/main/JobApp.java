package com.hy.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author HY
 * @ClassName JobApp
 * @Description TODE
 * @DateTime 2021/1/11 21:04
 * Version 1.0
 */
public class JobApp {

    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:application-job.xml");
        System.in.read();
    }
}
