package com.itheima.service;

import com.itheima.domain.Book;
import java.util.List;

public interface BookService {
    void save(Book book);
    List<Book> findAll();
}
