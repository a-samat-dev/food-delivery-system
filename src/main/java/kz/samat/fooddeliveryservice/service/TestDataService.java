package kz.samat.fooddeliveryservice.service;

import kz.samat.fooddeliveryservice.model.CustomerType;
import kz.samat.fooddeliveryservice.model.DeliveryStatus;
import kz.samat.fooddeliveryservice.model.entity.DeliveryEntity;
import kz.samat.fooddeliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains test data creation logic. Applicable only for local profile. (No test coverage for this class)
 *
 * Created by Samat Abibulla 2023-02-08
 */
@Slf4j
@Service
@Profile("local")
@RequiredArgsConstructor
public class TestDataService {

    private final DeliveryRepository deliveryRepository;

    /**
     * Creates testing deliveries. It's executed only under local profile.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void createTestDataOnStartUp() {
        List<DeliveryEntity> existingDeliveryEntityList = deliveryRepository.findAll();

        if (!existingDeliveryEntityList.isEmpty())
            return;

        log.info("Test data creation begin");
        List<DeliveryEntity> deliveryEntityList = new ArrayList<>(100_000);
        for (int i = 0; i < 100_000; i++) {
            deliveryEntityList.add(createDeliveryEntity(i));
        }

        deliveryRepository.saveAll(deliveryEntityList);
        log.info("Test data creation finished. Test data size: {}", deliveryEntityList.size());
    }

    /**
     * Creates delivery using customer index
     *
     * @param i cusomer index
     * @return newly created delivery
     */
    private DeliveryEntity createDeliveryEntity(int i) {
        return DeliveryEntity.builder()
                .customerType(getCustomerType(i))
                .deliveryStatus(getDeliveryStatus())
                .expectedDeliveryTime(getExpectedDeliveryTime(i))
                .distanceToDestination((short) ((Math.random() * (60 - 1)) + 1))
                .riderRating((short) (Math.random() * 10))
                .averagePrepareTime((short) ((Math.random() * (60 - 5)) + 5))
                .timeToReachDestination((short) ((Math.random() * (60 - 5)) + 5))
                .build();
    }

    /**
     * Returns customer type depending on customer index.
     * Every 200 customer is VIP.
     * Every 100 customer is LOYAL (if it's not VIP)
     *
     * @param i customer index
     * @return type of customer
     */
    private static CustomerType getCustomerType(int i) {
        CustomerType customerType = CustomerType.NEW;

        if (i % 200 == 0)
            customerType = CustomerType.VIP;
        else if (i % 100 == 0)
            customerType = CustomerType.LOYAL;

        return customerType;
    }

    /**
     * Randomly chooses delivery status
     *
     * @return delivery status
     */
    private static DeliveryStatus getDeliveryStatus() {
        int idx = (int) (Math.random() * (4 - 1)) + 1;

        if (idx == 1)
            return DeliveryStatus.RECEIVED;
        else if (idx == 2)
            return DeliveryStatus.PREPARING;
        else if (idx == 3)
            return DeliveryStatus.PICKED_UP;
        else
            return DeliveryStatus.DELIVERED;
    }

    /**
     * Returns expected delivery time of a food
     *
     * @param i customer index
     * @return expected time
     */
    private static LocalDateTime getExpectedDeliveryTime(int i) {
        if (i % 1000 == 0)
            return LocalDateTime.now()
                    .minusHours((long) (Math.random() * 2))
                    .minusMinutes((long) ((Math.random() * (60 - 1)) + 1));

        return LocalDateTime.now()
                .plusHours((long) (Math.random() * 5))
                .plusMinutes((long) ((Math.random() * (60 - 1)) + 1));
    }
}
