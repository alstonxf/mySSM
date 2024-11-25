import configuration.BookConfiguration;
import dao.impl.userDaoImpl;
import dao.user;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.impl.bookServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)

//相当于完成了容器的初始化  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BookConfiguration.class);//类路径下的XML配置文件
@ContextConfiguration(classes = {configuration.BookConfiguration.class})
//@ContextConfiguration(locations = {"...xml"})
public class AppTest {
    @Autowired
    bookServiceImpl bookService;
    @Autowired
    userDaoImpl userDaoImpl1;

    @Test
    public void databaseTest(){
        // 使用 Bean 的 id或name 获取 bookService 实例
        bookService.save();
    }

    @Test
    public void userDaoImplTest(){
        userDaoImpl1.insert();
    }


}
