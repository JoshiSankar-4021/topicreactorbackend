package com.example.devProject.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devProject.entities.Registration;

@RestController
@RequestMapping("user")
public class UserController {

	@PostMapping("/userregistration")
	public String postUser(@RequestBody String name) {
		return "user registered";
	}
	
	@GetMapping("/userdetails/{name}/{lastName}")
	public String getUser(@PathVariable String name,@PathVariable String lastName) {
		return "get user details";
	}
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestBody String userName) {
		return "deleted user";
	}
	
}
