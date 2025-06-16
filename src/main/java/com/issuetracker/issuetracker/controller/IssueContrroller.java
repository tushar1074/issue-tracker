package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.service.IssueService;
import com.issuetracker.issuetracker.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/dashboard")
public class IssueContrroller {

    private final IssueService issueService;
    private final UserService userService;

    public IssueContrroller(IssueService issueService, UserService userService){
        this.issueService=issueService;
        this.userService=userService;
    }

    @GetMapping
    public String viewDashboard(Model model){
        model.addAttribute("issues", issueService.getAllIssues());
        return "dashboard";
    }

    @GetMapping("/new")
    public String createIssueForm(Model model) {
        model.addAttribute("issue", new Issue());
        model.addAttribute("users", userService.getAllUsers()); // 👈 fetch all users
        return "create-issue";
    }

    @PostMapping("/new")
    public String saveIssue(@ModelAttribute Issue issue,
                            @RequestParam("assigneeId") Long assigneeId,
                            @AuthenticationPrincipal UserDetails userDetails) {

        // Set reporter from logged-in user
        User reporter = userService.findByEmail(userDetails.getUsername());
        issue.setReporter(reporter);

        // Set assignee from selected ID
        User assignee = userService.findById(assigneeId);
        issue.setAssignee(assignee);

        issueService.createIssue(issue);
        return "redirect:/dashboard";
    }


    @GetMapping("/issue/{id}")
    public String viewIssue(@PathVariable Long id, Model model) {
        Issue issue = issueService.getIssueById(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

        String createdAtFormatted = issue.getCreatedAt() != null ? issue.getCreatedAt().format(formatter) : "N/A";
        String updatedAtFormatted = issue.getUpdatedAt() != null ? issue.getUpdatedAt().format(formatter) : "N/A";

        model.addAttribute("issue", issue);
        model.addAttribute("createdAtFormatted", createdAtFormatted);
        model.addAttribute("updatedAtFormatted", updatedAtFormatted);

        return "view-issue";
    }

}
