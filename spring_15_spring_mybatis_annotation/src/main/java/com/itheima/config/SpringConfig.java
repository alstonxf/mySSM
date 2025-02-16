package com.itheima.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring 配置类
 * 该类用于配置 Spring 容器的基本信息，包括组件扫描、属性文件加载和其他配置类的导入。
 */
@Configuration // 标记为 Spring 配置类，相当于 XML 配置中的 <beans> 标签
@ComponentScan("com.itheima") // 扫描指定包下的 Spring 组件（如 @Component、@Service、@Repository、@Controller 等）
@PropertySource("myjdbc.properties") // 加载外部属性文件（如数据库连接配置）
@Import({MybatisConfig.class}) // 导入 MyBatis 相关的配置类
public class SpringConfig {
}
