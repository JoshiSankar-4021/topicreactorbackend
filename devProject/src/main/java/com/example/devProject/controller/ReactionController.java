package com.example.devProject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devProject.entities.Reaction;
import com.example.devProject.service.ReactionService;

@RestController
@RequestMapping("reaction")
@CrossOrigin
public class ReactionController {
	@Autowired
	private ReactionService reactionservice;
	@PostMapping
	public Object save(@RequestBody Reaction reaction) {
		return reactionservice.save(reaction);	
	}
	@GetMapping
	public List<Reaction> getAll(){
		ArrayList<Reaction> list =new ArrayList<Reaction>();
		list = reactionservice.getAll();
		return list;		
	}
	@GetMapping("/{id}")
	public List<Reaction> getAllByUser(@PathVariable Integer id){
		ArrayList<Reaction> list =new ArrayList<Reaction>();
		list = reactionservice.getById(id);
		return list;
	}
	@DeleteMapping("/{id}")
	public void deletById(@PathVariable Integer id){
		reactionservice.delet(id);
	}
	@GetMapping("/data/{id}")
	public Object getByid(@PathVariable Integer id) {
		return reactionservice.getByOnlyId(id);
	}
	
}
