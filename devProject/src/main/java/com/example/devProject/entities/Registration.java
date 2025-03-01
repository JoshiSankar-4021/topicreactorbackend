package com.example.devProject.entities;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Registration {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private String firstName;
private String lastName;
private String phnum;
private String address;
private String gender;
private String password;
@JsonProperty("physicallyHandicaped")
private boolean physicallyHandicaped;
@JsonProperty("legalIssues")
private boolean legalIssues;
private LocalDate dob;
private Integer age;
private Integer education;
@Column(unique = true)
private String email;
private String profileImagePath; 
@Transient
private byte[] profileImage;
@JsonManagedReference
@OneToMany(mappedBy = "registration", cascade = CascadeType.ALL,orphanRemoval = true)
private List<RegistrationFile> additionalFiles = new ArrayList<>();

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getPhnum() {
	return phnum;
}
public void setPhnum(String phnum) {
	this.phnum = phnum;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public boolean getphysicallyHandicaped() {
	return physicallyHandicaped;
}
public void setPhysicallyHandicaped(boolean physicallyHandicaped) {
	this.physicallyHandicaped = physicallyHandicaped;
}
public boolean getlegalIssues() {
	return legalIssues;
}
public void setLegalissues(boolean legalIssues) {
	this.legalIssues = legalIssues;
}
public LocalDate getDob() {
	return dob;
}
public void setDob(LocalDate dob) {
	this.dob = dob;
}
public Integer getAge() {
	return age;
}
public void setAge(Integer age) {
	this.age = age;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Integer getEducation() {
	return education;
}
public void setEducation(Integer education) {
	this.education = education;
}
public String getProfileImagePath() {
	return profileImagePath;
}
public void setProfileImagePath(String profileImagePath) {
	this.profileImagePath = profileImagePath;
}
public byte[] getProfileImage() {
	return profileImage;
}
public void setProfileImage(byte[] profileImage) {
	this.profileImage = profileImage;
}
public List<RegistrationFile> getAdditionalFiles() {
	return additionalFiles;
}
public void setAdditionalFiles(List<RegistrationFile> additionalFiles) {
	this.additionalFiles = additionalFiles;
}
}
