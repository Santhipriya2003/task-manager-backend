package com.example.taskmanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.ProjectRepository;
import com.example.taskmanager.repository.UserRepository;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin("*")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // ================= CREATE PROJECT =================

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }

    // ================= GET ALL PROJECTS =================

    @GetMapping
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    // ================= ADD MEMBER =================

    @PostMapping("/{projectId}/add-member/{userId}")
    public Project addMember(
            @PathVariable Long projectId,
            @PathVariable Long userId
    ) {

        Project project = projectRepository.findById(projectId).orElseThrow();

        User user = userRepository.findById(userId).orElseThrow();

        project.getMembers().add(user);

        return projectRepository.save(project);
    }

    // ================= REMOVE MEMBER =================

    @DeleteMapping("/{projectId}/remove-member/{userId}")
    public Project removeMember(
            @PathVariable Long projectId,
            @PathVariable Long userId
    ) {

        Project project = projectRepository.findById(projectId).orElseThrow();

        User user = userRepository.findById(userId).orElseThrow();

        project.getMembers().remove(user);

        return projectRepository.save(project);
    }

    // ================= GET PROJECT BY ID =================

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id);
    }
}