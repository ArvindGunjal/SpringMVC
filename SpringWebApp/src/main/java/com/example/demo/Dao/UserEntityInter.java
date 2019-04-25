package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.UserEntity;

public interface UserEntityInter extends JpaRepository<UserEntity, Integer>{

	List<UserEntity>findByIdGreaterThan(int id);
	
	@Query("from UserEntity order by name")
	List<UserEntity>datasearch();
}
