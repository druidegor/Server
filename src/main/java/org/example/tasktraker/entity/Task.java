package org.example.tasktraker.entity;

public class Task extends BaseEntity {
    private String title;
    private String status;
    private String priority;
    private String project;
    private String description;
    private int projectId;
    private int assigneeId;
    private String assigneeName;

    public Task(int id, String title, String status, String priority, String project, String description) {
        this(id, title, status, priority, project, description, 0, 0, "");
    }

    public Task(int id, String title, String status, String priority, String project, String description, int projectId) {
        this(id, title, status, priority, project, description, projectId, 0, "");
    }

    public Task(int id, String title, String status, String priority, String project, String description, int projectId, int assigneeId, String assigneeName) {
        super(id);
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.project = project;
        this.description = description;
        this.projectId = projectId;
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
    }

    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public String getProject() { return project; }
    public String getDescription() { return description; }
    public int getProjectId() { return projectId; }
    public int getAssigneeId() { return assigneeId; }
    public String getAssigneeName() { return assigneeName; }

    @Override
    public String getEntityDescription() {
        return "Task: " + title + " [" + status + "] in project: " + project;
    }
}