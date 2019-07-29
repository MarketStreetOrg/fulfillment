package org.katale.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logger {

    @Autowired
    Logger logger;

    @Pointcut("execution(public * org.katale.service..*(..))")
    public void log() {

    }

    @After("log()")
    public void logMethod(JoinPoint point) {
      System.out.println("Logging:"+point.getSignature());
    }

}
