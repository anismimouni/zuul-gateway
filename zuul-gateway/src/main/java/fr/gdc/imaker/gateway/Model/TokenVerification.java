package fr.gdc.imaker.gateway.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class TokenVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String tokenValue;
    private LocalDate createdDate;
    @OneToOne(targetEntity = Users.class , fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(nullable=false,name="user_id")
    @JsonIgnore
    private Users user;
}
