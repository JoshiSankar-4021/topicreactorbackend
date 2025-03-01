package com.example.devProject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devProject.entities.Skill;
import com.example.devProject.service.SkillService;

@RestController
@RequestMapping("skill")
@CrossOrigin
public class SkillController {
	@Autowired
	private SkillService skillService;
	@GetMapping
	public ArrayList<Skill> getAll(){
		ArrayList<Skill> list = new ArrayList<Skill>();
		list=skillService.getAll();
		return list;
	}
}
