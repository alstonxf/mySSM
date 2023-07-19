package com.itheima.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.itheima")
@PropertySource("myjdbc.properties")
//@Import(DataSource.class)
public class SpringConfig {
}
