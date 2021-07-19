package fr.gdc.imaker.gateway.Repository;

import fr.gdc.imaker.gateway.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByEmail(String email);
}
