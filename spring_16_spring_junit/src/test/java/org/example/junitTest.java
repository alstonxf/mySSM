package org.example;

import com.itheima.dao.BookDao;
import com.itheima.dao.service.BookService;
import com.itheima.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.itheima.config.SpringConfig.class)
public class junitTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookDao bookDao;
    
    @Test
    public void testBookService(){
        List<Map<String, Object>> save = bookService.save();
        System.out.println(save);
    }

    @Test
    public void testBookDao(){
        List<Book> save = bookDao.save();
        System.out.println(save);
    }
}
