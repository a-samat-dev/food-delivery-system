package kz.samat.fooddeliveryservice.service;

import kz.samat.fooddeliveryservice.model.DeliveryStatus;
import kz.samat.fooddeliveryservice.model.entity.DeliveryEntity;
import kz.samat.fooddeliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to work with {@link DeliveryEntity} model
 *
 * Created by Samat Abibulla on 2023-01-31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private static final List<String> UNPICKED_DELIVERY_STATUSES = List.of(DeliveryStatus.RECEIVED.name(),
            DeliveryStatus.PREPARING.name());

    private final DeliveryRepository deliveryRepository;

    /**
     * Returns the list of deliveries without tickets created
     *
     * @return list of deliveries
     */
    public List<DeliveryEntity> getUnpickedDeliveriesWithoutTicket() {
        return deliveryRepository.findUnpickedDeliveriesWithoutTicket(UNPICKED_DELIVERY_STATUSES);
    }

    /**
     * Updates existing delivery
     *
     * @param delivery existing record
     */
    public void updateDelivery(DeliveryEntity delivery) {
        deliveryRepository.save(delivery);
    }
}
