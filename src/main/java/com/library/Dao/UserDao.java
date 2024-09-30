package com.library.Dao;

import com.library.Entity.User;
import com.library.Utility.UserResponse;

public interface UserDao {

	UserResponse saveUser(User user) throws Exception;

	User loginUser(String userName, String password);

	boolean isUserExist(String userName);

}
