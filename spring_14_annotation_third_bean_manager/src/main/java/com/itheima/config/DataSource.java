package com.itheima.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("dataSource")
public class DataSource {
    @Value("${driverClassName}")
    public String driverClassName;
    @Value("${url}")
    public String url;
    @Value("${username}")
    public String user;
    @Value("${password}")
    public String password;

    @Bean
    public DruidDataSource getDruidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        // 如果不使用 Spring 管理数据源，可以手动创建并配置数据源
        druidDataSource.setDriverClassName(this.driverClassName);
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.user);
        druidDataSource.setPassword(this.password);
        return druidDataSource;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
