package com.example.springproject1.controller;

import com.example.springproject1.domain.Status;
import com.example.springproject1.domain.Task;
import com.example.springproject1.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @GetMapping("/")
//    public String taskList(Model model) {
//        model.addAttribute("tasks", taskService.getAll());
//
//        return "tasks";
//    }

    @GetMapping("/")
    public String getAll(Model model,
                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        List<Task> tasks = taskService.getPage(page, limit);

        int pageCount = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        List<Integer> pages = new ArrayList<>();

        for (int i = 1; i <= pageCount; i++) {
            pages.add(i);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("pages", pages);
        return "tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Integer id) {
        taskService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/updateTask/{id}")
    public String update(@PathVariable(value = "id") Integer id, Model model) {
        Task task = taskService.getById(id);
        Status status = task.getStatus();

        model.addAttribute("task", task);
        model.addAttribute("status", status);

        return "update";
    }

    @PostMapping("/update")
    public String save(@ModelAttribute("task") Task task) {
        taskService.update(task);

        return "redirect:/";
    }

    @GetMapping("/createTask")
    public String createTask(Model model) {
        Task task = new Task();

        model.addAttribute("task", task);

        return "createTask";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("task") Task task) {
        taskService.create(task);

        return "redirect:/";
    }
}
