package com.api.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.api.ws.entity.Users;

@Transactional
public interface UsersDao extends CrudRepository<Users, Long> {
	
	public Users findByuserId(int userId);
	public Users findByuserName(String userName);
	public List<Users> findAll();

}
