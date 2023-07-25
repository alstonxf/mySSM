package com.itheima.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
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

        //获取目标对象的签名信息
        Signature signature = proceedingJoinPoint.getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();
        System.out.println("declaringTypeName = " + declaringTypeName);//接口名
        String name = signature.getName(); //方法名
        System.out.println("name = "+name);
        Class declaringType = signature.getDeclaringType();//接口类
        System.out.println("declaringType = " + declaringType);

        proceedingJoinPoint.proceed();
        System.out.println("在执行原始操作后运行-------------------");
        return null;
    }

    @Pointcut("execution(* com.itheima.dao.BookDao.update())")
    private void ptUpdate() {
    }

    @Before("ptUpdate()")
    public void method(){
        System.out.println(System.currentTimeMillis());
    }

    @AfterReturning("ptUpdate()")
    public void method2(){
        System.out.println(" @AfterReturning(\"ptUpdate()\")" +System.currentTimeMillis());
    }


    @Pointcut("execution(* com.itheima.dao.BookDao.select())")
    private void ptSelect() {
    }

    @After("ptSelect()")
    public void methodSelect(){
        System.out.println("这是在select方法运行后执行，无论select方法是否报错，都会执行");
    }

    //获取返回值
    //简便方法
//    @AfterReturning("ptSelect()")
    @AfterReturning(pointcut = "execution(* com.itheima.dao.BookDao.select())", returning = "result")
    public void methodSelect2(Object result){
        System.out.println("这是在select方法正常运行后执行,才会执行，返回结果是"+result);
    }
    @Pointcut("execution(* com.itheima.dao.BookDao.delete(*))")
    private void ptDelete() {
    }

    @Around("ptDelete()")
    public void methodDeleteArgs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //proceedingJoinPoint是Spring AOP提供的一个核心概念，它代表了切面织入的连接点，通常是一个方法的执行。getArgs()是proceedingJoinPoint的方法之一，用于获取方法的参数。
        //args是一个Object[]数组，其中存储了方法执行时的所有参数。每个参数在数组中的位置与方法声明中参数的顺序一致，例如，如果方法有两个参数，那么args[0]将存储第一个参数的值，args[1]将存储第二个参数的值，依此类推。
        Object[] args = proceedingJoinPoint.getArgs();
        System.out.println(args[0]);
        System.out.println("11111111111methodDeleteArgs");
        proceedingJoinPoint.proceed();
        System.out.println("2222222222methodDeleteArgs");
    }

    @Pointcut("execution(* com.itheima.dao.BookDao.delete())")
    private void ptDeleteNoArg() {
    }

    @Around("ptDeleteNoArg()")
    public void methodDeleteNoArgs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //proceedingJoinPoint是Spring AOP提供的一个核心概念，它代表了切面织入的连接点，通常是一个方法的执行。getArgs()是proceedingJoinPoint的方法之一，用于获取方法的参数。
        //args是一个Object[]数组，其中存储了方法执行时的所有参数。每个参数在数组中的位置与方法声明中参数的顺序一致，例如，如果方法有两个参数，那么args[0]将存储第一个参数的值，args[1]将存储第二个参数的值，依此类推。
        System.out.println("11111111111methodDeleteNoArgs");
        proceedingJoinPoint.proceed();
        System.out.println("2222222222methodDeleteNoArgs");
    }


}
