package com.itheima;

import com.itheima.service.BookService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.xml.XmlBeanFactory;

public class AppForBeanFactory {

    public static void main(String[] args) {
        ClassPathResource classPathResource = new ClassPathResource("applicationContent.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(classPathResource);
//        * ==getBean("名称"):需要类型转换==
        BookService bookService = (BookService)beanFactory.getBean("bookService");
//        * ==getBean("名称",类型.class):多了一个参数==
        BookService bookService1 = beanFactory.getBean("bookService",BookService.class);
//        * ==getBean(类型.class):容器中不能有多个该类的bean对象==
//        所以xml里面如果配了两个bookService对象就会报错。No qualifying bean of type 'com.itheima.service.BookService' available: expected single matching bean but found 2: bookService,bookService2
        BookService bookService2 = beanFactory.getBean(BookService.class);
        bookService.save();
        System.out.println(bookService);
        bookService1.save();
        System.out.println(bookService1);
        bookService2.save();
        System.out.println(bookService2);
    }
}
