package com.itheima;

import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class App2 {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("spring_10_container/src/main/resources/applicationContent.xml");
        BookServiceImpl bookService = (BookServiceImpl)context.getBean("bookService");
        bookService.save();
    }
}
