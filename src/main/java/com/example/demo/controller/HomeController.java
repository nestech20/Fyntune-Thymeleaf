package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Controller
public class HomeController {
	
	
	 @Autowired
	    private UserService userService;
	 
		@Autowired
		private UserRepository userRepository;
	 
	 
	    @GetMapping("/register")
	    public String showRegisterForm(Model model) {
	        model.addAttribute("registerRequest", new RegisterRequest());
	        return "register";
	    }

	    @PostMapping("/register")
	    public String handleRegister(@ModelAttribute RegisterRequest registerRequest) {
	        userService.registerNewUser(registerRequest);
	        return "redirect:/login";
	    }
	    

	    @GetMapping("/login")
	    public String showLoginForm(Model model) {
	        model.addAttribute("loginRequest", new LoginRequest());
	        return "login";
	    }

	   
	
	    @PostMapping("/login")
	    public String handleLogin(@ModelAttribute LoginRequest loginRequest, Model model) {
	        boolean isAuthenticated = userService.authenticate(loginRequest);

	        if (isAuthenticated) {
	            System.out.println("Login success for: " + loginRequest.getUsername());
	            return "redirect:/issues";
	        } else {
	            System.out.println("Login failed for: " + loginRequest.getUsername());
	            model.addAttribute("loginError", "Invalid username or password");
	            return "login";
	        }
	    }

 
	 @GetMapping("/")
	 public String home(Model model) {
        model.addAttribute("message", "Welcome to Thymeleaf in Spring Boot!");
        return "home";
    }

	
}
