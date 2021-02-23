package com.bright.springboot2.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;

/**
 * @author bright
 * @version 1.0
 * @description
 * @date 2021-02-23 21:39
 */
@Aspect
@Component
public class DaoTimeInterceptor {

	private static final String POINT = "execution (* com.bright.springboot2.controller.*.*(..))";

	@Pointcut(POINT)
	public void recordLog() {
	}

	@Before("recordLog()")
	public void before(JoinPoint joinPoint) {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println(LocalDateTime.now() + " " + className + "." + methodName + " 开始执行");
	}

	@After("recordLog()")
	public void after(JoinPoint joinPoint) {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println(LocalDateTime.now() + " " + className + "." + methodName + " 完成执行");
	}

	@AfterReturning("recordLog()")
	public void afterReturn(JoinPoint joinPoint) {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println(LocalDateTime.now() + " " + className + "." + methodName + " 执行成功");
	}

	@AfterThrowing(value = POINT, throwing = "e")
	public void afterThrow(JoinPoint joinPoint, Exception e) {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println(LocalDateTime.now() + " " + className + "." + methodName + " 抛出异常" + e);
	}

	@Around("recordLog()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object obj = pjp.proceed(pjp.getArgs());
		stopWatch.stop();

		long cost = stopWatch.getTotalTimeMillis();
		if (cost > 0) {
			MethodSignature signature = (MethodSignature) pjp.getSignature();
			String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
			System.out.println(LocalDateTime.now() + " 执行" + methodName + "方法, 用时: " + cost + "ms -----------");
		}
		return obj;
	}

}
