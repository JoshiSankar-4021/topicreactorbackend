package com.example.devProject.service;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.devProject.entities.RegistrationFile;
import com.example.devProject.repositories.RegisterFilesRepo;

@Service
public class RegistrationFileService {
	@Autowired
    private RegisterFilesRepo registerFilesRepo;

    public List<RegistrationFile> getFilesByUserId(Integer userId) {
        return registerFilesRepo.findRegistrationFilesByRegistrationId(userId);
    }
	@Transactional
    public void deleteFileByID(Integer id,String filePath) {
		String decodedPath = URLDecoder.decode(filePath, StandardCharsets.UTF_8);
		File file = new File(decodedPath);
    	if(filePath!=null) {
    		if(file.exists()) {
    			file.delete();
    		}
    	}
    	registerFilesRepo.deleteFileById(id);
    }
	public int getCountFiles(Integer id) {
		int count = registerFilesRepo.getCountFiles(id);
		if(count>0) {
			return count;
		}
		return 0;
	}
	@Transactional
	public void deleteFileByRegistrationId(Integer id) {
		List<String> list=registerFilesRepo.getPathOfFilesByRegistrationId(id);
		for(String item:list) {
			File file= new File(item);
			System.out.println(item);
			if(file.exists()) {
				if(file.delete()) {
					registerFilesRepo.deleteFileByRegistrationId(id);
				}
			}
		}
		
	}
	
	
}
