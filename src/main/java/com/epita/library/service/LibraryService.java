package com.epita.library.service;

import com.epita.library.model.Book;
import com.epita.library.model.BorrowRecord;
import com.epita.library.repository.BookRepository;
import com.epita.library.repository.BorrowRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final BookRepository bookRepo;
    private final BorrowRecordRepository borrowRepo;

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    public String borrowBook(Long bookId, String borrower) {
        Book book = bookRepo.findById(bookId).orElseThrow();
        if (!book.isAvailable()) return "Book is already borrowed";

        book.setAvailable(false);
        bookRepo.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDate.now());
        borrowRepo.save(record);

        return "Book borrowed successfully";
    }

    public String returnBook(Long recordId) {
        BorrowRecord record = borrowRepo.findById(recordId).orElseThrow();
        record.setReturnDate(LocalDate.now());

        Book book = record.getBook();
        book.setAvailable(true);

        bookRepo.save(book);
        borrowRepo.save(record);

        return "Book returned successfully";
    }
}