package com.librarysystem.service;

import com.librarysystem.model.Book;
import com.librarysystem.model.User;
import com.librarysystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new RuntimeException("Book with this ISBN already exists");
        }
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        if (!bookRepository.existsById(book.getId())) {
            throw new RuntimeException("Book not found");
        }
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String searchTerm) {
        return bookRepository.searchBooks(searchTerm);
    }

    public List<Book> findBooksBorrowedByUser(User student) {
        return bookRepository.findByBorrowedBy(student);
    }

    @Transactional
    public Book issueBook(Long bookId, User student) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already issued");
        }

        book.setAvailable(false);
        book.setBorrowedBy(student);
        book.setBorrowedDate(LocalDateTime.now());
        book.setDueDate(LocalDateTime.now().plusDays(14)); // 2 weeks borrowing period

        return bookRepository.save(book);
    }

    @Transactional
    public Book returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.isAvailable()) {
            throw new RuntimeException("Book is already returned");
        }

        double fine = book.calculateFine();
        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setBorrowedDate(null);
        book.setDueDate(null);

        Book returnedBook = bookRepository.save(book);
        
        if (fine > 0) {
            // You could add fine processing logic here
            // For now, we'll just return the book with the calculated fine
            System.out.println("Fine calculated: Rs. " + fine);
        }

        return returnedBook;
    }
}