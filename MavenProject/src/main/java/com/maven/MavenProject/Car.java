package com.maven.MavenProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car implements Vehicle{

	@Autowired
	 private Tyre tyre;
	
	public void display()
	{
		System.out.println("Msg from Car Class");
	}

	public void Details() {
          System.out.println("Msg Frok Car");
		
	}
	 public void autowirex()
	  {
		  System.out.println(tyre);
	  }
}
