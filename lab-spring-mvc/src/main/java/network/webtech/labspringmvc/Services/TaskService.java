package network.webtech.labspringmvc.Services;

import network.webtech.labspringmvc.dto.taskResponseDTO;
import network.webtech.labspringmvc.dto.taskCreateDTO;

import java.util.List;

public interface TaskService {
    taskResponseDTO createTask(taskCreateDTO task);

    List<taskResponseDTO> findAllTasksNotCompleted();
    List<taskResponseDTO> findAllTasksCompleted();
    taskResponseDTO findById(Long id);

    taskResponseDTO updateTask(long id);

    void deleteTask(Long id);
}
