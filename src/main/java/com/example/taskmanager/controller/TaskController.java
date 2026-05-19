package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskRepository;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // ================= GET ALL TASKS =================

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ================= CREATE TASK =================

    @PostMapping
    public Task createTask(@RequestBody Task task) {

        if (task.getStatus() == null) {
            task.setStatus("TODO");
        }

        return taskRepository.save(task);
    }

    // ================= UPDATE TASK =================

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task updatedTask
    ) {

        Task task = taskRepository.findById(id).orElseThrow();

        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }

    // ================= DELETE TASK =================

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskRepository.deleteById(id);

        return "Task deleted successfully";
    }
}
