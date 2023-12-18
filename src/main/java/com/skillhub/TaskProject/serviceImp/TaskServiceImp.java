package com.skillhub.TaskProject.serviceImp;

import com.skillhub.TaskProject.entity.Task;
import com.skillhub.TaskProject.entity.Users;
import com.skillhub.TaskProject.exception.APIException;
import com.skillhub.TaskProject.exception.TaskNotFound;
import com.skillhub.TaskProject.exception.UserNotFound;
import com.skillhub.TaskProject.payload.TaskDto;
import com.skillhub.TaskProject.repository.TaskRepository;
import com.skillhub.TaskProject.repository.UserRepository;
import com.skillhub.TaskProject.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
     @Override
     public TaskDto saveTask(long userid, TaskDto taskDto) {
         Users user = userRepository.findById(userid).orElseThrow(
                 () -> new UserNotFound(String.format("User ID %d not found",userid))
         );
         Task task = modelMapper.map(taskDto,Task.class);
         task.setUsers(user);
         Task savedTask = taskRepository.save(task);
         return modelMapper.map(savedTask,TaskDto.class);
     }

    @Override
    public List<TaskDto> getAllTasks(long userid) {
         userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User ID %d not found",userid))
        );
         List<Task> tasks = taskRepository.findAllByUsersId(userid);
         return tasks.stream().map(
                 task -> modelMapper.map(task,TaskDto.class)
         ).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTask(long userid, long taskid) {
        Users users = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User ID %d not found",userid))
        );
        Task task = taskRepository.findById(taskid).orElseThrow(
                ()-> new TaskNotFound(String.format("Task ID %d not found",taskid))
        );
        if(users.getId() != task.getUsers().getId()){
            throw new APIException(String.format("Task ID %d not belongs to User ID %d",taskid,userid));
        }
        return modelMapper.map(task,TaskDto.class);
    }

    @Override
    public void DeleteTask(long userid, long taskid) {
        Users users = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User ID %d not found",userid))
        );
        Task task = taskRepository.findById(taskid).orElseThrow(
                ()-> new TaskNotFound(String.format("Task ID %d not found",taskid))
        );
        if(users.getId() != task.getUsers().getId()){
            throw new APIException(String.format("Task ID %d not belongs to User ID %d",taskid,userid));
        }
        //delete task
        taskRepository.deleteById(taskid);
    }
}
