package com.example.devProject.controller;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.devProject.entities.RegistrationFile;
import com.example.devProject.service.RegistrationFileService;
@RestController
@CrossOrigin
public class RegistrationFileController {
	@Autowired
	private RegistrationFileService registrationFileService;
	@Value("${file.upload-dir}")
    private String uploadDir;
	@GetMapping("/{userId}/files")
    public ResponseEntity<List<RegistrationFile>> getAdditionalFiles(@PathVariable Integer userId) {
        List<RegistrationFile> files = registrationFileService.getFilesByUserId(userId);
        if (files.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(files);
    }
	@DeleteMapping("/{id}")
	public void Delete(@PathVariable Integer id,@RequestParam String filePath) {
	    registrationFileService.deleteFileByID(id, filePath);
	}
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws MalformedURLException, FileNotFoundException{
		Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();; 
		Resource resource =new UrlResource(filePath.toUri());
		  if (!resource.exists() || !resource.isReadable()) {
              throw new FileNotFoundException("File not found or not readable: " + fileName);
          }
          return ResponseEntity.ok()
                  .contentType(MediaType.APPLICATION_OCTET_STREAM)
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                  .body(resource);
	}
}
