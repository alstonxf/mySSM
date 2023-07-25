package com.itheima.dao;

public interface BookDao {
    void save();
    void update();
    void delete(Integer id);
    void delete();

    void select();
}
