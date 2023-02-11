package kz.samat.fooddeliveryservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representation of ticket used by REST API
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Integer id;

    private DeliveryDTO delivery;

    private Short priority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
