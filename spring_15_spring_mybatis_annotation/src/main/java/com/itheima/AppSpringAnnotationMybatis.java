package com.itheima;

import com.itheima.config.SpringConfig;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.domain.Book;
import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AppSpringAnnotationMybatis {
    public static void main(String[] args) throws SQLException {

        // 改用注解加载配置
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

//         获取 BookDaoImpl 对象
        BookDaoImpl bookDaoImpl = (BookDaoImpl) context.getBean("bookDaoImpl");

        // 执行 SQL 查询
        List<Book> resultSet = bookDaoImpl.save();

//         打印书籍列表
        System.out.println(resultSet);

        // 获取 BookServiceImpl 对象
        BookServiceImpl bookService = (BookServiceImpl) context.getBean("bookServiceImpl");

        // 调用保存方法
        List<Map<String, Object>> resultMap = bookService.save();
        //         打印书籍列表
        System.out.println(resultMap);
    }
}
