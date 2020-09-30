package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.thesis.scm.dto.requests.UserRequest;
import pl.lodz.p.it.thesis.scm.dto.responses.AppResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.ErrorResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.SuccessResponse;
import pl.lodz.p.it.thesis.scm.service.implementation.AuthenticationService;

import javax.validation.Valid;

@RestController
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AppResponse> registerNewUserAccount(@Valid @RequestBody UserRequest userRequest) {

        authenticationService.registerNewUserAccount(userRequest);
        return new ResponseEntity<>(new SuccessResponse("success"), HttpStatus.OK);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Bean validation error");

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.addFullMessage(errorMessage);
        });
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
