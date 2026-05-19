package com.example.taskmanager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(String assignedTo);

    List<Task> findByStatus(String status);

    List<Task> findByDueDateBefore(LocalDate date);
}