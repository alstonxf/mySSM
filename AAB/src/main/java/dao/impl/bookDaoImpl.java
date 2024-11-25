package dao.impl;

import dao.bookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;

@Component("bookDaoImpl1")
@Scope("singleton")
public class bookDaoImpl implements bookDao {
    @Value("红楼梦")
    String bookName;
    @Value("曹雪芹")
    String author;
    @Value("56.99")
    BigDecimal price;

    @Autowired
    public bookDaoImpl(){

    }

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

    @PostConstruct
    public void init1(){
        System.out.println("bookDaoImpl执行init方法");
    }

    @PreDestroy
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
