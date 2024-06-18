package dao;

import domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 用户的持久层  接口
 */
public interface IUserMapper {
    /**
     * 查询操作
     * 
     */
//    <!--第一种方法，使用映射的mapper文件写sql-->
    List<User> findAllXml();

    List<User> findByIdXml(int id);

    void insertXml(User user);

    void deleteByIdXml(int id);

    void updateXml(User user);

//    <!--第二种方法，把映射的mapper文件删除，直接在接口上用注解写sql-->
    @Select("select * from user")
    List<User> findAllAnnotation();

    @Select("select * from user where id = #{id} ")
    List<User> findByIdAnnotation(int id);

    @Insert("insert into user(id,username,birthday,sex,address) values (#{id},#{username},#{birthday},#{sex},#{address})")
    void insertAnnotation(User user);

    @Delete("delete from user where id = #{id} ")
    void deleteByIdAnnotation(int id);

    @Update("update user set username = #{username} , address = #{address} ")
    void updateAnnotation(User user);

}
