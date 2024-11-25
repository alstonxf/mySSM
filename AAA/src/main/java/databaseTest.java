import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class databaseTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("book.xml");
        //注意：如果使用类型注入。必须要确保IOC容器中该类型对应的bean对象只能有一个。
        DruidDataSource dataSource = classPathXmlApplicationContext.getBean(com.alibaba.druid.pool.DruidDataSource.class);

        System.out.println(dataSource.getUrl());
        System.out.println(dataSource.getUsername());
        System.out.println(dataSource.getPassword());

        // 验证连接有效性的 SQL
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        // 获取连接
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("数据库连接成功：" + connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据源
            dataSource.close();
        }


    }
}
