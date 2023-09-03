package com.swamy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

	@Pointcut(value = "execution(* com.swamy.controller.*.*(..)) || execution(* com.swamy.service.*.*(..))")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object loggingAdvice(ProceedingJoinPoint pjp) throws Throwable {

		String methodName = pjp.getSignature().getName();

		String className = pjp.getTarget().getClass().getName();

		Object[] args = pjp.getArgs();

		ObjectMapper mapper = new ObjectMapper();

		log.info("Invoked Method : " + className + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(args));

		Object object = pjp.proceed();

		log.info("Returned Back From : " + className + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(object));

		return object;
	}
}
