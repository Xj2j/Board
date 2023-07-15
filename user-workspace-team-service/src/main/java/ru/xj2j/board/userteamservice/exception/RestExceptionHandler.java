package ru.xj2j.board.userteamservice.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.xj2j.board.userteamservice.exception.ApiError;
import ru.xj2j.board.userteamservice.exception.WorkspaceMemberNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    //@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError("Malformed JSON Request", ex.getMessage());
        return new ResponseEntity(apiError, status);
    }

    //@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = new ApiError("Method Argument Not Valid", ex.getMessage(), errors);
        return new ResponseEntity<>(apiError, status);
    }

    //@Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ApiError("No Handler Found", ex.getMessage()), status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError("Internal Exception", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex) {
        ApiError apiError = new ApiError("Bad request", ex.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler({WorkspaceMemberNotFoundException.class, EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundEx(WorkspaceMemberNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError("Workspace member not found", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamAlreadyExistsException.class)
    protected ResponseEntity<Object> handleTeamAlreadyExistsEx(TeamAlreadyExistsException ex) {
        ApiError apiError = new ApiError("Team Already Exists", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WorkspaceNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(WorkspaceNotFoundException ex) {
        ApiError apiError = new ApiError("Workspace not found", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Object> handleForbiddenEx(ForbiddenException ex) {
        ApiError apiError = new ApiError("User not a member of workspace", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundEx(UserNotFoundException ex) {
        ApiError apiError = new ApiError("User not found", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        captureException(ex);
        return ResponseEntity.badRequest().body("Something went wrong, please try again later");
    }*/

}