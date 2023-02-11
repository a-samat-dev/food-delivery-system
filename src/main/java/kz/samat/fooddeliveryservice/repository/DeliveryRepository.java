package kz.samat.fooddeliveryservice.repository;

import kz.samat.fooddeliveryservice.model.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link DeliveryEntity}
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Integer> {

    @Query(value = "select * from deliveries d where delivery_status in (:deliveryStatuses) and ticket_id is null;",
            nativeQuery = true)
    List<DeliveryEntity> findUnpickedDeliveriesWithoutTicket(
            @Param("deliveryStatuses") List<String> deliveryStatuses);
}
