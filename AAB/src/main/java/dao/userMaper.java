package dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface userMaper {
    @Select("select id,name,money from tbl_account;")
    List<user> getUsers();
}
