package org.example.tasktraker.entity;

public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private String role;

    public User(int id, String name, String email, String role, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }

    @Override
    public String getEntityDescription() {
        return "User Entity: " + name + ", Role: " + role;
    }
}
