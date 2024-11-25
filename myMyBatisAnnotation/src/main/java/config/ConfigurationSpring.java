package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("domain")
@Import(SqlSessionNew.class)
public class ConfigurationSpring {

}
