package kz.samat.fooddeliveryservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.samat.fooddeliveryservice.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    @JsonFormat(pattern = AppConstants.DEFAULT_OFFSET_DATE_TIME_FORMAT)
    private OffsetDateTime dateTime;
    private HttpStatus status;
    private int code;
    private String error;
    private String message;
    private Map<String, String> invalidFields;
}
