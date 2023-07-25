package com.itheima.dao;

import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface BookDao {

    @Select("SELECT * FROM ssm_db.tbl_book;")
    List<Book> save();

}
