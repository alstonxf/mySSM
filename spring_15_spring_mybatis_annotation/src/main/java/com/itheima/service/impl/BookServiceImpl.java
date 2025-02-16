package com.itheima.service.impl;

import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.domain.Book;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDaoImpl bookDaoImpl;

    @Override
    public List<Book> save() {
        System.out.println("BookServiceImpl save ..." + this.toString());
        return bookDaoImpl.save();
    }

}

