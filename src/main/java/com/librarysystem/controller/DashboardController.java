package com.librarysystem.controller;

import com.librarysystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        var allBooks = bookService.getAllBooks();
        
        // Calculate statistics
        model.addAttribute("totalBooks", allBooks.size());
        model.addAttribute("availableBooks", allBooks.stream().filter(b -> b.isAvailable()).count());
        model.addAttribute("issuedBooks", allBooks.stream().filter(b -> !b.isAvailable()).count());
        
        return "dashboard";
    }
}