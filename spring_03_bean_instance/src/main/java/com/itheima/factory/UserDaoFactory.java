package com.itheima.factory;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
//实例工厂创建对象
public class UserDaoFactory {
    //唯一区别少了static
    public UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
