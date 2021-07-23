package fr.gdc.imaker.gateway.Repository;


import fr.gdc.imaker.gateway.Model.TokenVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<TokenVerification,Long> {
    TokenVerification findByTokenValue(String tokenValue);
}
