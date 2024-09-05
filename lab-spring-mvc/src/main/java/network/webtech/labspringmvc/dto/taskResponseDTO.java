package network.webtech.labspringmvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class taskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}

