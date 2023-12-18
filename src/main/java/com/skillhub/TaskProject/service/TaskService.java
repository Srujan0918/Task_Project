package com.skillhub.TaskProject.service;

import com.skillhub.TaskProject.payload.TaskDto;

import java.util.List;

public interface TaskService {
    public TaskDto saveTask(long userid,TaskDto taskDto);

    public List<TaskDto> getAllTasks(long userid);
    public TaskDto getTask(long userid,long taskid);
    public void DeleteTask(long userid,long taskid);
}
