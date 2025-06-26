package com.epita.library.controller;

import com.epita.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final LibraryService service;

    @PostMapping("/{bookId}")
    public String borrowBook(@PathVariable Long bookId, @RequestParam String user) {
        return service.borrowBook(bookId, user);
    }

    @PostMapping("/return/{recordId}")
    public String returnBook(@PathVariable Long recordId) {
        return service.returnBook(recordId);
    }
}