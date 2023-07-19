package com.itheima;

import com.itheima.config.SpringConfig;
import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.service.BookService;
import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.Properties;

public class AppForAnnotation {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDaoImpl = (BookDao)context.getBean("bookDao");
        bookDaoImpl.save();

        BookService bookServiceImpl = (BookService)context.getBean("bookService");
        System.out.println(bookServiceImpl);
        bookServiceImpl.save();

    }
}
