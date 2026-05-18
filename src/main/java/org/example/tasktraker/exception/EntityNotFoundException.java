package org.example.tasktraker.exception;

public class EntityNotFoundException extends TrackerException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}