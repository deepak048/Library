package com.library.ServiceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.Dao.UserDao;
import com.library.Entity.User;
import com.library.Service.UserService;
import com.library.Utility.UserResponse;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	private Set<String> blacklistedTokens = new HashSet<>();
	
	@Override
	public UserResponse saveUser(User user) throws Exception {
		return userDao.saveUser(user);
	}

	@Override
	public User loginUser(String userName, String password) {
		// TODO Auto-generated method stub
		return userDao.loginUser(userName,password);
	}

	@Override
	public boolean isTokenBlacklisted(String jwt) {
		return blacklistedTokens.contains(jwt);
	}

	@Override
	public void blacklistToken(String token) {
		 blacklistedTokens.add(token);
		
	}

}
