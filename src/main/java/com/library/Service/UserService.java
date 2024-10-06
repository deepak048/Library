package com.library.Service;

import com.library.Entity.User;
import com.library.Utility.UserResponse;

public interface UserService {

	UserResponse saveUser(User user) throws Exception;

	User loginUser(String userName, String password);

	boolean isTokenBlacklisted(String jwt);

	void blacklistToken(String token);

}
