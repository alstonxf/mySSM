package com.itheima.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * MyBatis 配置类
 * 该类用于配置 MyBatis 相关的 Bean，包括 SqlSessionFactoryBean 和 MapperScannerConfigurer
 */
public class MybatisConfig {

    /**
     * 配置 SqlSessionFactoryBean
     * SqlSessionFactoryBean 是 MyBatis 的核心工厂，用于创建 SqlSessionFactory。
     *
     * @param dataSource 数据源（由 Spring 自动注入）
     * @return 配置完成的 SqlSessionFactoryBean
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        System.out.println("创建 sqlSessionFactory"); // 控制台日志，方便调试
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

        // 设置 MyBatis 实体类的别名包，简化 Mapper 中的类名引用
        sessionFactoryBean.setTypeAliasesPackage("com.itheima.domain");

        // 设置数据源（数据库连接信息）
        sessionFactoryBean.setDataSource(dataSource);

        return sessionFactoryBean;
    }

    /**
     * 配置 MapperScannerConfigurer
     * 该 Bean 负责扫描 MyBatis 的 Mapper 接口，并自动为其创建代理对象，简化 MyBatis 的使用
     *
     * @return 配置完成的 MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

        // 设置 Mapper 接口所在的基础包，MyBatis 会自动扫描该包下的所有 Mapper 接口
        mapperScannerConfigurer.setBasePackage("com.itheima");

        return mapperScannerConfigurer;
    }
}
