package kz.samat.fooddeliveryservice.service;

import kz.samat.fooddeliveryservice.model.DeliveryStatus;
import kz.samat.fooddeliveryservice.model.entity.DeliveryEntity;
import kz.samat.fooddeliveryservice.repository.DeliveryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DeliveryService}
 *
 * Created by Samat Abibulla on 2023-01-30
 */
@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Captor
    private ArgumentCaptor<List<String>> deliveryStatusesArgumentCaptor;

    @Captor
    private ArgumentCaptor<DeliveryEntity> deliveryCaptor;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService underTest;

    @Test
    void getUnpickedDeliveriesWithoutTicket_returnsEmptyList() {
        // given
        Set<String> expectedDeliveryStatuses = Set.of(DeliveryStatus.RECEIVED.name(), DeliveryStatus.PREPARING.name());
        when(deliveryRepository.findUnpickedDeliveriesWithoutTicket(any())).thenReturn(Collections.emptyList());
        // when
        List<DeliveryEntity> actual = underTest.getUnpickedDeliveriesWithoutTicket();
        // then
        assertNotNull(actual);
        assertTrue(actual.isEmpty());

        verify(deliveryRepository).findUnpickedDeliveriesWithoutTicket(deliveryStatusesArgumentCaptor.capture());
        List<String> actualDeliveryStatuses = deliveryStatusesArgumentCaptor.getValue();

        assertEquals(expectedDeliveryStatuses.size(), actualDeliveryStatuses.size());
        assertTrue(expectedDeliveryStatuses.containsAll(actualDeliveryStatuses));
    }

    @Test
    void getUnpickedDeliveriesWithoutTicket_returnsDeliveryList() {
        // given
        List<DeliveryEntity> expectedDeliveryList = List.of(DeliveryEntity.builder().build(),
                DeliveryEntity.builder().build());
        Set<String> expectedDeliveryStatuses = Set.of(DeliveryStatus.RECEIVED.name(), DeliveryStatus.PREPARING.name());
        when(deliveryRepository.findUnpickedDeliveriesWithoutTicket(any())).thenReturn(expectedDeliveryList);
        // when
        List<DeliveryEntity> actualDeliveryList = underTest.getUnpickedDeliveriesWithoutTicket();
        // then
        assertNotNull(actualDeliveryList);
        assertFalse(actualDeliveryList.isEmpty());
        assertEquals(expectedDeliveryList, actualDeliveryList);

        verify(deliveryRepository).findUnpickedDeliveriesWithoutTicket(deliveryStatusesArgumentCaptor.capture());
        List<String> actualDeliveryStatuses = deliveryStatusesArgumentCaptor.getValue();

        assertEquals(expectedDeliveryStatuses.size(), actualDeliveryStatuses.size());
        assertTrue(expectedDeliveryStatuses.containsAll(actualDeliveryStatuses));
    }

    @Test
    void updateDelivery() {
        // given
        DeliveryEntity expected = DeliveryEntity.builder().id(1).build();
        // when
        underTest.updateDelivery(expected);
        //
        verify(deliveryRepository).save(deliveryCaptor.capture());
        DeliveryEntity actual = deliveryCaptor.getValue();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}