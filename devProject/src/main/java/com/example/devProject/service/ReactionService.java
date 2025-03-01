package com.example.devProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.devProject.entities.Reaction;
import com.example.devProject.repositories.ReactionRepo;

@Service
public class ReactionService {
	@Autowired
	private ReactionRepo reactionrepo;
	
	public Object save(Reaction reaction) {
		
		return reactionrepo.save(reaction);
	}

	public ArrayList<Reaction> getAll() {
		List<Reaction> list = new ArrayList<Reaction>();
		list = reactionrepo.findAll();
		return (ArrayList<Reaction>) list;
	}
	
	public ArrayList<Reaction> getById(Integer id){
		List<Reaction> list = new ArrayList<Reaction>();
		list = reactionrepo.findCommentById(id);
		return (ArrayList<Reaction>) list;
	}
	public void delet(Integer id) {
		reactionrepo.deleteById(id);
	}
	public Object getByOnlyId(Integer id) {
		return reactionrepo.findById(id);
	}
} 
