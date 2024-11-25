package dao.impl;

import dao.bookDao;

import java.math.BigDecimal;

public class bookDaoImpl implements bookDao {

    String bookName;
    String author;
    BigDecimal price;
    public bookDaoImpl(String bookName, String author, BigDecimal price){
        this.bookName = bookName;
        this.author = author;
        this.price = price;
    }

    @Override
    public void insert(){
        System.out.println("数据库book执行书籍更新数据");
        System.out.println(this);
    }

    public void init1(){
        System.out.println("bookDaoImpl执行init方法");
    }

    public void destroy1(){
        System.out.println("bookDaoImpl执行destroy方法");
    }

    @Override
    public String toString() {
        return "bookDaoImpl{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
