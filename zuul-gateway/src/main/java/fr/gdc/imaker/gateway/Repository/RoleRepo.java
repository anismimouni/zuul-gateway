package fr.gdc.imaker.gateway.Repository;
import fr.gdc.imaker.gateway.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles,Long> {
    Roles findRolesByRoleName(String role);
}
