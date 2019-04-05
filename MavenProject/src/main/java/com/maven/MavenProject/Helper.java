package com.maven.MavenProject;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class Helper {

	@After("execution(public void autowirex())")
	public void helperMethod()
	{
		System.out.println("Log Recorded");
	}
	
	
}
