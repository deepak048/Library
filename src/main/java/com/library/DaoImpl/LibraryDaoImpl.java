package com.library.DaoImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.library.Dao.LibraryDao;
import com.library.Entity.Book;
import com.library.Entity.BorrowedBooks;
import com.library.Entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class LibraryDaoImpl implements LibraryDao {

	@Autowired
	private EntityManager entityManager;

	public long countBooks() {
		TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b", Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<Book> getListOfBooks() {
		TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);

		List<Book> books = query.getResultList();

		List<Book> availableBooks = books.stream().filter(book -> book.getNoOfBooks() > 0).collect(Collectors.toList());

		return availableBooks;
	}

	@Override
	@Transactional
	public List<Book> addBooks(Book book) {
		entityManager.persist(book);
		return getListOfBooks();
	}

	@Override
	@Transactional
	public List<Book> updateBooks(Book book, Long userId) {

		Book existingBook = entityManager.find(Book.class, book.getId());

		if (existingBook != null) {
			existingBook.setNoOfBooks(existingBook.getNoOfBooks() + 1);

			entityManager.merge(existingBook);
		}
		
		TypedQuery<BorrowedBooks> query = entityManager.createQuery(
		        "SELECT bb FROM BorrowedBooks bb WHERE bb.bookId = :bookId AND bb.user.id = :userId", BorrowedBooks.class);
		    query.setParameter("bookId", book.getId());
		    query.setParameter("userId", userId);

		    List<BorrowedBooks> borrowedBooksList = query.getResultList();

		    if (!borrowedBooksList.isEmpty()) {
		        for(BorrowedBooks borrowedBook : borrowedBooksList) {
		        	 entityManager.remove(borrowedBook); 
		        };
		       
		    }

		

		return userBorrowedBooks(userId);
	}

	@Override
	@Transactional
	public List<Book> borrowBooks(Book book, Long userId) {
		Book existingBook = entityManager.find(Book.class, book.getId());
		List<Book> books  = userBorrowedBooks(userId);
		if(books.size()>=2) {
			throw new IllegalArgumentException("Max 2 Books can be borrowed by single user");
		}
		if (existingBook != null) {
			existingBook.setNoOfBooks(existingBook.getNoOfBooks() - 1);
			entityManager.merge(existingBook);
		}

		return getListOfBooks();
	}

	@Override
	public List<Book> userBorrowedBooks(Long userId) {
		String sql = "SELECT b.* " + "FROM borrowed_books bb " + "JOIN book b ON b.id = bb.book_id "
				+ "JOIN users u ON u.id = bb.user_id " + "WHERE u.id = :userId";

		Query query = entityManager.createNativeQuery(sql, Book.class);
		query.setParameter("userId", userId);
		List<Book> books = query.getResultList();

		return query.getResultList();
	}

	@Override
	@Transactional
	public List<Book> addBorrowBooks(Long userId, Long id) {
		Book existingBook = entityManager.find(Book.class, id);
		
		if (existingBook != null) {
			existingBook.setNoOfBooks(existingBook.getNoOfBooks() - 1);
			entityManager.merge(existingBook);
		}
		
		
		
		List<Book> borrowedBooks = userBorrowedBooks(userId);
		boolean isAlreadyBorrowed = borrowedBooks.stream().anyMatch(b -> b.getId().equals(id));
		if (!isAlreadyBorrowed) {
			User user = entityManager.find(User.class, userId);
			BorrowedBooks books = new BorrowedBooks();
			books.setBookId(id);
			books.setUser(user);
			entityManager.persist(books);
		} else {
			 throw new IllegalArgumentException("Book with ID " + id + " is already borrowed by user with ID " + userId);
		}
		return userBorrowedBooks(userId);
	}
}
