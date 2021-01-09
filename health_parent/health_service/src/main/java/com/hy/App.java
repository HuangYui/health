package com.hy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author HY
 * @ClassName App
 * @Description TODE
 * @DateTime 2021/1/6 17:21
 * Version 1.0
 */
public class App {

    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
        System.in.read();
    }
}
