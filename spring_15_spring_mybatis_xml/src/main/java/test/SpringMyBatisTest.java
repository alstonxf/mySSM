package test;

import com.itheima.domain.Book;
import com.itheima.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SpringMyBatisTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService = context.getBean(BookService.class);

        // 添加一本书籍
        Book book = new Book();
        book.setName("Spring与MyBatis整合");
        book.setAuthor("张三");
        book.setPrice(79.9);
        bookService.save(book);

        // 查询所有书籍
        List<Book> books = bookService.findAll();
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }
}
