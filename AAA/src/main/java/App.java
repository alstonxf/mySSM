import dao.impl.bookDaoImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import service.impl.bookServiceImpl;

public class App {
    public static void main(String[] args) {
//        bookServiceImpl bookService = new bookServiceImpl();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("book.xml");//类路径下的XML配置文件
//        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("D:\\myGitProject\\mySMM1\\mySSM\\AAA\\src\\main\\resources\\book.xml");//系统路径下的XML配置文件

        // 使用 Bean 的 id或name 获取 bookService 实例
        bookServiceImpl bookService = (bookServiceImpl) context.getBean("bk1");

        // 调用 bookService 方法
        bookService.save();
        bookService.getUsersInfo();

        // 注册 JVM 关闭钩子，确保 JVM 退出时执行destroy 方法
        context.registerShutdownHook();

        // 也可以手动调用 context.close()，但在方法调用后进行，这会销毁容器内所有bean，并执行实例的destroy方法。
        /**
         * 注意：用于在应用程序生命周期内手动管理 Bean 的销毁，适合在临时环境中使用，比如开发或测试时，需要立即释放资源的场景。
         * 如果在生产环境中频繁调用 context.close()，可能会出现资源管理不当的情况，尤其在并发请求多的应用中，关闭后再调用其他 Bean 方法容易出现异常。
         */
        // context.close();
    }
}
