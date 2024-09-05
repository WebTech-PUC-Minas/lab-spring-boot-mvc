package network.webtech.labspringmvc.erros;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Tarefa com ID " + id + " não encontrada");
    }
}
