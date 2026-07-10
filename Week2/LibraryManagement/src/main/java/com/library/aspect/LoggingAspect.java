package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect handles cross-cutting logging concerns using Spring AOP.
 *
 * Exercise 3: Track method execution times using @Around advice.
 * Exercise 8: Add @Before and @After advice for entry/exit logging.
 *
 * The pointcut targets everything in com.library.service so we don't
 * have to scatter logging calls across every service method.
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Exercise 8: Log a message before any method in BookService runs.
     * Good for auditing which methods get called and with what class context.
     */
    @Before("execution(* com.library.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[LOG] Entering: "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
    }

    /**ded 
     * Exercise 8: Log after the method completes (whether it succeeded or threw).
     */
    @After("execution(* com.library.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[LOG] Exiting:  "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
    }

    /**
     * Exercise 3: Wrap each service call to measure how long it takes.
     * ProceedingJoinPoint lets us actually invoke the target method
     * and capture the time before and after.
     */
    @Around("execution(* com.library.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // call the real method

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[LOG] "
                + joinPoint.getSignature().getName()
                + " completed in " + elapsed + " ms");

        return result;
    }
}
