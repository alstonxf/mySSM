package com.itheima;

import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
        BookServiceImpl bookService = (BookServiceImpl)context.getBean("bookService");
        bookService.save();
    }
}
