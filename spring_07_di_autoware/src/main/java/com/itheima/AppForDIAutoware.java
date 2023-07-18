package com.itheima;

import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForDIAutoware {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
        BookServiceImpl bookService = (BookServiceImpl)context.getBean("bookService");
        bookService.save();
    }
}
