package com.event.eventweb.exception.handler;

import com.event.eventweb.dto.PlatformResponse;
import com.event.eventweb.exception.DataNotFoundException;
import com.event.eventweb.exception.InvalidDataException;
import com.event.eventweb.exception.ResourceAlreadyExistException;
import com.event.eventweb.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Anusha Shresthah
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServerException.class)
    protected ResponseEntity<Object> handleServerException(ServerException ex) {
        log.error("Exception message : {}", ex.getServerResponse().getMessage());

        if (log.isDebugEnabled()) {
            log.error("Exception : ", ex);
        }

        PlatformResponse platformResponse = ex.getServerResponse();

        PlatformResponse genericResponse = new PlatformResponse();
        genericResponse.setSuccess(platformResponse.isSuccess());
        genericResponse.setMessage(platformResponse.getMessage());
        genericResponse.setUnicodeMessage(platformResponse.getUnicodeMessage());
       // genericResponse.setProcessingCode(ex.getProcessingCode());
        return buildResponseEntity(genericResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<Object> handleInvalidDataException(DataNotFoundException ex) {
        log.error("Exception message : {}", ex.getMessage());

        if (log.isDebugEnabled()) {
            log.error("Exception : ", ex);
        }
        return buildResponseEntity(ex.getServerResponse(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex) {
        log.error("Exception message : {}", ex.getMessage());

        if (log.isDebugEnabled()) {
            log.error("Exception : ", ex);
        }
        return buildResponseEntity(ex.getServerResponse(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    protected ResponseEntity<Object> handleResourceAlreadyExistException(ResourceAlreadyExistException ex) {
        log.error("Exception message : {}", ex.getMessage());

        if (log.isDebugEnabled()) {
            log.error("Exception : ", ex);
        }
        return buildResponseEntity(ex.getPlatformResponse(), HttpStatus.OK);
    }

    private ResponseEntity<Object> buildResponseEntity(PlatformResponse genericResponse, HttpStatus httpStatus) {
        return new ResponseEntity<>(genericResponse,httpStatus);
    }
}
