package learn.field_agent.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintException(SQLIntegrityConstraintViolationException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<String> handleHttpMediaException(HttpMediaTypeException ex) {
        return new ResponseEntity<String>("There is an error in one of the data types submitting in your request.",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<String>("Argument passed in does not match method data type.",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PermissionDeniedDataAccessException.class)
    public ResponseEntity<String> handlePermissionDeniedException(PermissionDeniedDataAccessException ex) {
        return new ResponseEntity<String>("Permission to access a database element was denied.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        return new ResponseEntity<String>("Something went wrong when trying to access the database. " +
                "We are unable to show the details",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<String>("An unidentified error occured. Request failed.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
