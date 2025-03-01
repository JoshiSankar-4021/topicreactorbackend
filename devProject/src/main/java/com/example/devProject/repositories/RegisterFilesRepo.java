package com.example.devProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.devProject.entities.Registration;
import com.example.devProject.entities.RegistrationFile;

@Repository
public interface RegisterFilesRepo extends JpaRepository<RegistrationFile, Integer> {
	@Query("SELECT rf FROM RegistrationFile rf WHERE rf.registration.id = :registrationId")
	List<RegistrationFile> findRegistrationFilesByRegistrationId(@Param("registrationId") Integer registrationId);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO registration_file (file_name, original_name, file_path, registration_id) " +
	               "VALUES (:fileName, :originalName, :filePath, :registrationId)", nativeQuery = true)
	void saveFiles(@Param("fileName") String fileName,
	               @Param("originalName") String originalName,
	               @Param("filePath") String filePath,
	               @Param("registrationId") Integer userId);
	@Modifying
	@Transactional
	@Query("Delete from  RegistrationFile rf where rf.id=:id")
	void deleteFileById(@Param("id")Integer id);
	
	@Query("select count(rf) from RegistrationFile rf where rf.registration.id = :registrationId")
	int getCountFiles(@Param("registrationId") Integer id);

	@Modifying
	@Transactional
	@Query("Delete from RegistrationFile rf where rf.registration.id = :registrationId")
	void deleteFileByRegistrationId(@Param("registrationId") Integer id);

	@Modifying
	@Transactional
	@Query("Select rf.filePath from RegistrationFile rf where rf.registration.id = :registrationId")
	List<String> getPathOfFilesByRegistrationId(@Param("registrationId")Integer id);
}
	