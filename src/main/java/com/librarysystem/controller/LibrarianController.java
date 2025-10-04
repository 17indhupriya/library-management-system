package com.librarysystem.controller;

import com.librarysystem.model.Book;
import com.librarysystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LibrarianController {

    @Autowired
    private BookService bookService;

    @GetMapping("/librarian/manage-books")
    public String manageBooks(Model model) {
        List<Book> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks);
        model.addAttribute("newBook", new Book());
        return "librarian/manage-books";
    }

    @PostMapping("/librarian/books/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            bookService.createBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding book: " + e.getMessage());
        }
        return "redirect:/librarian/manage-books";
    }

    @PostMapping("/librarian/books/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            book.setId(id);
            bookService.updateBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + e.getMessage());
        }
        return "redirect:/librarian/manage-books";
    }

    @PostMapping("/librarian/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/librarian/manage-books";
    }
}