package com.example.devProject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devProject.entities.Topic;
import com.example.devProject.service.TopicService;

@RestController
@RequestMapping("topic")
@CrossOrigin
public class TopicController {
	@Autowired
	private TopicService topicservice;
	@PostMapping
	public Object save(@RequestBody Topic topic ) {
		topicservice.createTopic(topic);
		return topic;
	}
	@GetMapping
	public List<Topic> getAllTopic(){
		ArrayList<Topic> list = new ArrayList<Topic>();
		list=topicservice.getAllTopics();
		return list;
	}
}
