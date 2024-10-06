package com.library.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BorrowedBooks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long borrowedBookId;
	
	
	private Long bookId;

	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	
	public Long getBorrowedBookId() {
		return borrowedBookId;
	}

	public void setBorrowedBookId(Long borrowedBookId) {
		this.borrowedBookId = borrowedBookId;
	}


	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}
