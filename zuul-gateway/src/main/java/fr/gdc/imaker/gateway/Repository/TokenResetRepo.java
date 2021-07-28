package fr.gdc.imaker.gateway.Repository;

import fr.gdc.imaker.gateway.Model.TokenResetPassword;
import fr.gdc.imaker.gateway.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenResetRepo extends JpaRepository<TokenResetPassword,Long> {
    public TokenResetPassword findByTokenValue(String tokenValue);
    public TokenResetPassword findByUser(Users user);
}
