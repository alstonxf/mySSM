package com.itheima.dao.impl;

import com.itheima.dao.BookDao;

public class BookDaoImpl implements BookDao {
    @Override
    public void save() {
        System.out.println("book dao ...");
    }

    public void init(){
        System.out.println("执行初始化方法");
    }

    public void destroy(){
        System.out.println("执行了销毁方法");
    }
}
