package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.User;
import com.demo.spring.model.UserFiles;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmailIgnoreCase(String username);
	User findByEmailOrPhoneNumber(String email,String phoneNumber);

	@Query("select f from UserFiles as f where f.slider.id=?1")
	List<UserFiles> findFilesByUserId(Long userId);
}
