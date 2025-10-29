package com.librarysystem.repository;

import com.librarysystem.model.Activity;
import com.librarysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUserOrderByActivityDateDesc(User user);
    
    @Query("SELECT a FROM Activity a WHERE a.user = :user ORDER BY a.activityDate DESC")
    List<Activity> findRecentActivities(@Param("user") User user);
}
