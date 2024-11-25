package configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

//@Configuration
/**
 * *其他配置类引入了 @PropertySource：如果你的项目中有其他类，比如带 @Configuration 的主配置类，
 * * 且带有 @PropertySource 注解来加载 db.properties 文件，Spring 会加载这些属性。
 * * 因此，即便在 jdbcConfiguration 类上没有使用 @Component 或 @Configuration 注解，@Value 仍然能够访问这些属性，因为 Spring 容器已经将它们加载到环境中。
 */
@Configuration
public class jdbcConfiguration {

    //要通过 @Value 注解来读取 db.properties 中的配置项
    @Value("${dbip}")
    String dbip;
    @Value("${dbport}")
    String dbport;
    @Value("${username1}")
    String username;
    @Value("${password}")
    String password;

    @Bean
    public DruidDataSource dataSource1 (){
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setUrl("jdbc:mysql://"+dbip+":"+dbport+"/spring_db?useSSL=false");
        dataSource1.setUsername(username);
        dataSource1.setPassword(password);
        // 打印连接信息
        System.out.println("Database URL: " + dataSource1.getUrl());
        System.out.println("Database Username: " + dataSource1.getUsername());
        System.out.println("Database Password: " + dataSource1.getPassword());

        return dataSource1;
    }
    //另一数据源
    //    @Bean
//    public ComboPooledDataSource dataSource2(){
//        ComboPooledDataSource dataSource2 = new ComboPooledDataSource();
//
//        dataSource2.setJdbcUrl("jdbc:mysql://"+dbip+":"+dbport+"/spring_db?useSSL=false");
//        dataSource2.setUser(username);
//        dataSource2.setPassword(password);
//        // 打印连接信息
//        System.out.println("Database URL: " + dataSource2.getJdbcUrl());
//        System.out.println("Database Username: " + dataSource2.getUser());
//        System.out.println("Database Password: " + dataSource2.getPassword());
//
//        return dataSource2;
//
//    }
    //mybatis与spring 结合的第一种方法，使用SqlSessionFactoryBuilder，并结合使用xml文件。
    @Bean
    public SqlSession getMybatisSqlSession(DruidDataSource dataSource1) throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatisConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 将数据源设置到 SqlSessionFactory
        sqlSessionFactory.getConfiguration().setEnvironment(
                new org.apache.ibatis.mapping.Environment("development",
                        new org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory(),
                        dataSource1)
        );
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    //mybatis与spring 结合的第二种方法，使用SqlSessionFactoryBean，并使用MapperScannerConfigurer读取sql映射。
    //定义bean，SqlSessionFactoryBean，用于产生SqlSessionFactory对象
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource){
//        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
//        //设置数据源
//        ssfb.setDataSource(dataSource);
//        return ssfb;
//    }
//    //定义bean，返回MapperScannerConfigurer对象
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//        MapperScannerConfigurer msc = new MapperScannerConfigurer();
//        msc.setSqlSessionFactoryBeanName("sqlSessionFactory");  // 指定 SqlSessionFactoryBean 名称
//        msc.setBasePackage("dao"); // 指定要扫描的map映射包
//
//        return msc;
//    }
}
