package com.itheima.dao.impl;

import com.itheima.dao.BookDao;

public class BookDaoImpl implements BookDao {
    // 测试构造器实例化
    public BookDaoImpl() {
        System.out.println("new book dao constructor is running ....");
    }

    public void save() {
        System.out.println("new book dao save ...");
    }

}
