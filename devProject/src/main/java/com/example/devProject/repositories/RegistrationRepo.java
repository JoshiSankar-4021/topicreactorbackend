package com.example.devProject.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.devProject.entities.Registration;


public interface RegistrationRepo{
	 @Query("select r.id from Registration r where r.email = :email and r.password = :password")
	    Integer isExist(@Param("email") String email, @Param("password") String password);
	
	 @Query("SELECT r FROM Registration r WHERE r.firstName LIKE %:keyword%")
	 	Page<Registration> findByKeyword(@Param("keyword")String keyword, Pageable pageable);
	
	 @Query("SELECT r FROM Registration r")
	 	Page<Registration> finduserwithoutkeyword(Pageable pageable);
	 
//	 @Modifying
//	 @Transactional
//	 @Query(value = "INSERT INTO registration (first_name, last_name, phnum, address, gender,password,physically_handicaped,legal_issues,dob,age,education,email,	profile_image_path) " +
//	  "VALUES (:firstName, :lastName, :phnum, :address, :gender, :password, :physicallyHandicaped, :legalIssues, :dob, :age, :education, :email, :profileImagePath)",nativeQuery = true)
//     void saveuser(@Param("firstName")String firstName,@Param("lastName") String lastName, @Param("phnum")String phnum,@Param("address") String address,@Param("gender") String gender, 
//	 @Param("password")String password,@Param("physicallyHandicaped") boolean physicallyHandicaped,@Param("legalIssues") boolean legalIssues,@Param("dob") LocalDate dob,@Param("age") Integer age, 
//	 @Param("education")Integer education,@Param("email") String email, @Param("profileImagePath")String profileImagePath);
	
	 void saveuser(Registration registration);
	 
	 @Query("select r.id from Registration r where r.email=:email")
	 Integer getIdByEmail(@Param("email")String email);
	
//	 @Modifying
//	 @Transactional
//	 @Query("UPDATE Registration r SET r.firstName=:firstName,r.lastName=:lastName,r.phnum=:phnum,r.address=:address,r.gender=:gender,"
//	 		+ "r.password=:password,r.physicallyHandicaped=:physicallyHandicaped,r.legalIssues=:legalIssues,r.dob=:dob,r.age=:age,r.education=:education,r.email=:email,r.profileImagePath=:profileImagePath where r.id=:id")
//	 void updateUser(@Param("id")Integer id,@Param("firstName") String firstName,@Param("lastName") String lastName, @Param("phnum")String phnum,@Param("address")String address,@Param("gender") String gender,
//			@Param("password")String password,@Param("physicallyHandicaped") boolean physicallyHandicaped,@Param("legalIssues") boolean legalIssues,@Param("dob") LocalDate dob, @Param("age")Integer age,
//			@Param("education")Integer education,@Param("email") String email,@Param("profileImagePath")String profileImagePath);

	 @Transactional
	 void updateUser(Registration registration);
	 
	 @Query("select r.profileImagePath from Registration r where r.id=:id")
	 String getImagePathByUserId(@Param("id")Integer id);

	 @Query("select r from Registration r")
	 public List<Registration> getAllUsers();
	
	 @Modifying
	 @Transactional
	 @Query("Delete from Registration r where r.id=:id")
	 void deleteUserById(@Param("id")Integer id);
	
	 @Query("Select r.profileImagePath from Registration r where r.id=:id")
	 String findProfilePathById(@Param("id")Integer id);
	 
	 @Query("SELECT r FROM Registration r WHERE r.id = :id")
	  Registration findUserById(@Param("id") Integer id);
	 
	 @Query("Select count(r) from Registration r where r.email=:email")
	 int getCountEmail(@Param("email")String email);

	void saveAll(List<Registration> excelRecords);
 
}