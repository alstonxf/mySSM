
import config.ConfigurationSpring;
import dao.IUserMapper;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Repository
public class MybatisTest {

    /**
     * 入门案例
     */
    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ConfigurationSpring.class);
        SqlSession sqlSession = annotationConfigApplicationContext.getBean(SqlSession.class);
        //4.使用SqlSession创建Dao接口的代理对象
        IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);

        //5.使用代理对象执行方法
        System.out.println("1:测试不传入参数");
        System.out.println("    1.1 第一种方法，使用映射的mapper文件写sql");
        List<User> usersFindAllXml = userMapper.findAllXml();
        for (User user:usersFindAllXml) {
            System.out.println(user);
        }
        System.out.println("    1.2 第二种方法，把映射的mapper文件删除，直接在接口上用注解写sql");
        List<User> usersFindAllAnnotation = userMapper.findAllAnnotation();
        for (User user:usersFindAllAnnotation) {
            System.out.println(user);
        }

        System.out.println("\n2:测试传入引用参数");
        User user1 = new User();
        user1.setUsername("uesr1");
        user1.setSex("f");
        user1.setBirthday(new Date());
        user1.setAddress("address of user1");

        System.out.println("    2.1 第一种方法，使用映射的mapper文件写sql");
        System.out.println("        测试insertXml");
        userMapper.insertXml(user1);
        for (User user:userMapper.findAllXml()) {
            System.out.println(user);
        }
        System.out.println("        测试updateXml 更新address");
        user1.setAddress("new address");
        userMapper.updateXml(user1);
        for (User user:userMapper.findAllXml()) {
            System.out.println(user);
        }

        System.out.println("    2.2 第二种方法，把映射的mapper文件删除，直接在接口上用注解写sql");
        System.out.println("        测试insertAnnotation");
        userMapper.insertAnnotation(user1);
        for (User user:userMapper.findAllAnnotation()) {
            System.out.println(user);
        }

        System.out.println("        测试updateAnnotation 更新username");
        user1.setUsername("new username");
        userMapper.updateAnnotation(user1);
        for (User user:userMapper.findAllAnnotation()) {
            System.out.println(user);
        }


        System.out.println("\n3:测试传入基本类型参数");
        System.out.println("    3.1 第一种方法，使用映射的mapper文件写sql");
        List<User> usersByIDXml = userMapper.findByIdXml(50);
        for (User user:usersByIDXml) {
            System.out.println(user);
        }
        System.out.println("    3.2 第二种方法，把映射的mapper文件删除，直接在接口上用注解写sql");
        List<User> usersByIDAnnotation = userMapper.findByIdAnnotation(50);
        for (User user:usersByIDAnnotation) {
            System.out.println(user);
        }

        sqlSession.commit();  // 提交事务 注意如果没有提交，是不会落库的
        //6.释放资源
        sqlSession.close();
//        in.close();
    }
}

