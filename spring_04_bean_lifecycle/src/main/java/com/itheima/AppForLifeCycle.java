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
            //通过注册钩子或者调用close方法都可以调用销毁方法。
            //区别：close()是在调用的时候关闭，registerShutdownHook()是在JVM退出前调用关闭。
            applicationContext.registerShutdownHook();//或者注册钩子
            System.out.println("还没有close哦");//注册钩子后，这一行还是会被执行，然后在jvm关闭前才会执行销毁方法。
//            applicationContext.close();//或者把容器关掉，才能在main中显示调用销毁方法。
        }
}
