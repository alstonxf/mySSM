package com.example.demo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Hello {
    @RequestMapping("/hello")
    public String hello()  throws Exception{
//        只是在页面展示文字，没有设置格式啥的
        return "Hello ,Spring Boot!";
    }
}
