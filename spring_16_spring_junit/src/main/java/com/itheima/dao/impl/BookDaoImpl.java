package com.itheima.dao.impl;

import com.itheima.dao.BookDao;
import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("bookDaoImpl")
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> save() {
        System.out.println("book dao save ..." + this.toString());
        return bookDao.save();
    }

}
