package com.example.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}