package com.library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.Entity.Book;
import com.library.Security.JwtUtil;
import com.library.Service.LibraryService;
import com.library.Utility.UserResponse;

import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RequestMapping("/api/library")
@RestController
public class LibraryController {

	@Autowired
    private LibraryService libraryService;

	@Autowired
	private JwtUtil jwtUtil;
	
    @GetMapping("/books")
    public List<Book> getListOfBooks() {
        return libraryService.getListOfBooks();
    }
    
    @GetMapping("/getUserId")
    public Long getUserId(String token) {
        // Remove "Bearer " from the token string
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Extract the user ID from the token
        Long userId = jwtUtil.extractUserId(token);
        return userId;
    }
    
    @PostMapping("/addBooks")
	public List<Book> addBooks(@RequestBody Book book) throws Exception {

    	List<Book> bookList = libraryService.addBooks(book);

		return bookList;
	}
    
    @PostMapping("/returnBooks")
   	public List<Book> updateBooks(@RequestBody Book book,@RequestHeader("Authorization") String token) throws Exception {
    	Long userId = getUserId(token);
       	List<Book> bookList = libraryService.updateBooks(book,userId);

   		return bookList;
   	}
    
    @PostMapping("/borrowBooks")
   	public UserResponse borrowBooks(@RequestBody Book book,@RequestHeader("Authorization") String token) throws Exception {
    	Long userId = getUserId(token);
    	List<Book> bookList = null;
    	try {
    		 bookList = libraryService.borrowBooks(book,userId);
    		 return new UserResponse.Builder()
 	                .setMessage("Success")
 	                .setSuccess(false)
 	                .setBooks(bookList)
 	                .build();
		} catch (Exception e) {
			return new UserResponse.Builder()
	                .setMessage(e.getMessage())
	                .setSuccess(false)
	                .setBooks(bookList)
	                .build();
		}
       	

   	}
    
    @PostMapping("/userBorrowedBooks")
   	public List<Book> userBorrowedBooks(@RequestHeader("Authorization") String token) throws Exception {
    	Long userId = getUserId(token);

       	List<Book> bookList = libraryService.userBorrowedBooks(userId);

   		return bookList;
   	}
    
    @PostMapping("/addBorrowBooks")
   	public UserResponse addBorrowBooks(@RequestBody Book book,@RequestHeader("Authorization") String token) throws Exception {
    	List<Book> bookList = null;
    	Long userId = getUserId(token);
    	try {
    		 bookList = libraryService.addBorrowBooks(userId,book.getId());
		} catch (Exception e) {
			System.out.println(e);
			 return new UserResponse.Builder()
		                .setMessage("Book is already borrowed")
		                .setSuccess(false)
		                .setBooks(bookList)
		                .build();
		}
       	
    	 return new UserResponse.Builder()
	                .setMessage("Login successful!")
	                .setMessage("Bood added to Borrowed List")
	                .setBooks(bookList)
	                .build();

   	}
    
    
}
