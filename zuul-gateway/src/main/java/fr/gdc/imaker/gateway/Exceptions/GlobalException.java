package fr.gdc.imaker.gateway.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception) {
        ErrorDetails errorDetails = new ErrorDetails( LocalDateTime.now(), "Input validation error",
                exception.getBindingResult().getFieldError().getDefaultMessage().toUpperCase(Locale.ROOT));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> customBadCredentialsException(BadCredentialsException badCredentialsException){
        ErrorDetails errorDetails= new ErrorDetails(LocalDateTime.now(),"Password does not match" ,
                badCredentialsException.getLocalizedMessage().toUpperCase(Locale.ROOT));
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> customNoSuchElementException( NoSuchElementException noSuchElementException){
        ErrorDetails errorDetails=  new ErrorDetails(LocalDateTime.now(),"404 Not Found"
                                    ,noSuchElementException.getLocalizedMessage().toUpperCase(Locale.ROOT));

    return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
/*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> customException(Exception exception){
        ErrorDetails errorDetails= new ErrorDetails(LocalDateTime.now(),"Something went wrong !!!",exception.getLocalizedMessage().toUpperCase(Locale.ROOT));
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
*/

}
