package fr.gdc.imaker.gateway.Service;
import fr.gdc.imaker.gateway.Model.TokenVerification;
import fr.gdc.imaker.gateway.Model.Users;
import fr.gdc.imaker.gateway.Repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TokenServiceImpl {

    private TokenRepo tokenRepository;
    private UserService userService;

    @Autowired
    public TokenServiceImpl(TokenRepo tokenRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    public TokenVerification createToken(Users user) {
        TokenVerification confirmationToken = new TokenVerification();
        confirmationToken.setUser(user);
        confirmationToken.setCreatedDate(LocalDate.now());
        confirmationToken.setTokenValue(UUID.randomUUID().toString());
        confirmationToken = tokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    public void findTokenByValue(String tokenValue) {
        TokenVerification tokenVerification = tokenRepository.findByTokenValue(tokenValue);
        if (tokenVerification != null) {
            LocalDate currentDate = LocalDate.now();
            Period lengthOfDays = Period.between(tokenVerification.getCreatedDate(), currentDate);
            if (lengthOfDays.getDays() <= 1) {
                Users user = userService.findUserByEmail(tokenVerification.getUser().getEmail());
                user.setEnabled(true);
                userService.saveUser(user);
            } else throw new NoSuchElementException("Token expired !!");
        } else throw new NoSuchElementException("Token not found !!");
    }
}
