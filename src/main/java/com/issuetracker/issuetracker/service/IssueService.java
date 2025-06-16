package com.issuetracker.issuetracker.service;

import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepo;

    public IssueService(IssueRepository issueRepo) {
        this.issueRepo = issueRepo;
    }

    public List<Issue> getAllIssues() {
        return issueRepo.findAll();
    }

    public void createIssue(Issue issue) {
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepo.save(issue);
    }

    public Issue getIssueById(Long id) {
        return issueRepo.findById(id).orElseThrow();
    }
}
