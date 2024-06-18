package com.itheima.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProjectAdvice {
    //匹配业务层的所有方法
    @Pointcut("execution(* com.itheima.service.*Service.*(..))")
    private void servicePt(){}

    //设置环绕通知，在原始操作的运行前后记录执行时间
    @Around("ProjectAdvice.servicePt()")
    public void runSpeed(ProceedingJoinPoint pjp) throws Throwable {
        //获取执行的签名对象
        Signature signature = pjp.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
           pjp.proceed();
        }
        long end = System.currentTimeMillis();
        System.out.println("百次执行："+ className+"."+methodName+"---->" +(end-start) + "ms");
    }
    @Pointcut("execution(* com.itheima.service.*Service.*(..))")
    private void serviceBefore(){}

    @Before("ProjectAdvice.serviceBefore()")
    public void startMessage(){
        System.out.println("执行前打印");
    }
    @Pointcut("execution(* com.itheima.service.*Service.*(..))")
    private void serviceAfterReturn(){}
    @AfterReturning("ProjectAdvice.serviceAfterReturn()")
    public void endMessage(){
        System.out.println("return后打印");
    }

}
