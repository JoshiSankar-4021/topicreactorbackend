package com.example.devProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.devProject.entities.Topic;

public interface TopicRepo extends JpaRepository<Topic, Integer> {

}
