package com.itheima;

import com.itheima.dao.impl.BookDaoImpl;

/**
 * Hello world!
 *
 */
public class App 
    {
        public static void main( String[] args )
            {
                System.out.println( "Hello World!" );
                new BookDaoImpl().save();
            }
    }
