package com.itheima.service.impl;

import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookService bookService;

    @Override
    public List<Map<String, Object>> save() {
        System.out.println("BookServiceImpl save ..." + this.toString());
        return bookService.save();

    }

    @Override
    public String toString() {
        return "BookServiceImpl{" +
                "bookService=" + bookService +
                '}';
    }
}

