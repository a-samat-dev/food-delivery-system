package kz.samat.fooddeliveryservice.exception;

import kz.samat.fooddeliveryservice.model.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

/**
 * Exception handler for REST API calls
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException ex) {
        log.error("Exception occurred: {}", ex.getMessage());
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .dateTime(OffsetDateTime.now())
                .status(ex.getStatus())
                .code(ex.getStatus().value())
                .error(ex.getError())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponseDTO, ex.getStatus());
    }
}
