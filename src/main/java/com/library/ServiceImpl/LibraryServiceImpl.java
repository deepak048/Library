package com.library.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.Dao.LibraryDao;
import com.library.Entity.Book;
import com.library.Service.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService{

	@Autowired
	LibraryDao libraryDao;
	
	@Override
	public List<Book> getListOfBooks() {
		return libraryDao.getListOfBooks();
	}

	@Override
	public List<Book> addBooks(Book book) {
		return libraryDao.addBooks(book);
	}

	@Override
	public List<Book> updateBooks(Book book,Long userId) {
		return libraryDao.updateBooks(book,userId);
	}

	@Override
	public List<Book> borrowBooks(Book book, Long userId) {
		return libraryDao.borrowBooks(book,userId);
	}

	@Override
	public List<Book> userBorrowedBooks(Long userId) {
		return libraryDao.userBorrowedBooks(userId);
	}

	@Override
	public List<Book> addBorrowBooks(Long userId, Long id) {
		return libraryDao.addBorrowBooks(userId,id);
	}

}
