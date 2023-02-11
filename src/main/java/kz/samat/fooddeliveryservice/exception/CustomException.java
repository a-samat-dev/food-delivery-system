package kz.samat.fooddeliveryservice.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Custom unchecked exception that must be thrown if there is an error in business logic
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CustomException extends RuntimeException {

    private HttpStatus status;
    private String error;
    private String message;
}
