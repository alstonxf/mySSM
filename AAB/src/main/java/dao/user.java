package dao;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;


public class user {
    int age;
    String name;
    BigDecimal money;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "user{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
