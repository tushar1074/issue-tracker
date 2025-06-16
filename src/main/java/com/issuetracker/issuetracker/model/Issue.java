    package com.issuetracker.issuetracker.model;

    import jakarta.persistence.*;

    import java.time.LocalDateTime;

    @Entity
    @Table(name = "issues")
    public class Issue {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        @Column(length = 1000)
        private String description;

        private String status;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        @ManyToOne
        @JoinColumn(name = "reporter_id")
        private User reporter;

        @ManyToOne
        @JoinColumn(name = "assignee_id")
        private User assignee;

        public Issue() {
        }

        public Issue(Long id, String title, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt, User reporter, User assignee) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.status = status;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.reporter = reporter;
            this.assignee = assignee;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }

        public User getReporter() {
            return reporter;
        }

        public void setReporter(User reporter) {
            this.reporter = reporter;
        }

        public User getAssignee() {
            return assignee;
        }

        public void setAssignee(User assignee) {
            this.assignee = assignee;
        }

        @Override
        public String toString() {
            return "Issue{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", status='" + status + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    '}';
        }

    }
