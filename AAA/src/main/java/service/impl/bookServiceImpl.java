package service.impl;

import dao.impl.userDaoImpl;
import service.bookService;
import dao.impl.bookDaoImpl;

public class bookServiceImpl implements bookService {

    bookDaoImpl bookDaoImpl;
    userDaoImpl userDaoImpl;

    public bookServiceImpl(){
    }

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
        userDaoImpl.getUserList();
        userDaoImpl.getUserMap();
        userDaoImpl.getUserSet();
        userDaoImpl.getUserPros();
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
