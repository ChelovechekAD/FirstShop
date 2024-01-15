package com.example.firstshop.exception.handler;

import com.example.firstshop.dto.response.ExceptionResponse;
import com.example.firstshop.exception.ListExceptions;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.InvalidObjectException;
import java.time.LocalDateTime;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Slf4j
@RestControllerAdvice
public class handlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ListExceptions.InvalidInputException.class)
    public ResponseEntity<?> invalidInputException(ListExceptions.InvalidInputException e){
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(ListExceptions.UserAuthenticationException.class)
    public ResponseEntity<?> userAuthenticationException(ListExceptions.UserAuthenticationException e){
        return buildExceptionResponse(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler(ListExceptions.UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(ListExceptions.UserNotFoundException e){
        return buildExceptionResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(ListExceptions.LoginAlreadyUsed.class)
    public ResponseEntity<?> loginAlreadyUsed(ListExceptions.LoginAlreadyUsed e){
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(ListExceptions.UserIsBlocked.class)
    public ResponseEntity<?> userIsBlocked(ListExceptions.UserIsBlocked e){
        return buildExceptionResponse(HttpStatus.UNAUTHORIZED, e);
    }
    @ExceptionHandler(ListExceptions.WrongPasswordException.class)
    public ResponseEntity<?> wrongPasswordException(ListExceptions.WrongPasswordException e){
        return buildExceptionResponse(HttpStatus.UNAUTHORIZED, e);
    }

    private ResponseEntity<Object> buildExceptionResponse(HttpStatusCode code, Exception e){
        var exceptionResponse = ExceptionResponse.builder()
                .HttpStatusCode(code.value())
                .ExceptionMessage(e.getMessage())
                .TimeStamp(LocalDateTime.now())
                .build();
        log.debug(e.getMessage());
        return ResponseEntity.status(code).body(exceptionResponse);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode code,
                                                                  @NonNull WebRequest request){
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(collectingAndThen(
                        toList(),
                        details -> ResponseEntity.status(code)
                                .body(ExceptionResponse.builder()
                                        .HttpStatusCode(code.value())
                                        .ExceptionMessage("Validation Failed")
                                        .ExceptionDetails(details)
                                        .TimeStamp(LocalDateTime.now())
                                        .build())
                ));
    }
}
