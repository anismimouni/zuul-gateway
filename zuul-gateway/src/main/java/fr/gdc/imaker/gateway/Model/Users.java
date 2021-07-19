package fr.gdc.imaker.gateway.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {

    private static final long serialVersionUID = -2800960695811489984L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private Roles userRole;
    private boolean isEnabled;
    private int companyEmployeesNumber ;
    private long  phoneNumber;
}
