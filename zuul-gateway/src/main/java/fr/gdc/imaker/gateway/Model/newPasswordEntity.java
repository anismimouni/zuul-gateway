package fr.gdc.imaker.gateway.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class newPasswordEntity {
    private String newPassword ;
    private String repeatNewPassword ;
}
