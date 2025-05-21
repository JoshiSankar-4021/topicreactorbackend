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

import com.example.devProject.entities.Education;
import com.example.devProject.service.EducationService;
@RestController
@RequestMapping("education")
@CrossOrigin
public class EducationController {
 @Autowired
 EducationService educationService;
 @PostMapping
 public String AddEducation(@RequestBody Education education) {
	 educationService.AddEducation(education);
	 return "Education addded";
 }
 
 @GetMapping
	public ResponseEntity<List<String>> list(){
		return ResponseEntity.ok(educationService.getAll());
	}
}
