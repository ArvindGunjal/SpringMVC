package com.example.demo.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.UserEntityInter;
import com.example.demo.Entity.UserEntity;



@RestController
@RequestMapping("index")
public class IndexController {

	
	@Autowired
	UserEntityInter repo;
	
	@RequestMapping(value="/insert")
	public String insert(UserEntity user)
	{
		repo.save(user);	
		
		System.out.println(user.getId()+" "+user.getAddress());
		
		return "Data Added";
	}
	
	@RequestMapping("/AllData")
	@ResponseBody				//This service return JSON
	public List<UserEntity> getAllData()
	{
		
		return repo.findAll();
	}
	
	@RequestMapping("/findById")
	@ResponseBody			//This Service return String data by id
	public String findById(@RequestParam int id)
	{
		UserEntity userentityobj= repo.findById(id).orElse(new UserEntity());

		System.out.println(userentityobj.getName()+"Testing");
		return userentityobj.getName();
		

		
	}
	
	
	@RequestMapping("/findByidGr")
	@ResponseBody
	public String findByName(@RequestParam int id)
	{
		List<UserEntity> list=repo.findByIdGreaterThan(id);
						
		System.out.println(list.toString());
		return repo.findByIdGreaterThan(id).toString();
		
		
	}
	
	@RequestMapping("/dataorder")	
	public void dataorder()
	{
		
		System.out.println(repo.datasearch());
	}
	
	@DeleteMapping("/userdelete/{id}")
	public String userdelete(@PathVariable int id)
	{
		UserEntity userdata=repo.getOne(id);
		
		System.out.println(id);
		repo.delete(userdata); 
		
		return "User Deleted";
		
	}
	
	@PutMapping("/updateEntity")
	public List<UserEntity> updateUser(UserEntity user)
	{
		repo.save(user);
		return repo.findAll();
	}
	
}
