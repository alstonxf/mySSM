package service.AOP;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class myAop {

    @Pointcut("execution(void service.bookService.getUsersInfo())")
    public void pt1(){};

    @Before("pt1()")
    public void beforeMethod(){
        System.out.println("正在执行AOP的beforeMethod方法");
    }

    @After("pt1()")
    public void afterMethod(){
        System.out.println("正在执行AOP的afterMethod方法");
    }

    @AfterReturning("pt1()")
    public void afterReturningMethod(){
        System.out.println("正在执行AOP的AfterReturning方法");
    }

    @AfterThrowing("pt1()")
    public void afterThrowingMethod(){
        System.out.println("正在执行AOP的AfterThrowing方法");
    }

    @Pointcut("execution(void service.bookService.save())")
    public void pt2(){};

    @Around("pt2()")
    public void AroundMethod(){
        System.out.println("正在执行AroundMethod的Before阶段");
        try{
            System.out.println("正在执行AroundMethod的AfterReturning阶段");
        }catch (Exception e){
            System.out.println("正在执行AfterThrowing的阶段");
        }
        System.out.println("正在执行AroundMethod的After阶段");
    }
}
