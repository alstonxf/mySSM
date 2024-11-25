package config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SqlSessionNew {

    @Bean
    public SqlSession getSqlSession() throws IOException {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("mybatisConfig1.xml");

        //2.创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

        //3.使用工厂生产SqlSession对象
        SqlSession sqlSession = factory.openSession();
        return sqlSession;
    }

}
