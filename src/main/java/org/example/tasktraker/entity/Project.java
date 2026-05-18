package org.example.tasktraker.entity;

public class Project extends BaseEntity {
    private String name;
    private String description;

    public Project(int id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getEntityDescription() {
        return "Project: " + name;
    }
}