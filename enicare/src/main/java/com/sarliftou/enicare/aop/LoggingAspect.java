package com.sarliftou.enicare.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* com.sarliftou.enicare.service..*(..)) || execution(* com.sarliftou.enicare.controller..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Avant l'exécution de la méthode : " + joinPoint.getSignature());
        String name = joinPoint.getSignature().getName();
        logger.info("Méthode cible : " + name);
        Object[] args = joinPoint.getArgs();
        logger.info("Arguments : " + Arrays.toString(args));
    }

    @After("execution(* com.sarliftou.enicare.service..*(..)) || execution(* com.sarliftou.enicare.controller..*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        logger.info("Après l'exécution de la méthode : " + joinPoint.getSignature());
        String name = joinPoint.getSignature().getName();
        logger.info("Méthode terminée : " + name);
    }
}