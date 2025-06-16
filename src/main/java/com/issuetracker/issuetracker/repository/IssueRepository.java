package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findByReporter(User reporter);
    List<Issue> findByAssignee(User assignee);
}
