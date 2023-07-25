package com.itheima.dao.impl;

import com.itheima.dao.BookDao;
import org.springframework.stereotype.Component;

@Component("bookDaoImpl")
public class BookDaoImpl implements BookDao {

    private Integer id;
    private String name;

    public void save() {
        System.out.println("saving ...");
    }

    public void update() {
        System.out.println("update ...");
    }

    public void delete() {
        System.out.println("delete ...");
    }

    public void select() {
        System.out.println("select ...");
    }
}
