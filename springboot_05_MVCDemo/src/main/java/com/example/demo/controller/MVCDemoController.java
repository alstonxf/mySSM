package com.example.demo.controller;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Random;

/**
 * Author:   longzhonghua
 * Date:     3/22/2019 10:42 AM
 */
@Controller
public class MVCDemoController {
    @Autowired
    private UserService userService;

    //映射URL地址
    @GetMapping("/mvcdemo")
    public ModelAndView hello() {
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义带有毫秒的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        // 格式化当前时间
        String formattedTime = currentTime.format(formatter);
        // 输出格式化后的时间
        System.out.println("当前时间（带毫秒）: " + formattedTime);

        //实例化对象
//        User user=new User();
//        user.setName("zhonghua " + formattedTime );
//        user.setAge(28);
        User user = userService.getUserById(1);

        // 创建 ModelAndView 对象
        ModelAndView modelAndView = new ModelAndView();

        // 设置视图的名称（视图解析器会根据视图名称查找对应的视图）,会关联mvcdemo1.html
        modelAndView.setViewName("mvcdemo1");

        // 添加模型数据（以键值对的形式）
        modelAndView.addObject("user",user);

        return modelAndView;
    }

    @GetMapping("/engNm")
    @ResponseBody
    public String getEnglishName() {
        return "alston";
    }
}
