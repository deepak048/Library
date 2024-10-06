package com.library.Service;

import java.util.List;

import com.library.Entity.Book;

public interface LibraryService {

	List<Book> getListOfBooks();

	List<Book> addBooks(Book book);

	List<Book> updateBooks(Book book, Long userId);

	List<Book> borrowBooks(Book book, Long userId);

	List<Book> userBorrowedBooks(Long userId);

	List<Book> addBorrowBooks(Long userId, Long id);

}
