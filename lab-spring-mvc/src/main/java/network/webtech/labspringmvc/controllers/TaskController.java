package network.webtech.labspringmvc.controllers;

import jakarta.validation.Valid;
import network.webtech.labspringmvc.erros.ResourceNotFoundException;
import network.webtech.labspringmvc.Services.TaskService;
import network.webtech.labspringmvc.dto.taskCreateDTO;
import network.webtech.labspringmvc.dto.taskResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public String TaskHomeModal(Model model) {
        List<taskResponseDTO> tasks = taskService.findAllTasksNotCompleted();

        model.addAttribute("tasks", tasks);
        model.addAttribute("taskForm", new taskCreateDTO());

        return "manage-tasks";
    }

    @GetMapping("/completed")
    public String TaskCompleteModal(Model model) {
        List<taskResponseDTO> tasks = taskService.findAllTasksCompleted();

        model.addAttribute("tasks", tasks);

        return "completed-tasks";
    }

    @GetMapping("/taskId={id}")
    public String findTaskById(Model model, RedirectAttributes redirectAttributes,
                               @PathVariable("id") String taskId
    )
    {
        if (!taskId.matches("[0-9]+")) {
            redirectAttributes.addFlashAttribute("redirectMessage", "ID inválido.");
            return "redirect:/tasks";
        }

        try {
            var id = Long.parseLong(taskId);
            var task = taskService.findById(id);
            System.out.println(task);

            model.addAttribute("task", task);
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("redirectMessage", e.getMessage());
            return "redirect:/tasks";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("redirectMessage", "Error ao buscar tarefa");
            return "redirect:/tasks";
        }
        return "task-details";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") @Valid taskCreateDTO task, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            StringBuilder message = new StringBuilder("Por favor, corrija os seguintes erros:");

            result.getFieldErrors().forEach(error -> {
                String field = error.getField();
                String defaultMessage = error.getDefaultMessage();

                String friendlyField = getFriendlyFieldName(field);
                message.append("\n").append(friendlyField).append(": ").append(defaultMessage);
            });

            redirectAttributes.addFlashAttribute("redirectMessage", message.toString());

            return "redirect:/tasks";
        }

        try {
            taskService.createTask(task);
            redirectAttributes.addFlashAttribute("redirectMessage", "Tarefa criada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("redirectMessage", "Falha ao criar a tarefa.");
        }

        return "redirect:/tasks";
    }

    @GetMapping("/update/taskId={id}")
    public String updateTask(@PathVariable("id") String taskId, RedirectAttributes redirectAttributes) {
        if (!taskId.matches("[0-9]+")) {
            redirectAttributes.addFlashAttribute("redirectMessage", "ID inválido.");
            return "redirect:/tasks";
        }


        try {
            var id = Long.parseLong(taskId);
            var task = taskService.updateTask(id);

            redirectAttributes.addFlashAttribute("redirectMessage", "Tarefa atualizada com sucesso.");

            if(task.isCompleted()) {
                return "redirect:/tasks/completed";
            }
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("redirectMessage", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("redirectMessage", "Falha ao atualizar a tarefa.");
        }

        return "redirect:/tasks";
    }

    @GetMapping("/delete/taskId={id}")
    public String deleteTask(@PathVariable("id") String taskId, RedirectAttributes redirectAttributes) {
        if (!taskId.matches("[0-9]+")) {
            redirectAttributes.addFlashAttribute("redirectMessage", "ID inválido.");
            return "redirect:/tasks";
        }

        try {
            Long id = Long.parseLong(taskId);

            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("redirectMessage", "Tarefa deletada com sucesso.");

        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("redirectMessage", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("redirectMessage", "Falha ao deletar a tarefa.");
        }

        return "redirect:/tasks";
    }

    private String getFriendlyFieldName(String field) {
        return switch (field) {
            case "title" -> "Título";
            case "description" -> "Descrição";
            default -> field.substring(0, 1).toUpperCase() + field.substring(1);
        };
    }
}
