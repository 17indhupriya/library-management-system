package com.librarysystem.service;

import com.librarysystem.model.Activity;
import com.librarysystem.model.Book;
import com.librarysystem.model.User;
import com.librarysystem.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public void logActivity(User user, String activityType, String description, Book book) {
        Activity activity = new Activity(user, activityType, description, LocalDateTime.now(), book);
        activityRepository.save(activity);
    }

    public void logBorrowActivity(User user, Book book) {
        String description = user.getFullName() + " borrowed \"" + book.getTitle() + "\"";
        logActivity(user, "BORROWED", description, book);
    }

    public void logReturnActivity(User user, Book book) {
        String description = user.getFullName() + " returned \"" + book.getTitle() + "\"";
        logActivity(user, "RETURNED", description, book);
    }

    public List<Activity> getRecentActivities(User user, int limit) {
        List<Activity> activities = activityRepository.findByUserOrderByActivityDateDesc(user);
        return activities.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Activity> getAllActivities(int limit) {
        // Get all activities in the system, sorted by most recent first
        var allActivities = activityRepository.findAll();
        return allActivities.stream()
                .sorted((a, b) -> b.getActivityDate().compareTo(a.getActivityDate()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Activity> getAllUserActivities(User user) {
        return activityRepository.findByUserOrderByActivityDateDesc(user);
    }

    @Transactional
    public void deleteActivitiesForBook(Book book) {
        // This could be used for cleanup if needed
        // For now, we'll just leave the history intact
    }
}
