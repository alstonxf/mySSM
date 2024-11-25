package service.impl;

import dao.impl.userDaoImpl;
import dao.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import service.bookService;
import dao.impl.bookDaoImpl;

import java.util.List;

@Service("bk1")
public class bookServiceImpl implements bookService {

    @Autowired
    @Qualifier("userDaoImpl1")
    userDaoImpl userDaoImpl;

    @Autowired
    @Qualifier("bookDaoImpl1")
    bookDaoImpl bookDaoImpl;

    public bookServiceImpl(){
    }

    @Autowired
    public bookServiceImpl(bookDaoImpl bookDaoImpl,userDaoImpl userDaoImpl){
        this.bookDaoImpl = bookDaoImpl;
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public void save() {
        System.out.println("用户："+userDaoImpl.getUserName() +" \n订阅的书名:《"+ bookDaoImpl.getBookName()+ "》  作者："+bookDaoImpl.getAuthor());
        userDaoImpl.insert();
        bookDaoImpl.insert();
    }

    @Override
    public void getUsersInfo() {

        List<user> userList = userDaoImpl.getUserList();
        for (int i = 0; i < userList.size(); i++) {
            user user = userList.get(i);
            System.out.println(user);
        }

        userDaoImpl.getUserMap();
        userDaoImpl.getUserSet();
        userDaoImpl.getUserPros();
        // 故意抛出异常 用于测试AOP的AfterThrowing方法
//        throw new RuntimeException("获取用户信息失败！");
    }



    public dao.impl.bookDaoImpl getBookDaoImpl() {
        return bookDaoImpl;
    }

    public dao.impl.userDaoImpl getUserDaoImpl() {
        return userDaoImpl;
    }

    public void setUserDaoImpl(dao.impl.userDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    public void setBookDaoImpl(bookDaoImpl bookDaoImpl){
        this.bookDaoImpl = bookDaoImpl;
    }

}
