package fr.gdc.imaker.gateway.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessDetails {
    private LocalDateTime timestamp;
    private String message;
    private Object object;
}
