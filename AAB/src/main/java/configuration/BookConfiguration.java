package configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({"dao","service"})
@PropertySource("db.properties")
@Import({jdbcConfiguration.class})
//强制使用 CGLIB 代理
//如果您需要将代理对象转换为具体的实现类（例如 bookServiceImpl），可以配置 Spring AOP 强制使用 CGLIB 代理。CGLIB 代理生成的对象是目标类的子类，因此支持强制类型转换。
//添加参数proxyTargetClass = true
@EnableAspectJAutoProxy(proxyTargetClass = true)

public class BookConfiguration {

}
