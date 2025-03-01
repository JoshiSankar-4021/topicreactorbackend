package com.example.devProject.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.devProject.entities.Skill;
import com.example.devProject.repositories.SkillRepo;

@Service
public class SkillService {
	@Autowired
	private SkillRepo skilRepo;
	
	public ArrayList<Skill> getAll() {
		ArrayList<Skill> list = new ArrayList<Skill>();
		list=(ArrayList<Skill>) skilRepo.findAll();
		return list;
	}
}
	
