package fr.gdc.imaker.gateway.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Model to create a new user")
public class UserDto {
    @NotBlank(message = " FullName must be Set !!")
    @ApiModelProperty(notes = " User full name  " , example = "My full name ",required = true)
    private String fullName;
    @NotBlank(message = " Email should be not empty")
    @Email(message = "Email should be valid !!")
    @ApiModelProperty(notes = " Valid email for the user " , example = "MyEmail@gmail.com",required = true)
    private String email;
    @NotNull(message = "password is mandatory")
    @Size(min = 8 ,message = " Password must contains at least 8 characters !! ")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(notes = "  Password of the user must have at least 8 characters " , required = true)
    private String password;
    @NotNull(message = "phone  must be set")
    @ApiModelProperty(notes = " User phone number " ,example = "12345678", required = true)
    private long phoneNumber;
    @ApiModelProperty(notes = " Company number of employees " ,example = "200")
    private int companyEmployeesNumber;

}
