package com.accord.logging;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * User : Tanvir Ahmed
 * Date: 11/20/2024.
 */

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.accord.controller..*(.., @org.springframework.web.bind.annotation.RequestBody (*), ..))")
    public Object logRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log Request
        logger.info("Incoming Request: {} {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));

        Object result;

        // Proceed with the method execution
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Error during request processing: {}", throwable.getMessage());
            throw throwable;
        }

        // Log Response
        logger.info("Outgoing Response: {}", result);

        return result;
    }
}
