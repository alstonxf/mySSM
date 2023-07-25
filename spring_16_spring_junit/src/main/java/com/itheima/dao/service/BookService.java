package com.itheima.dao.service;

import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
//@Mapper
public interface BookService {
    @Select("SELECT * FROM ssm_db.tbl_book limit 5;")
//    @ResultType(Map.class)
    //如果确定返回结果就一条，可以使用Map<String, Object>。String是bean字段名称，Object是bean值。
    List<Map<String, Object>> save();
}
