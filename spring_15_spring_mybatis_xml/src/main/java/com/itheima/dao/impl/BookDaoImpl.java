package com.itheima.dao.impl;

import com.itheima.dao.BookDao;
import com.itheima.domain.Book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BookDaoImpl implements BookDao {
    private SqlSessionFactory sqlSessionFactory;

    // Spring 通过 setter 方式注入
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void save(Book book) {
       SqlSession session = sqlSessionFactory.openSession();
        session.insert("com.itheima.dao.BookDao.save", book);
        session.commit();
    }

    @Override
    public List<Book> findAll() {
        SqlSession session = sqlSessionFactory.openSession();
        return session.selectList("com.itheima.dao.BookDao.findAll");
    }

}
