package com.example.devProject.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.devProject.entities.Education;

public interface EducationRepo{

	int gerMaxEducation();

	int getIdByQualification(String qualification);

	List<String> getEducation();

	void save(Education education);
	
}
