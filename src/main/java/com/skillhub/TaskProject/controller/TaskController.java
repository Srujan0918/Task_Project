package com.skillhub.TaskProject.controller;

import com.skillhub.TaskProject.payload.TaskDto;
import com.skillhub.TaskProject.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //For Saving the Tasks to the database
    @PostMapping("/{userid}/tasks")
    public ResponseEntity<TaskDto> saveTask(
            @PathVariable(name = "userid") long userid,
            @RequestBody TaskDto taskDto
    ){
        return new ResponseEntity<>(taskService.saveTask(userid, taskDto), HttpStatus.CREATED);
    }

    //For fetching all the tasks from the database based on Userid

    @GetMapping("/{userid}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @PathVariable(name = "userid") long userid
    ){
        return new ResponseEntity<>(taskService.getAllTasks(userid),HttpStatus.OK);
    }

    //get individual task
    @GetMapping("/{userid}/tasks/{taskid}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable(name = "userid") long userid,
            @PathVariable(name = "taskid") long taskid
    ){
        return new ResponseEntity<>(taskService.getTask(userid,taskid),HttpStatus.OK);
    }

    @DeleteMapping("/{userid}/tasks/{taskid}")
    public ResponseEntity<String> deleteTask(
            @PathVariable(name = "userid") long userid,
            @PathVariable(name = "taskid") long taskid
    ){
        taskService.DeleteTask(userid,taskid);
        return new ResponseEntity<>("Task Deleted Successfully",HttpStatus.OK);
    }
}
