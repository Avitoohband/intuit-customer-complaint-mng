package com.intuit.complaintservice.aspect;

import com.intuit.complaintservice.dto.ComplaintRequest;
import com.intuit.complaintservice.exception.ComplaintException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class ValidationAspect {

    @Pointcut("execution(* com.intuit.complaintservice.controller.*.*(..))")
    public void controllerPointcut() {
    }

    ;

    @Before("controllerPointcut()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof ComplaintRequest) {
                validateComplaintRequest((ComplaintRequest) arg);
            }
        }
    }

    private void validateComplaintRequest(ComplaintRequest complaintRequest) {
        if (Objects.isNull(complaintRequest.getUserId())) {
            throw new ComplaintException("Invalid user id", HttpStatus.BAD_REQUEST);
        }
        else if (Objects.isNull(complaintRequest.getPurchaseId())) {
            throw new ComplaintException("Invalid purchase id", HttpStatus.BAD_REQUEST);
        }
        else if (Objects.isNull(complaintRequest.getSubject()) || complaintRequest.getSubject().length() < 3) {
            throw new ComplaintException("Must have a valid subject", HttpStatus.BAD_REQUEST);
        }
        else if (Objects.isNull(complaintRequest.getComplaint()) || complaintRequest.getComplaint().length() < 3) {
            throw new ComplaintException("Must have a valid complaint content", HttpStatus.BAD_REQUEST);
        }
    }
}
