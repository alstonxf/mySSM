package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author longzhonghua
 * @data 2019/02/05 20:44
 */
@RestController
public class GetPersonInfoController {

    /**
     默认配置文件名和加载顺序：
     1、application.properties 或 application.yml：
     这是Spring Boot的默认配置文件名。Spring Boot会首先查找这两个文件并加载配置。你可以使用 .properties 或 .yml 格式，根据你的需要选择。
     默认位置是在 src/main/resources 目录下。

     2、config/application.properties 或 config/application.yml：
     你还可以将配置文件放在 config 目录下，这样Spring Boot会自动扫描该目录。

     3、application.properties 或 application.yml 指定活动的配置文件
     */
    //获取配置文件中的age
    @Value("${age}")
    private int age;

    //获取配置文件中的name
    @Value("${name}")
    private String name;

    @GetMapping("/getage")
    public int getAge() {
        return age;
    }

    @GetMapping("/getname")
    public String getName() {
        return name;
    }

    @Autowired
    private GetPersonInfoProperties getPersonInfoProperties;

    @GetMapping("/getpersonproperties")
    public String getpersonproperties() {
        return getPersonInfoProperties.getName()+getPersonInfoProperties.getAge();
    }
}
