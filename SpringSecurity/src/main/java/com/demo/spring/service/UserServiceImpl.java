package com.demo.spring.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.model.User;
import com.demo.spring.repository.UserRepository;
import com.demo.spring.util.PasswordUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired private UserRepository userRepository;

	@Override
	public User save(User user) {
		String password=PasswordUtil.getPasswordHash(user.getPassword());
		user.setPassword(password);
		user.setCreatedDate(new Date());
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String name) {
		
		return userRepository.findByEmailIgnoreCase(name);
	}

	@Override
	public User getUserByEmailOrPhoneNumber(String emailOrPhone) {
		// TODO Auto-generated method stub
		return userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone);
	}
	
	
	
	
	
}
