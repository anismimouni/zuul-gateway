package fr.gdc.imaker.gateway.Endpoint;

import fr.gdc.imaker.gateway.Dto.UserDto;
import fr.gdc.imaker.gateway.Exceptions.SuccessDetails;
import fr.gdc.imaker.gateway.Model.*;
import fr.gdc.imaker.gateway.Service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private Jwt jwt;
    @Autowired
    @Qualifier("auth")
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private MailingService mailService;
    @Autowired
    private TokenResetPasswordService tokenResetPasswordService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @ApiOperation(value = "Register new user ", response = SuccessDetails.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " User Added successfully"),
            @ApiResponse(code = 400, message = " Input validation error ")
    }
    )
    @PostMapping("/signup")
    public ResponseEntity<Object> registration(@Valid @RequestBody UserDto userDto){
        Users userEntity=userService.registerUser(userDto);
        TokenVerification token=tokenService.createToken(userEntity);
        mailService.sendEmail(userEntity, token);
        SuccessDetails successMessage = new SuccessDetails(LocalDateTime.now(), "User Added Successfully , a confirmation " +
                "email was sent to :" + userEntity.getEmail(), userDto);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @ApiOperation(value = " Account Validation for new users registered ", response = SuccessDetails.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account confirmation successfully"),
            @ApiResponse(code = 404, message = "Token confirmation Not  Found or Expired ")
    })
    @GetMapping("/validation")
    public ResponseEntity<Object> accountConfirmation(@RequestParam("token") String token) {
        tokenService.findTokenByValue(token);
        SuccessDetails successMessage = new SuccessDetails(LocalDateTime.now(), "Your Account has been confirmed ", null);

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> generateToken(@RequestBody Authentification auth) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
        }catch (Exception e){
            e.printStackTrace();}
        final UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getEmail());
        String webToken=jwt.generateToken(userDetails);
        SuccessDetails successMessage = new SuccessDetails(LocalDateTime.now(), "Login successfully" ,null);
        HttpHeaders response= new HttpHeaders();
        response.set("Authorazation","Bearer "+webToken );
        return ResponseEntity.ok().headers(response).body(successMessage);
    }

    @PostMapping("/ResetPassword")
    public ResponseEntity<Object> passwordReset(@RequestBody ResetPassword resetPassword){
        SuccessDetails successMessage = null;
        Users user = userService.findUserByEmail(resetPassword.getEmail());
        if (user != null){
            TokenResetPassword tokenResetPassword =tokenResetPasswordService.createToken(user);
            mailService.sendResetPasswordEmail(user,tokenResetPassword);
            successMessage = new SuccessDetails(LocalDateTime.now(), "Your Account exist and you can change your password  ", null);

        }

        return ResponseEntity.ok().body(successMessage);
    }
    @ApiOperation(value = " Reset password for user  ", response = SuccessDetails.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reset password  confirmation successfully"),
            @ApiResponse(code = 404, message = "Token confirmation Not  Found or Expired ")
    })
    @GetMapping("/ResetPasswordValidation")
    public ResponseEntity<Object> resetPasswordConfirmation(@RequestParam("token") String token) {
        tokenResetPasswordService.findTokenByValue(token);
        SuccessDetails successMessage = new SuccessDetails(LocalDateTime.now(), "Your Reset Password  has been confirmed ", null);

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PostMapping("/newPassword/{token}")
    public ResponseEntity<SuccessDetails> newPassword (@PathVariable String token , @RequestBody newPasswordEntity newPassword ){
            SuccessDetails successMessage = null;
            TokenResetPassword tokenResetPassword = tokenResetPasswordService.getToken(token);
            if(tokenResetPassword != null){
                if(newPassword.getNewPassword().equals(newPassword.getRepeatNewPassword())){
                    tokenResetPasswordService.updatePassword(token,newPassword.getNewPassword());
                    successMessage = new SuccessDetails(LocalDateTime.now(), "Your password has been changed successfully  ", null);
                }
            }
        return ResponseEntity.ok().body(successMessage);

    }

}
