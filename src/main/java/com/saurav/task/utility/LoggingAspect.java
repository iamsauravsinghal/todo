package com.saurav.task.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	
	@AfterThrowing(pointcut = "execution(* com.saurav.task.service.*Impl.*(..))", throwing = "exception")
	public void logServiceException(Exception exception) {
		Logger logger = LogManager.getLogger(this.getClass());
		if(exception.getMessage()!=null && 
				(exception.getMessage().contains("Service"))){
			logger.error(exception.getMessage());
		}
		else{
			logger.error(exception.getMessage(), exception);
		}

	}
	
	@AfterThrowing(pointcut = "execution(* com.saurav.task.dao.*Impl.*(..))", throwing = "exception")
	public void logDaoException(Exception exception) {
		Logger logger = LogManager.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);

	}
	}