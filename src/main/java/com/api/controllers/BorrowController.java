package com.api.controllers;

import com.api.dto.BorrowedBookDTO;
import com.api.entities.Book;
import com.api.services.BorrowService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrowBook")
    public ResponseEntity<String> borrowBook(@RequestBody Map<String, Integer> body) {
        try {
            int pid = body.get("pid");
            int bookId = body.get("bookId");
            String response = borrowService.borrowBook(pid, bookId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error " + e.getMessage());
        }
    }
    @PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestBody Map<String, Integer> body) {
	 try {
		    Integer pidStr = body.get("pid");
	        Integer bookIdStr = body.get("bookId");

	        if (pidStr == null  || bookIdStr == null) {
	            throw new IllegalArgumentException("pid and bookId must not be blank.");
	        }
		 int pid=body.get("pid");
		 int bookId=body.get("bookId");
		 if (pid == 0 || bookId == 0) {
			    throw new IllegalArgumentException("Missing pid or bookId");
			}
		 String response =borrowService.returnBook(pid,bookId);
		 return ResponseEntity.ok(response);
	 } catch(Exception e) {
		return ResponseEntity.status(400).body("Error "+e.getMessage()); 
	 } 
   }
    @GetMapping("/listBooks/{pid}")
    public ResponseEntity<?> getBooksBorrowedByPatron(@PathVariable int pid) {
        List<BorrowedBookDTO> books = borrowService.getBooksBorrowedByPatron(pid);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books borrowed by patron or does not exist with ID: " + pid);
        }
        return ResponseEntity.ok(books);
    }
}