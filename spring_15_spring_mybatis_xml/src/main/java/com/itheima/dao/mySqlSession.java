package com.itheima.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class mySqlSession {

    public static SqlSessionFactory sqlSessionFactory;
//    SqlSession sqlSession;

    public mySqlSession(){};

    public mySqlSession(String sqlMapConfigPath) throws IOException {
        // 1. 加载SqlMapConfig.xml配置文件
        InputStream inputStream = Resources.getResourceAsStream(sqlMapConfigPath);
        // 2. 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 3. 创建SqlSessionFactory对象
        sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    }

//    public mySqlSession(Class mapper){
//        // 4. 获取SqlSession
//        sqlSession = sqlSessionFactory.openSession();
//        sqlSession.getMapper(mapper);
//    }


}
