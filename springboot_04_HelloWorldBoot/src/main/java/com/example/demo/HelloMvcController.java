package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * mvc模式的控制器
 * */
@Controller
public class HelloMvcController {
    @RequestMapping("/helloworld")
    public String helloWorld (Model model)  throws Exception{
//        返回设定好的视图
        model.addAttribute("mav", "Hello ,Spring Boot!");
        model.addAttribute("myContent", "测试控制器");
        //视图(view)的位置和名称，视图位于example文件夹下，视图文件为hello.html。
        return "example/hello";
    }
}
