package com.library.Utility;

import com.library.Entity.User;

public class UserResponse {
    private String message;
    private boolean success;
    private User user;

 
    private UserResponse(Builder builder) {
        this.message = builder.message;
        this.success = builder.success;
        this.user = builder.user;
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

  
    public static class Builder {
        private String message;
        private boolean success;
        private User user;

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

        public UserResponse build() {
            return new UserResponse(this);
        }
    }
}
