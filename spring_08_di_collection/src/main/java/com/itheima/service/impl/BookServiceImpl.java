package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.service.BookService;

public class BookServiceImpl implements BookService {
    public String bookServiceName;
    public BookDao bookDao;
    @Override
    public void save() {
        System.out.println("BookServiceImpl save ..." + this.toString());
        bookDao.save();
    }

    public void setBookDao(BookDaoImpl bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public String toString() {
        return "BookServiceImpl{" +
                "bookServiceName='" + bookServiceName + '\'' +
                ", bookDao=" + bookDao +
                '}';
    }

    public void setBookServiceName(String bookServiceName) {
        this.bookServiceName = bookServiceName;
    }
}

