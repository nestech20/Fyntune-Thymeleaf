package com.example.demo.controller;

import com.example.demo.entity.Issue;
import com.example.demo.enums.IssueStatus;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private UserService userService;

    // List all issues (optional filter by status)
    @GetMapping
    public String listIssues(@RequestParam(required = false) IssueStatus status, Model model) {
        if (status != null) {
            model.addAttribute("issues", userService.getIssuesByStatus(status));
        } else {
            model.addAttribute("issues", userService.getAllIssues());
        }
        model.addAttribute("selectedStatus", status);
        model.addAttribute("statuses", IssueStatus.values());
        return "issues/list";
    }

    // Show form to create a new issue
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("issue", new Issue());
        return "issues/create";
    }

    // Handle new issue submission
    @PostMapping
    public String createIssue(@ModelAttribute Issue issue) {
        userService.createIssue(issue);
        return "redirect:/issues";
    }

    // Show form to edit issue status
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Issue issue = userService.getIssueById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue Id: " + id));
        model.addAttribute("issue", issue);
        model.addAttribute("statuses", IssueStatus.values());
        return "issues/edit";
    }

    // Handle status update
    @PostMapping("/update/{id}")
    public String updateIssue(@PathVariable Long id, @RequestParam IssueStatus status) {
        userService.updateIssueStatus(id, status);
        return "redirect:/issues";
    }

    // Delete an issue
    @PostMapping("/delete/{id}")
    public String deleteIssue(@PathVariable Long id) {
        userService.deleteIssue(id);
        return "redirect:/issues";
    }
}
