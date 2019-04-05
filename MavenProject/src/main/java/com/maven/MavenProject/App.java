package com.maven.MavenProject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	ApplicationContext context=new ClassPathXmlApplicationContext("springfile.xml");
    	Vehicle vehicle=(Vehicle)context.getBean("car");
    	vehicle.autowirex();
    	
    	
    	//Aspect Oriented Programming For adding log ,security feature
    	
    	Vehicle bike=(Vehicle)context.getBean("bike");
    	bike.Details();
    	
    	
    	
//    	Tyre t=(Tyre)context.getBean("tyre");
//    	
//    	
//    	System.out.println(t.getBrand());
    }
}
