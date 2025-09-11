package com.dp.vvgram.contollerAdvices;

import com.dp.vvgram.dtos.ExceptionDto;
import com.dp.vvgram.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ExceptionDto> handleInvalidPasswordException(InvalidPasswordException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserAlreadyFollowingUserException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyFollowingUserException(UserAlreadyFollowingUserException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserCannotFollowUserException.class)
    public ResponseEntity<ExceptionDto> handleUserCannotFollowUserException(UserCannotFollowUserException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserCannotUnfollowUserException.class)
    public ResponseEntity<ExceptionDto> handleUserCannotUnfollowUserException(UserCannotUnfollowUserException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserIsNotFollowingUserException.class)
    public ResponseEntity<ExceptionDto> handleUserIsNotFollowingException(UserIsNotFollowingUserException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, HttpStatus.BAD_REQUEST
        );
    }

}
