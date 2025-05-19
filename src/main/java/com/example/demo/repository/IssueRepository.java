package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Issue;
import com.example.demo.enums.IssueStatus;

public interface IssueRepository extends JpaRepository<Issue, Long>{

    List<Issue> findByStatus(IssueStatus status);
}
