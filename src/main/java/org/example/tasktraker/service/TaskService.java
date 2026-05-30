package org.example.tasktraker.service;

import org.example.tasktraker.data.TaskDao;
import org.example.tasktraker.data.ProjectUserDao;
import org.example.tasktraker.entity.Task;
import org.example.tasktraker.exception.TrackerException;
import org.example.tasktraker.exception.ValidationException;

import java.util.List;

public class TaskService {
    private final TaskDao taskDao = new TaskDao();
    private final ProjectUserDao projectUserDao = new ProjectUserDao();

    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public boolean createBug(String title, String description, int projectId, int authorId) {
        return createBug(title, description, projectId, authorId, 0);
    }

    public boolean createBug(String title, String description, int projectId, int authorId, int assigneeId) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Bug title is empty");
        }
        if (projectId <= 0) {
            throw new ValidationException("Project is not selected");
        }

        if (assigneeId > 0) {
            projectUserDao.addUserToProject(assigneeId, projectId);
        }

        boolean created = taskDao.createBug(title.trim(), description != null ? description.trim() : "", projectId, authorId, assigneeId);
        if (!created) {
            throw new TrackerException("Failed to save bug to database");
        }
        return true;
    }

    public void createTask(String title, String description, int projectId, int assigneeId, int authorId, int priorityId) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Task title is empty");
        }
        if (projectId <= 0) {
            throw new ValidationException("Project is not selected");
        }
        if (assigneeId <= 0) {
            throw new ValidationException("Assignee is not selected");
        }
        if (priorityId <= 0) {
            throw new ValidationException("Priority is not selected");
        }

        projectUserDao.addUserToProject(assigneeId, projectId);

        boolean created = taskDao.createTask(title.trim(), description != null ? description.trim() : "", projectId, assigneeId, authorId, priorityId);

        if (!created) {
            throw new TrackerException("Task creation failed");
        }
    }

    public boolean updateTaskStatus(int taskId, int statusId) {
        return taskDao.updateTaskStatus(taskId, statusId);
    }

    public void deleteTask(int taskId) {
        if (taskId <= 0) {
            throw new ValidationException("Task is not selected");
        }
        boolean deleted = taskDao.deleteTask(taskId);
        if (!deleted) {
            throw new TrackerException("Task deletion failed");
        }
    }

    public List<Task> getTasksByAssignee(int assigneeId) {
        return taskDao.getTasksByAssignee(assigneeId);
    }

    public List<Task> getTasksByTesterProjects(int testerId) {
        return taskDao.getTasksByTesterProjects(testerId);
    }

    public String generateProjectReport(int projectId) {
        if (projectId <= 0) {
            throw new ValidationException("Project is not selected");
        }

        List<Task> projectTasks = taskDao.getAllTasks().stream()
                .filter(t -> t.getProjectId() == projectId)
                .toList();

        if (projectTasks.isEmpty()) {
            return "Нет задач в данном проекте.";
        }

        long total = projectTasks.size();
        long open = projectTasks.stream().filter(t -> "Open".equals(t.getStatus())).count();
        long inProgress = projectTasks.stream().filter(t -> "In Progress".equals(t.getStatus())).count();
        long accepted = projectTasks.stream().filter(t -> "Accepted".equals(t.getStatus())).count();


        double completionRate = (double) accepted / total * 100.0;

        return String.format("ОТЧЕТ ПО ПРОЕКТУ ID %d\n" +
                        "------------------------\n" +
                        "Всего задач: %d\n" +
                        "Открыто: %d\n" +
                        "В процессе: %d\n" +
                        "Завершено: %d\n" +
                        "Прогресс выполнения: %.2f%%\n" +
                        "------------------------",
                projectId, total, open, inProgress, accepted, completionRate);
    }

    public void autoAssignTask(int taskId, int projectId) {
        if (taskId <= 0 || projectId <= 0) {
            throw new ValidationException("Invalid task or project ID");
        }

 
        List<org.example.tasktraker.entity.User> developers = projectUserDao.getUsersByProject(projectId).stream()
                .filter(u -> "DEVELOPER".equals(u.getRole()))
                .toList();

        if (developers.isEmpty()) {
            throw new TrackerException("Cannot auto-assign: No developers in this project.");
        }


        List<Task> allTasks = taskDao.getAllTasks();
        org.example.tasktraker.entity.User leastLoadedDev = null;
        long minTasks = Long.MAX_VALUE;

        for (org.example.tasktraker.entity.User dev : developers) {
            long activeTasks = allTasks.stream()
                    .filter(t -> t.getAssigneeId() == dev.getId() &&
                            ("Open".equals(t.getStatus()) || "In Progress".equals(t.getStatus())))
                    .count();

            if (activeTasks < minTasks) {
                minTasks = activeTasks;
                leastLoadedDev = dev;
            }
        }

        if (leastLoadedDev == null) {
            throw new TrackerException("Could not find a developer to assign.");
        }

        boolean updated = taskDao.updateTaskAssignee(taskId, leastLoadedDev.getId());
        if (!updated) {
            throw new TrackerException("Database error during auto-assignment");
        }
    }
}
