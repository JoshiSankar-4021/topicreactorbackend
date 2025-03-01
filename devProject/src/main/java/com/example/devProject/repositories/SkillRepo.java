package com.example.devProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.devProject.entities.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Integer> {
	@Query(value="Insert into registration_skill (registration_id,registration_id) values(:id,:skillId)",nativeQuery = true)
	ResponseEntity<Skill> addUserSkills(Integer id, Integer skillId);

}
