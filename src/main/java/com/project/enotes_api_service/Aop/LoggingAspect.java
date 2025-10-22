package com.project.enotes_api_service.Aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

//    @Before("execution(* com.project.enotes_api_service.contoller..*(..))")
//    public void beforeController(JoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        String name=signature.getDeclaringType().getSimpleName();
//        String methodName = signature.getName();
//        log.info("Controller Calling:: {} :: {}()",name,methodName);
//    }
//
//    @After("execution(* com.project.enotes_api_service.contoller..*(..))")
//    public void afterController(JoinPoint joinPoint){
//        Signature signature=joinPoint.getSignature();
//        String className = signature.getClass().getSimpleName();
//        String methodName = signature.getClass().getName();
//        log.info("Controller End Calling:: {} :: {}()",className,methodName);
//    }

    @Around("execution(* com.project.enotes_api_service.contoller..*(..))")
    public Object jointPointController(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint);
    }

    @Around("execution(* com.project.enotes_api_service.service..*(..))")
    public Object jointPointService(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint);
    }

    private Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("{} Calling :: {}()",className,methodName);
        Object result = joinPoint.proceed();
        log.info("{} End :: {}()", className,methodName);

        return result;
    }


}
