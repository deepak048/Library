package com.library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.Entity.Book;
import com.library.Entity.User;
import com.library.Security.JwtUtil;
import com.library.Service.LibraryService;
import com.library.Service.UserService;
import com.library.Utility.UserResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
    private LibraryService libraryService;

	@PostMapping("/register")
	public UserResponse registerUser(@RequestBody User user) throws Exception {

		UserResponse userResponse = userService.saveUser(user);

		return userResponse;
	}

	 @PostMapping("/login")
	    public UserResponse loginUser(@RequestBody User loginRequest, HttpSession session) {
	        // Validate the user credentials using UserService or UserRepository
	        User user = userService.loginUser(loginRequest.getUserName(), loginRequest.getPassword());

	        if (user == null) {
	            return new UserResponse.Builder()
	                    .setMessage("Invalid username or password.")
	                    .setSuccess(false)
	                    .build();
	        }

	        // Generate JWT
	        session.setAttribute("userId", user.getId());
	        
	        final String jwt = jwtUtil.generateToken(user.getId(),loginRequest.getUserName());

	        // Return UserResponse with JWT token
	        return new UserResponse.Builder()
	                .setMessage("Login successful!")
	                .setSuccess(true)
	                .setJwt(jwt)
	                .build();
	    }
	 
	 
	 
	 @GetMapping("/books")
	    public List<Book> getListOfBooks() {
	        return libraryService.getListOfBooks();
	    }
	 
	 @PostMapping("/logout")
	    public String logout(@RequestHeader("Authorization") String authorizationHeader,HttpSession session) {
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            String token = authorizationHeader.substring(7);
	            userService.blacklistToken(token);
	            session.invalidate();
	            return "Successfully logged out";
	        }
	        return "Invalid request";
	    }
}
