package com.itheima;

import com.itheima.service.impl.BookServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class AppForDIConstructor
{
    public static void main( String[] args )
        {
            System.out.println( "Hello World!" );
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContent.xml");
            BookServiceImpl bookService = (BookServiceImpl)applicationContext.getBean("bookService");
            bookService.save();

        }
}
