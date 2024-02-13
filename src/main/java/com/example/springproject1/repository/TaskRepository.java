package com.example.springproject1.repository;

import com.example.springproject1.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task t where t.id = ?1")
    Task getById(Integer id);

}