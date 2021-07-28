package fr.gdc.imaker.gateway.Service;

import fr.gdc.imaker.gateway.Model.TokenResetPassword;
import fr.gdc.imaker.gateway.Model.TokenVerification;
import fr.gdc.imaker.gateway.Model.Users;
import fr.gdc.imaker.gateway.Repository.TokenResetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TokenResetPasswordService {

    @Autowired
    TokenResetRepo tokenResetRepo;

    @Autowired
    UserService userService;

    public TokenResetPassword createToken(Users user) {
        TokenResetPassword resetToken = new TokenResetPassword();
        resetToken.setUser(user);
        resetToken.setCreatedDate(LocalDate.now());
        resetToken.setTokenValue(UUID.randomUUID().toString());
        resetToken = tokenResetRepo.save(resetToken);
        return resetToken;
    }
    public  TokenResetPassword getToken(String token){
        return tokenResetRepo.findByTokenValue(token);
    }
    public void findTokenByValue(String tokenValue) {
        TokenResetPassword tokenResetPassword = tokenResetRepo.findByTokenValue(tokenValue);
        if (tokenResetPassword != null) {
            LocalDate currentDate = LocalDate.now();
            Period lengthOfDays = Period.between(tokenResetPassword.getCreatedDate(), currentDate);
            if (lengthOfDays.getDays() <= 1) {
                Users user = userService.findUserByEmail(tokenResetPassword.getUser().getEmail());
                user.setEnabled(true);
                userService.saveUser(user);
            } else throw new NoSuchElementException("Token expired !!");
        } else throw new NoSuchElementException("Token not found !!");
    }
    public void updatePassword(String tokenValue, String newPassword ) {
        TokenResetPassword tokenResetPassword = tokenResetRepo.findByTokenValue(tokenValue);
        if (tokenResetPassword != null) {
            LocalDate currentDate = LocalDate.now();
            Period lengthOfDays = Period.between(tokenResetPassword.getCreatedDate(), currentDate);
            if (lengthOfDays.getDays() <= 1) {
                Users user = userService.findUserByEmail(tokenResetPassword.getUser().getEmail());
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodePassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodePassword);
                userService.saveUser(user);
            } else throw new NoSuchElementException("Token expired !!");
        } else throw new NoSuchElementException("Token not found !!");
    }
}
