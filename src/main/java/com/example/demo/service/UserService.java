package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Issue;
import com.example.demo.entity.User;
import com.example.demo.enums.IssueStatus;
import com.example.demo.repository.IssueRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IssueRepository issueRepository;

	
	 public void registerNewUser(RegisterRequest registerRequest) {
	        User user = new User(
	            registerRequest.getUsername(),
	            registerRequest.getPassword(), 
	            registerRequest.getFullName(),
	            registerRequest.getEmail()
	        );
	        userRepository.save(user);
	        
	    }
	 
	   public boolean authenticate(LoginRequest loginRequest) {
	        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
	        return userOpt.isPresent() && userOpt.get().getPassword().equals(loginRequest.getPassword());
	    }
	   
	  
	    public Issue createIssue(Issue issue) {
	        // default status if null
	        if (issue.getStatus() == null) {
	            issue.setStatus(IssueStatus.OPEN);
	        }
	        return issueRepository.save(issue);
	    }

	
	    public List<Issue> getAllIssues() {
	        return issueRepository.findAll();
	    }


	    public List<Issue> getIssuesByStatus(IssueStatus status) {
	        return issueRepository.findByStatus(status);
	    }

	   
	    public Optional<Issue> getIssueById(Long id) {
	        return issueRepository.findById(id);
	    }

	
	    public Issue updateIssueStatus(Long id, IssueStatus newStatus) {
	        Optional<Issue> opt = issueRepository.findById(id);
	        if (opt.isPresent()) {
	            Issue issue = opt.get();
	            issue.setStatus(newStatus);
	            return issueRepository.save(issue);
	        }
	        throw new IllegalArgumentException("Issue not found with id: " + id);
	    }

	 
	    public void deleteIssue(Long id) {
	        issueRepository.deleteById(id);
	    }
}
