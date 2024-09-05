package network.webtech.labspringmvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class taskCreateDTO {

    @NotBlank(message = "O título não pode estar em branco.")
    @Size(min = 5, max = 30, message = "O título deve ter entre 5 e 30 caracteres.")
    private String title;

    @NotBlank(message = "A descrição não pode estar em branco.")
    @Size(min = 5, max = 80, message = "A descrição deve ter entre 5 e 80 caracteres.")
    private String description;
}
