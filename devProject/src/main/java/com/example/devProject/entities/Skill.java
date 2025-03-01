package com.example.devProject.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Skill {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String skill;
	private String skillDesc;
}
