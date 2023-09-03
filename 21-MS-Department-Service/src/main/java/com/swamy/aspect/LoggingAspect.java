package com.swamy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Pointcut(value = "execution(* com.swamy.controller.*.*(..)) || execution(* com.swamy.service.*.*(..))")
	public void appPointcut() {

	}

	@Around(value = "appPointcut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName();

		String className = joinPoint.getTarget().getClass().getName();

		Object[] args = joinPoint.getArgs();

		ObjectMapper mapper = new ObjectMapper();

		log.info("Invoked Method : " + className + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(args));

		Object object = joinPoint.proceed();

		log.info("Returning Back From : " + className + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(object));

		return object;
	}
}
