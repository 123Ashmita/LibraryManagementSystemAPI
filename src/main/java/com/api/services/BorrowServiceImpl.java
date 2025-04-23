package com.api.services;

import com.api.entities.Book;
import com.api.entities.Borrow;
import com.api.entities.Patron;
import com.api.dao.BookRepository;
import com.api.dao.BorrowRepository;
import com.api.dao.PatronRepository;
import com.api.dto.BorrowedBookDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;
   
    @Override
    public String borrowBook(int pid, int bookId) {
        Patron patron = patronRepository.findById(pid).orElseThrow(() -> new EntityNotFoundException("Patron not exist"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not exist"));
        if (book.getAvailable().equalsIgnoreCase("false")) {
            throw new IllegalStateException("Book is not available to borrow");
        }

        Borrow borrow = new Borrow(book, patron);
        borrowRepository.save(borrow);

        book.setQuantity(book.getQuantity() - 1);
//        if (book.getQuantity() <= 0) {
//            book.setAvailable("false"); 
//        }
        bookRepository.save(book);

        return "Book borrowed successfully by Patron ID: " + pid;
    }

//	public String returnBook(int pid, int bookId) {
//		Patron patron=patronRepository.findById(pid).orElseThrow(()-> new EntityNotFoundException("patron not exist"));
//		Book book=bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("book not exist"));
//		if (book.getAvailable().equalsIgnoreCase("true")) {
//	        throw new IllegalStateException("Book is already available. No need to return.");
//		}
//		Borrow borrow=borrowRepository.findByBookAndPatronAndReturnDateIsNull(book,patron);
//		if(borrow==null) {
//			throw new IllegalStateException("this book was not borrowed by this patron");
//		}
//		borrow.setReturnDate(LocalDate.now());
//	    borrowRepository.save(borrow);
//
//	    book.setQuantity(book.getQuantity() + 1);
//	    bookRepository.save(book);
//
//	    return "Book returned successfully by Patron ID " + pid;
//
//	}
    public String returnBook(int pid, int bookId) {
        Patron patron = patronRepository.findById(pid).orElseThrow(() -> new EntityNotFoundException("Patron not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        List<Borrow> borrows = borrowRepository.findByBookAndPatron(book, patron);
        if (borrows.isEmpty()) {
            throw new IllegalStateException("This book was not borrowed by this patron with ID: " + pid);
        }
        Borrow borrow=borrows.get(0);
        borrowRepository.delete(borrow);
 
        book.setQuantity(book.getQuantity() + 1);
//        if (book.getQuantity() > 0) {
//            book.setAvailable("true");
//        }
        bookRepository.save(book); 

        return "Book returned successfully by Patron ID " + patron.getPid()+" name "+patron.getName();
    }

    public List<BorrowedBookDTO> getBooksBorrowedByPatron(int pid) {
        // Get all borrow entries for this patron
        List<Borrow> borrows = borrowRepository.findByPatronPid(pid);

        // Create a map to track books and how many times each was borrowed
        Map<Integer, BorrowedBookDTO> bookMap = new HashMap<>();

        for (Borrow borrow : borrows) {
            Book book = borrow.getBook();
            int bookId = book.getBookId();

            // If the book already exists in the map, increase quantity
            if (bookMap.containsKey(bookId)) {
                BorrowedBookDTO dto = bookMap.get(bookId);
                dto.setQuantity(dto.getQuantity() + 1);
            } else {
                // If not, add it with quantity = 1
                bookMap.put(bookId, new BorrowedBookDTO(
                    bookId,
                    book.getTitle(),
                    book.getAuthor(),
                    1
                ));
            }
        }
        return new ArrayList<>(bookMap.values());
    }


}