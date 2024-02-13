package com.example.springproject1.service;

import com.example.springproject1.domain.Task;
import com.example.springproject1.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EntityManager entityManager;

    public TaskService(TaskRepository taskRepository, EntityManager entityManager) {
        this.taskRepository = taskRepository;
        this.entityManager = entityManager;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public List<Task> getPage(int page, int limit) {
        Query query = entityManager.createQuery("FROM Task");
        query.setFirstResult((page - 1) * 10);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    public long getAllCount() {
        return taskRepository.count();
    }

    public Task getById(Integer id) {
        return taskRepository.getById(id);
    }

    public void create(Task task) {
        taskRepository.save(task);
    }

    public void update(Task task) {
        Task taskForCheck = taskRepository.getById(task.getId());

        if (isNull(taskForCheck)) {
            throw new RuntimeException();
        }

        taskRepository.save(task);
    }

    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }
}
