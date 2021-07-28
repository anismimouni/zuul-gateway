package fr.gdc.imaker.gateway.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String tokenValue ;
    private LocalDate createdDate;
    @OneToOne(targetEntity = Users.class , fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(nullable=false,name="user_id")
    @JsonIgnore
    private Users user;
}
