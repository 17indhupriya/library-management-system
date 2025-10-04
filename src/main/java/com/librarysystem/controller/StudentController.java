package com.librarysystem.controller;

import com.librarysystem.model.Book;
import com.librarysystem.model.User;
import com.librarysystem.service.BookService;
import com.librarysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/student/borrowed")
    public String viewBorrowedBooks(Model model) {
        // Get current logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userService.findByUsername(username);

        // Get books borrowed by this user
        List<Book> borrowedBooks = bookService.findBooksBorrowedByUser(currentUser);
        
        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("student", currentUser);
        return "student/my-borrowed-books";
    }
}