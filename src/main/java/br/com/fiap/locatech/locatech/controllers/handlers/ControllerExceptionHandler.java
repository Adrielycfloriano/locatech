package br.com.fiap.locatech.locatech.controllers.handlers;

import br.com.fiap.locatech.locatech.Services.exceptions.ResourceNotFoundException;
import br.com.fiap.locatech.locatech.dtos.ResourceNotFoundDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDTO> handleResourceNotFound(ResourceNotFoundException e) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value())
                .body(new ResourceNotFoundDTO(e.getMessage(), status.value()));
    }
}
