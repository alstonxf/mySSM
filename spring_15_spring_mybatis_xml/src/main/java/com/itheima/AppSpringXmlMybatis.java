package com.itheima;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.itheima.service.impl.bookServiceImpl;

public class AppSpringXmlMybatis {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springConfig.xml");
        bookServiceImpl bookServiceImpl1 = (bookServiceImpl) context.getBean("bookServiceImpl1");
        bookServiceImpl1.save();
    }

}
