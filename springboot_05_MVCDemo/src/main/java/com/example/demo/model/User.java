package com.example.demo.model;

/**
 * Author:   longzhonghua
 * Date:     3/22/2019 10:42 AM
 */

public class User {
    //定义id
    private long id;
    //定义用户名
    private String name;
    //定义用户年龄
    private  int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
