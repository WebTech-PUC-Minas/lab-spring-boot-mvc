package network.webtech.labspringmvc.Repositories;

import network.webtech.labspringmvc.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCompletedIsTrueOrderByCreatedAtDesc();
    
    List<Task> findAllByCompletedIsFalseOrderByCreatedAtDesc();
}
