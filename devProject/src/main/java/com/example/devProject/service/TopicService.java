package com.example.devProject.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devProject.entities.Topic;
import com.example.devProject.repositories.TopicRepo;

@Service
public class TopicService {
	@Autowired
	private TopicRepo topicrepo;
	
	public Topic createTopic(Topic topic) {
		return topicrepo.save(topic);
	}

	public ArrayList<Topic> getAllTopics() {
		// TODO Auto-generated method stub
		return (ArrayList<Topic>) topicrepo.findAll();
	}
	
}
