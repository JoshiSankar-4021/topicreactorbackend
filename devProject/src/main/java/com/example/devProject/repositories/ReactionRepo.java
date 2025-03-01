package com.example.devProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.devProject.entities.Reaction;

public interface ReactionRepo extends JpaRepository<Reaction, Integer>{
	@Query(value="select * from reaction where user_id=:commenterid",nativeQuery = true)
	List<Reaction> findCommentById(@Param(value = "commenterid") Integer id);
}
