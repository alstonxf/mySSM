package com.itheima;

import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForNameAndScope {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContent.xml");
        BookDaoImpl dao = (BookDaoImpl)applicationContext.getBean("dao");
        BookDaoImpl dao1 = (BookDaoImpl)applicationContext.getBean("dao");
        BookServiceImpl bookServiceImpl1 = (BookServiceImpl)applicationContext.getBean("name1");
        BookServiceImpl bookServiceImpl2 = (BookServiceImpl)applicationContext.getBean("name2");

        //scope="singleton"
        System.out.println(dao);
        System.out.println(dao1);
        //scope="prototype"
        System.out.println(bookServiceImpl1);
        System.out.println(bookServiceImpl2);
    }

}
