package kz.samat.fooddeliveryservice.model.entity;

import jakarta.persistence.*;
import kz.samat.fooddeliveryservice.model.CustomerType;
import kz.samat.fooddeliveryservice.model.DeliveryStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Class that represents delivery record in DB
 *
 * Created by Samat Abibulla on 2023-01-31
 */
@Entity
@Table(name = "deliveries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus;

    @Column(name = "expected_delivery_time", nullable = false)
    private LocalDateTime expectedDeliveryTime;

    @Column(name = "distance_to_destination")
    private Short distanceToDestination;

    @Column(name = "rider_rating")
    private Short riderRating;

    @Column(name = "average_prepare_time")
    private Short averagePrepareTime;

    @Column(name = "time_to_reach_destination")
    private Short timeToReachDestination;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private TicketEntity ticket;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null)
            this.createdAt = LocalDateTime.now();

        this.updatedAt = LocalDateTime.now();
    }
}