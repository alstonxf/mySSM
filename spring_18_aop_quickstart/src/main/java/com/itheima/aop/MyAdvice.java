package com.itheima.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(* com.itheima.dao.BookDao.save())")
    private void pt() {
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("在执行原始操作前运行-------------------");
        proceedingJoinPoint.proceed();
        System.out.println("在执行原始操作后运行-------------------");
        return null;
    }

    @Pointcut("execution(* com.itheima.dao.BookDao.update())")
    private void pt2() {
    }

    @Before("pt2()")
    public void method(){
        System.out.println("update时间："+System.currentTimeMillis());
    }
}
