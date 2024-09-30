package com.library.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.Entity.User;
import com.library.Service.UserService;
import com.library.Utility.UserResponse;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public UserResponse registerUser(@RequestBody User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		UserResponse userResponse = userService.saveUser(user);
		
		return userResponse;
	}

	@PostMapping("/login")
    public String loginUser(@RequestBody User loginRequest) {
        User user = userService.loginUser(loginRequest.getUserName(), loginRequest.getPassword());
        if (user != null) {
            return "Login successful!";
        } else {
            return "Invalid username or password.";
        }
    }
}
