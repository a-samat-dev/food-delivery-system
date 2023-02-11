package kz.samat.fooddeliveryservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Class that represents ticket record in DB
 *
 * Created by Samat Abibulla on 2023-01-31
 */
@Entity
@Table(name = "tickets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "ticket")
    private DeliveryEntity delivery;

    @Column(name = "priority", nullable = false)
    private Short priority;

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
