package com.itheima;

import com.itheima.dao.impl.BookDaoImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class AppForLifeCycle
{
    public static void main( String[] args )
        {
            System.out.println( "Hello World!" );
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContent.xml");
            BookDaoImpl bookDao = (BookDaoImpl)applicationContext.getBean("bookDao");
            bookDao.save();
            applicationContext.registerShutdownHook();//或者注册钩子
//            applicationContext.close();//或者把容器关掉，才能在main中显示调用销毁方法。
        }
}
