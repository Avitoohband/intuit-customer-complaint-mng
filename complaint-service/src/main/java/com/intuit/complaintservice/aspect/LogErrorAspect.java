package com.intuit.complaintservice.aspect;

import com.intuit.complaintservice.exception.ComplaintException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogErrorAspect {

    @Pointcut("execution(* com.intuit.complaintservice.controller.*.*(..))")
    public void controllerPointcut() {
    }

    @AfterThrowing(value = "controllerPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex){
        if(ex instanceof ComplaintException complaintException){
            log.error("Exception Occurs in: {}.{}.\nmessage: {}.\nwith status: {}.",
                    joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(),
                    complaintException.getMessage(),
                    complaintException.getStatus());
        }else {
            log.error("Exception Occurs in: {}.{} with cause: {}. message: {}",
                    joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(),
                    ex.getCause() != null ? ex.getCause() : "NULL",
                    ex.getMessage()
            );
        }
    }
}
