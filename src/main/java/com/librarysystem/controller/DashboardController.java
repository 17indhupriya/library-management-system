package com.librarysystem.controller;

import com.librarysystem.model.User;
import com.librarysystem.service.BookService;
import com.librarysystem.service.UserService;
import com.librarysystem.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ActivityService activityService;

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
        
        // Get recent activities for logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            try {
                User currentUser = userService.findByUsername(auth.getName());
                if (currentUser != null) {
                    var recentActivities = activityService.getRecentActivities(currentUser, 5);
                    model.addAttribute("recentActivities", recentActivities);
                    
                    // Check if librarian and also show all activities in system
                    if (currentUser.getRole().equals("ROLE_LIBRARIAN")) {
                        var allActivities = activityService.getAllActivities(5);
                        model.addAttribute("recentActivities", allActivities);
                    }
                }
            } catch (Exception e) {
                // User not found, continue without activities
            }
        }
        
        return "dashboard";
    }
}