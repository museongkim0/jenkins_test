package com.example.backend.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class SimpleAop {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.example.backend..*.*(..))")
    private void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        log.trace("TRACE: {} - {} 실행 시작", className, methodName);
        log.debug("DEBUG: {} - {} 실행 시작", className, methodName);
        log.info("INFO: {} - {} 실행 시작", className, methodName);
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();
            log.info("INFO: {} - {} 실행 완료 ({}ms)", className, methodName, stopWatch.getTotalTimeMillis());
            return result;
        } catch (Exception e) {
            log.error("ERROR: {} - {} 실행 중 예외 발생: {}", className, methodName, e.getMessage());
            throw e;
        }
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        log.debug("DEBUG: {} - {} 성공적으로 실행됨, 반환 값: {}", className, methodName, result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        log.warn("WARN: {} - {} 예외 발생: {}", className, methodName, ex.getMessage());
        log.error("ERROR: {} - {} 예외 발생: {}", className, methodName, ex.getMessage());
    }
}
