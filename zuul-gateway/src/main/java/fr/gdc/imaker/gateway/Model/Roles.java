package fr.gdc.imaker.gateway.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleName;

    @OneToMany(mappedBy = "userRole" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Users> roleOwner;


    public Roles(String roleName) {
        this.roleName = roleName.toUpperCase(Locale.ROOT);
    }
}