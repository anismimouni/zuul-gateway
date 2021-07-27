package fr.gdc.imaker.gateway.Service;

import fr.gdc.imaker.gateway.Model.TokenResetPassword;
import fr.gdc.imaker.gateway.Model.TokenVerification;
import fr.gdc.imaker.gateway.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MailingService {
    private JavaMailSender javaMailSender;
    private ServerProperties serverProperties;

    @Autowired
    public MailingService(JavaMailSender javaMailSender, ServerProperties serverProperties) {
        this.javaMailSender = javaMailSender;
        this.serverProperties=serverProperties;
    }

    public void sendEmail(Users user , TokenVerification tokenVerification) {
        javaMailSender.send(this.verificationMail(user,tokenVerification));
    }

    public SimpleMailMessage verificationMail(Users user, TokenVerification tokenVerification) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Account Verification");
        mailMessage.setFrom("urpharma.store@gmail.Com");
        mailMessage.setText(" Greetings "+user.getFullName().toUpperCase(Locale.ROOT) + ",\r \n" +
                " We appreciate you  choosing our MarketPlace .\r\n" +
                " Please Confirm your account by clicking the following link: \r\n " + "http://localhost:" + serverProperties.getPort() +
                "/api/home/confirmAccount?token=" + tokenVerification.getTokenValue());
        return mailMessage;
    }

    public void sendResetPasswordEmail(Users user , TokenResetPassword tokenResetPassword) {
        javaMailSender.send(this.ResetPasswordMail(user,tokenResetPassword));
    }


    public SimpleMailMessage ResetPasswordMail (Users user, TokenResetPassword tokenResetPassword) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset Your Password ");
        mailMessage.setFrom("urpharma.store@gmail.Com");
        mailMessage.setText(" Hello  "+user.getFullName().toUpperCase(Locale.ROOT) + ",\r \n" +
                " We appreciate you  choosing our MarketPlace .\r\n" +
                " You have requested to reset your password : \r\n " + "http://localhost:" + serverProperties.getPort() +
                "/api/home/ResetPassword?token=" + tokenResetPassword.getTokenValue() +
                " \r \n" +
                "Ignore this email if you do remember your password, or you have not made the request ") ;
        return mailMessage;
    }
}
