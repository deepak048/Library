package com.library.Utility;

import java.util.List;

import com.library.Entity.Book;
import com.library.Entity.User;

public class UserResponse {
    private String message;
    private boolean success;
    private User user;
    private String jwt;
    private List<Book> books;
 
    private UserResponse(Builder builder) {
        this.message = builder.message;
        this.success = builder.success;
        this.user = builder.user;
        this.jwt = builder.jwt;
        this.books = builder.books;
    }

   
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getJwt() {
        return jwt;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public static class Builder {
        private String message;
        private boolean success;
        private User user;
        private String jwt;
        private List<Book> books;
        
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
        
        public Builder setJwt(String jwt) {
            this.jwt = jwt;
            return this;
        }
        
        public Builder setBooks(List<Book> book) {
        	 this.books = book;
             return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }
}
