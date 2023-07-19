package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("bookService")
//@Scope("singleton")
@Scope("prototype")
public class BookServiceImpl implements BookService {
    public String bookServiceName;
    @Autowired
    //自动装配时会拿bookDao2去容器中查找对应的bean，如果没有找到则报错。
    public BookDao bookDao2;
    @Override
    public void save() {
        System.out.println("BookServiceImpl save ..." + this.toString());
        bookDao2.save();
    }

    public void setBookDao(BookDaoImpl bookDao) {
        this.bookDao2 = bookDao;
    }

    @PostConstruct
    public void init(){
        System.out.println("执行初始化方法");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("执行了销毁方法");
    }

    public void setBookServiceName(String bookServiceName) {
        this.bookServiceName = bookServiceName;
    }
}

