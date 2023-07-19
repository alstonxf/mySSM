package com.itheima;

import com.itheima.service.BookService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.xml.XmlBeanFactory;

public class AppForBeanFactory {

    public static void main(String[] args) {
        ClassPathResource classPathResource = new ClassPathResource("applicationContent.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(classPathResource);
        BookService bookService = (BookService)beanFactory.getBean("bookService");
        bookService.save();
    }
}
