package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.domain.Book;
import com.itheima.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    @Override
    public void save(Book book) {
        bookDao.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
