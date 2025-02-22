package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

//如果你不想在启动类中使用 @MapperScan，你可以直接在每个 Mapper 接口上使用 @Mapper 注解
public interface UserMapper {

    // 使用注解查询单个用户
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    // 使用xml配置文件查询所有用户
//    @Select("SELECT * FROM user")
    List<User> findAll();
}
