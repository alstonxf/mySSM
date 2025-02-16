package com.itheima.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 数据源配置类
 * 该类用于配置数据库连接池，使用 Druid 作为数据源。
 */
@Component("dataSource1") // 将该类作为 Spring 组件进行管理，并指定 Bean 名称为 "dataSource1"
public class DataSourceConfig {

    // 通过 @Value 注解从配置文件（如 myjdbc.properties）中读取数据库连接信息
    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url1}")
    private String url;

    @Value("${username1}")
    private String user;

    @Value("${password1}")
    private String password;

    /**
     * 创建并返回 Druid 数据源 Bean
     * 该方法会被 Spring 管理，并在需要时提供一个 DruidDataSource 实例。
     *
     * @return 配置完成的 DruidDataSource 数据源
     */
    @Bean
    public DruidDataSource getDruidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();

        // 配置数据库连接信息
        druidDataSource.setDriverClassName(this.driverClassName);
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.user);
        druidDataSource.setPassword(this.password);

        return druidDataSource;
    }

    // Getter 和 Setter 方法，用于获取和设置数据库连接信息

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUsername(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 重写 toString 方法，方便打印数据库配置信息
     *
     * @return 数据源配置信息的字符串表示
     */
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
