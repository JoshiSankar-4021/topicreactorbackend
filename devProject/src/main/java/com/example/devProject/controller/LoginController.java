package com.example.devProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devProject.entities.Login;
import com.example.devProject.service.RegisrterationService;

@RestController
@RequestMapping("login")
@CrossOrigin
public class LoginController {
	@Autowired
	public RegisrterationService registrationService;
	@PostMapping
	public Integer isExist(@RequestBody Login login) {
		String email = login.getEmail();
		String password = login.getPassword();
		Integer id = registrationService.authenticate(email,password);
		if(id>0) {
			return id;
		}
		return 0;
	}
}
