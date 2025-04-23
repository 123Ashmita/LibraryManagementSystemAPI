package com.api.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.api.entities.Book;
import com.api.services.BookService;

@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/home")
	public String home() {
		return "Welcome to this LMS by Ashmita";
	}
	
	@PostMapping("/addBook")
	public ResponseEntity<?> addBook(@RequestBody Book book) {
	    if (bookService.existsByBookId(book.getBookId())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with ID " + book.getBookId() + " already exists.");
	    }
	    Book savedBook = bookService.addBook(book);
//	    book.setAvailable("true");
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

	@GetMapping("/bookList")
	public List<Book> getBooks() {
		return this.bookService.getBooks();
	}
	@DeleteMapping("/deleteBook/{bookId}") 
	public ResponseEntity<String> DeleteBook(@PathVariable int bookId) {
		try {
		 bookService.deleteBook(bookId);
		 return ResponseEntity.ok("Delete book id "+bookId+" successfully");
		} catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
} 
	}
	
}