package com.itheima;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws SQLException {
        // 创建 Spring 应用程序上下文，加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");

        // 获取 BookDaoImpl 对象
        BookDaoImpl bookDaoImpl = (BookDaoImpl) context.getBean("bookDao");

        // 创建用于存储 BookDaoImpl 对象的列表
        ArrayList<BookDaoImpl> bookDaoArrayList = new ArrayList<BookDaoImpl>();

        // 获取数据源对象
        DruidDataSource dataSource = (DruidDataSource) context.getBean("dataSource");
        // 如果不使用 Spring 管理数据源，可以手动创建并配置数据源
        // DruidDataSource dataSource = new DruidDataSource();
        // dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        // dataSource.setUrl("jdbc:mysql://localhost:3306/mysql?useSSL=false");
        // dataSource.setUsername("root");
        // dataSource.setPassword("12345678");

        // 获取数据库连接
        DruidPooledConnection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();

        // 执行 SQL 查询
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ssm_db.tbl_book;");

        // 遍历查询结果
        while (resultSet.next()) {
            // 从结果集中提取数据并设置到 BookDaoImpl 对象中
            bookDaoImpl.setId(resultSet.getInt("id"));
            bookDaoImpl.setName(resultSet.getString("name"));
            bookDaoImpl.setType(resultSet.getString("type"));
            bookDaoImpl.setDescription(resultSet.getString("description"));

            // 将 BookDaoImpl 对象添加到列表中
            bookDaoArrayList.add(bookDaoImpl);
        }

        // 打印书籍列表
        System.out.println(Arrays.asList(bookDaoArrayList));

        // 获取 BookServiceImpl 对象
        BookServiceImpl bookService = (BookServiceImpl) context.getBean("bookService");

        // 调用保存方法
        bookService.save();
    }
}
