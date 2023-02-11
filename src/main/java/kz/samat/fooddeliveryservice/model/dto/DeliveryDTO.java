package kz.samat.fooddeliveryservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.samat.fooddeliveryservice.model.CustomerType;
import kz.samat.fooddeliveryservice.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representation of delivery used by REST API
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private CustomerType customerType;

    private DeliveryStatus deliveryStatus;

    private LocalDateTime expectedDeliveryTime;

    private Short distanceToDestination;

    private Short riderRating;

    private Short averagePrepareTime;

    private Short timeToReachDestination;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
