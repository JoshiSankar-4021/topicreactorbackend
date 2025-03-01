package com.example.devProject.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.devProject.entities.Registration;
import com.example.devProject.service.RegisrterationService;
@RestController
@RequestMapping("register")
@CrossOrigin
public class RegistrationController {
	@Autowired
	public RegisrterationService registrationService;
	
	@PostMapping
	public Object save(@RequestPart(value="registration") Registration registration,@RequestPart(value="profileImage",
	required = false)MultipartFile profileImage,
	@RequestPart(value = "additionalFiles", required = false) List<MultipartFile> additionalFiles) throws IOException {
		return registrationService.save(registration,profileImage,additionalFiles);
	}
	@GetMapping
	public List<Registration> getAll(){
		return registrationService.getAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Registration> read(@PathVariable Integer id){
		 Registration registration = registrationService.read(id);
		 if (registration.getProfileImagePath() != null) {
	            try {
	                byte[] imageData = Files.readAllBytes(Paths.get(registration.getProfileImagePath()));
	                registration.setProfileImage(imageData);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		    return ResponseEntity.ok(registration);	
		    }
	@GetMapping("/users")
    public Page<Registration> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false)String keyword) {
        
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return registrationService.getUsers(keyword, pageable);
    }
	@DeleteMapping("/{id}")
	public void deletById(@PathVariable Integer id){

	} 
	
	@GetMapping("/profile-image/{id}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Integer id) {
        Registration registration = registrationService.read(id);

        if (registration.getProfileImagePath() != null) {
            try {
                Path imagePath = Paths.get(registration.getProfileImagePath());
                byte[] imageBytes = Files.readAllBytes(imagePath);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> update(
        @PathVariable Integer id,
        @RequestPart("registration") Registration registration,
        @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
        @RequestPart(value="additionalFiles", required = false) List<MultipartFile> additionalFiles) throws IOException {
        registrationService.update(id, registration, profileImage,additionalFiles);
        return ResponseEntity.ok("Update successful");
    }
    
    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable Integer id) {
    	registrationService.deleteUserById(id);
    }
    
    @PostMapping("/excelFile")
    public ResponseEntity<?> SaveByExcelRecord(@RequestPart(value="excelFile") MultipartFile excelFile) throws IOException {
    	 String savingRecord = registrationService.SaveByExcelRecord(excelFile);
    	    Map<String, Object> response = new HashMap<>();
    	     if (savingRecord.trim().toLowerCase().contains("something is wrong")) {
                response.put("message", savingRecord);
                response.put("statusCode", 400);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                response.put("message", savingRecord);
                response.put("statusCode", 200);
                return ResponseEntity.ok(response);
            }
    } 
}
