package com.itheima.controller;

import com.itheima.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class modelAndViewController {

    @GetMapping("/mvcdemo")
    public ModelAndView hello() {
        // 创建 User 对象
        User user = new User();
        user.setName("zhonghua");
        user.setAge(28);

        // 创建 ModelAndView 对象
        ModelAndView modelAndView = new ModelAndView();

        // 设置视图的名称
        modelAndView.setViewName("page.jsp");

        // 添加模型数据
        modelAndView.addObject("user", user);

        return modelAndView;
    }

}
