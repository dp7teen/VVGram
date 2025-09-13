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
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException e) {
        return exceptionWrapper(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ExceptionDto> handleInvalidPasswordException(InvalidPasswordException e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyFollowingUserException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyFollowingUserException(UserAlreadyFollowingUserException e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserCannotFollowUserException.class)
    public ResponseEntity<ExceptionDto> handleUserCannotFollowUserException(UserCannotFollowUserException e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserCannotUnfollowUserException.class)
    public ResponseEntity<ExceptionDto> handleUserCannotUnfollowUserException(UserCannotUnfollowUserException e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsNotFollowingUserException.class)
    public ResponseEntity<ExceptionDto> handleUserIsNotFollowingException(UserIsNotFollowingUserException e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleLikeNotFoundException(LikeNotFoundException e) {
        return exceptionWrapper(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostingServiceNotAvailableException.class)
    public ResponseEntity<ExceptionDto> handlePostingServiceNotAvailableException(PostingServiceNotAvailableException e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlePostNotFoundException(PostNotFoundException e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCommentNotFoundException(CommentNotFoundException e) {
        return exceptionWrapper(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        return exceptionWrapper(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionDto> exceptionWrapper(Exception e, HttpStatus status) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(
                exceptionDto, status
        );
    }

}
