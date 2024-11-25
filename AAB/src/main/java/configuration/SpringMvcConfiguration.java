package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(value = "controler")
@EnableWebMvc //开启json数据类型自动转换
public class SpringMvcConfiguration {


}
