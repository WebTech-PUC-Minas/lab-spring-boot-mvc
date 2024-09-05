package network.webtech.labspringmvc.Services.impl;

import network.webtech.labspringmvc.erros.ResourceNotFoundException;
import network.webtech.labspringmvc.Repositories.TaskRepository;
import network.webtech.labspringmvc.Services.TaskService;
import network.webtech.labspringmvc.dto.taskCreateDTO;
import network.webtech.labspringmvc.dto.taskResponseDTO;
import network.webtech.labspringmvc.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public taskResponseDTO createTask(taskCreateDTO taskToCreate) {
        Task task = mapCreateTaskDTOToTask(taskToCreate);
        Task savedTask = taskRepository.save(task);
        return mapToTaskDto(savedTask);
    }

    @Override
    public List<taskResponseDTO> findAllTasksCompleted() {
        List<Task> tasks = taskRepository.findAllByCompletedIsTrueOrderByCreatedAtDesc();

        return tasks.stream()
                .map(this::mapToTaskDto)
                .toList();
    }

    @Override
    public List<taskResponseDTO> findAllTasksNotCompleted() {
        List<Task> tasks = taskRepository.findAllByCompletedIsFalseOrderByCreatedAtDesc();

        return tasks.stream()
                .map(this::mapToTaskDto)
                .toList();
    }

    @Override
    public taskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
//        Optional<Task> task = taskRepository.findById(id);
//        if(task.isEmpty()) {
//            throw new ResourceNotFoundException(id);
//        }
//        return mapToTaskDto(task.get());
        return mapToTaskDto(task);
    }


    @Override
    public taskResponseDTO updateTask(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow (
                        () -> new ResourceNotFoundException(id)
                );
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
        return mapToTaskDto(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(id));
        taskRepository.deleteById(id);
    }

    private taskResponseDTO mapToTaskDto(Task task) {
        return taskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .build();
    }

    private Task mapCreateTaskDTOToTask(taskCreateDTO taskDTO) {
        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .build();
    }
}
