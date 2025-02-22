package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //效果等同于  @Controller + @ResponseBody 的组合
//@Controller
public class HelloWorldController {
    @RequestMapping("/hello")
//    @ResponseBody
    public String hello(){
        return "Hello,Spring Boot!";
    }
}
