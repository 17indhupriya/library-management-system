package com.librarysystem.controller;

import com.librarysystem.model.Book;
import com.librarysystem.model.User;
import com.librarysystem.service.BookService;
import com.librarysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String listBooks(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(search));
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "books";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "book_form";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            bookService.createBook(book);
            redirectAttributes.addFlashAttribute("success", "Book added successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("book", bookService.getBookById(id).orElseThrow());
            return "book_form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Book not found");
            return "redirect:/books";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            book.setId(id);
            bookService.updateBook(book);
            redirectAttributes.addFlashAttribute("success", "Book updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("success", "Book deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/borrow/{id}")
    public String borrowBookPage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("book", bookService.getBookById(id).orElseThrow());
            return "borrow_book";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Book not found");
            return "redirect:/books";
        }
    }

    @PostMapping("/borrow/{id}")
    public String borrowBook(@PathVariable Long id, 
                           @RequestParam(required = false) String borrowDate,
                           @RequestParam(required = false) String returnDate,
                           @AuthenticationPrincipal UserDetails userDetails,
                           RedirectAttributes redirectAttributes) {
        try {
            User student = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
            
            LocalDateTime borrowDateTime = LocalDateTime.now();
            LocalDateTime returnDateTime = LocalDateTime.now().plusDays(14); // Default 2 weeks
            
            // Parse dates if provided
            if (borrowDate != null && !borrowDate.isEmpty()) {
                try {
                    borrowDateTime = LocalDateTime.parse(borrowDate + "T00:00:00");
                } catch (Exception e) {
                    // Use current date if parsing fails
                }
            }
            
            if (returnDate != null && !returnDate.isEmpty()) {
                try {
                    returnDateTime = LocalDateTime.parse(returnDate + "T23:59:59");
                } catch (Exception e) {
                    // Use default if parsing fails
                }
            }
            
            bookService.borrowBook(id, student, borrowDateTime, returnDateTime);
            redirectAttributes.addFlashAttribute("success", "Book borrowed successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }
    
    @GetMapping("/issue/{id}")
    public String issueBook(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails,
                          RedirectAttributes redirectAttributes) {
        try {
            User student = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
            bookService.borrowBook(id, student);
            redirectAttributes.addFlashAttribute("success", "Book borrowed successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.returnBook(id);
            double fine = book.calculateFine();
            if (fine > 0) {
                redirectAttributes.addFlashAttribute("warning", 
                    String.format("Book returned successfully. Fine amount: Rs. %.2f", fine));
            } else {
                redirectAttributes.addFlashAttribute("success", "Book returned successfully!");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }
}