package com.itheima.dao.impl;

import com.itheima.dao.BookDao;
import com.itheima.dao.UserDao;

public class UserDaoImpl implements UserDao {

    String name;
    BookDao bookDao;
    BookDao bookDao2;

    public UserDaoImpl() {
    }

    public UserDaoImpl(String name) {
        this.name = name;
        System.out.println("调用了初始化方法(String name)");
    }

    public UserDaoImpl(BookDao bookDao) {
        this.bookDao = bookDao;
        System.out.println("调用了初始化方法(BookDao bookDao)");
    }

    public UserDaoImpl(BookDao bookDao,String name) {
        this.name = name;
        this.bookDao = bookDao;
        System.out.println("调用了初始化方法(BookDao bookDao,String name)");
    }


    public UserDaoImpl(String name, BookDao bookDao, BookDao bookDao2) {
        this.name = name;
        this.bookDao = bookDao;
        this.bookDao2 = bookDao2;
        System.out.println("调用了初始化方法(String name, BookDao bookDao, BookDao bookDao2)");
    }

    @Override
    public void save() {
        System.out.println("User save ...");
    }

    @Override
    public String toString() {
        return "UserDaoImpl{" +
                "name='" + name + '\'' +
                ", bookDao=" + bookDao +
                '}';
    }
}
