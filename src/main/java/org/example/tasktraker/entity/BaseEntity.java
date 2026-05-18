package org.example.tasktraker.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {
    private int id;

    public BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String getEntityDescription();
}