package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.service.BookService;

public class BookServiceImpl implements BookService {
//    private BookDao bookDao = new BookDaoImpl(); 删除new的对象，然后改用ioc容器中的对象（通过配置文件调用set方法关联，也就是DI注入）。
    public BookDao bookDao;
    @Override
    public void save() {
        bookDao.save();
    }

    public void setBookDao(BookDaoImpl bookDao) {
        this.bookDao = bookDao;
    }
}
