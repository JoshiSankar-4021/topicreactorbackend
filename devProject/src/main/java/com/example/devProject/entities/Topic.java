package com.example.devProject.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Topic {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String topicName;
	Integer creatorId;	
	String reason;
	Date date;
}
