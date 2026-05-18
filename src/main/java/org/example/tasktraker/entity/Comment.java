package org.example.tasktraker.entity;

public class Comment extends BaseEntity {
    private int taskId;
    private String text;
    private String authorName;

    public Comment(int id, int taskId, String text, String authorName) {
        super(id);
        this.taskId = taskId;
        this.text = text;
        this.authorName = authorName;
    }

    public int getTaskId() { return taskId; }
    public String getText() { return text; }
    public String getAuthorName() { return authorName; }

    @Override
    public String toString() {
        return authorName + ": " + text;
    }

    @Override
    public String getEntityDescription() {
        return "Comment by " + authorName + " for Task ID: " + taskId;
    }
}