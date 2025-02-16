package com.itheima.service.impl;

import com.itheima.dao.BookDao;
//import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.dao.mySqlSession;
import com.itheima.domain.Book;
import com.itheima.service.bookService;
import org.apache.ibatis.session.SqlSessionFactory;

public class bookServiceImpl implements bookService {

    public bookServiceImpl(){
    }

    @Override
    public void save() {
        Book book1 = mySqlSession.sqlSessionFactory.openSession().getMapper(BookDao.class).save();
        System.out.println(book1+"done");
    }

    @Override
    public void getUsersInfo() {

    }
}
