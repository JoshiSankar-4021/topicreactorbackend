package com.example.devProject.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.devProject.entities.Education;
import com.example.devProject.repositories.EducationRepo;
@Service	
public class EducationService {

	@Autowired()
	EducationRepo educationRepo;
public String AddEducation(Education education) {
	try {
		educationRepo.save(education);
		return"Added Education";
	}catch(Exception ex) {
		return "Adding Failde";
	}
}
	public List<String> getAll(){
		return educationRepo.getEducation();
	}
}
