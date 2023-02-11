package kz.samat.fooddeliveryservice.service;

import kz.samat.fooddeliveryservice.model.CustomerType;
import kz.samat.fooddeliveryservice.model.DeliveryStatus;
import kz.samat.fooddeliveryservice.model.dto.TicketDTO;
import kz.samat.fooddeliveryservice.model.entity.DeliveryEntity;
import kz.samat.fooddeliveryservice.model.entity.TicketEntity;
import kz.samat.fooddeliveryservice.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link TicketService}
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Captor
    private ArgumentCaptor<TicketEntity> ticketEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<DeliveryEntity> deliveryEntityArgumentCaptor;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService underTest;

    @Test
    void getTicketsSortedByPriority_returnsSortedTickets() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<DeliveryEntity> expectedDeliveryEntityList = getTestDeliveryEntityList();
        List<TicketEntity> expectedTicketEntityList = getTestTicketEntityList(expectedDeliveryEntityList);
        when(ticketRepository.findTicketsSortedByPriority(pageable))
                .thenReturn(new PageImpl<>(expectedTicketEntityList));
        // when
        Page<TicketDTO> actualTicketDTOPage = underTest.getTicketsSortedByPriority(pageable);
        List<TicketDTO> actualTicketDTOList = actualTicketDTOPage.getContent();
        // then
        assertNotNull(actualTicketDTOPage);
        assertFalse(actualTicketDTOPage.isEmpty());
        assertEquals(3, actualTicketDTOList.size());
        assertEquals(expectedTicketEntityList.get(0).getPriority(), actualTicketDTOList.get(0).getPriority());
        assertEquals(expectedTicketEntityList.get(1).getPriority(), actualTicketDTOList.get(1).getPriority());
        assertEquals(expectedTicketEntityList.get(2).getPriority(), actualTicketDTOList.get(2).getPriority());

        for (int i = 0; i < 3; i++) {
            assertEquals(expectedTicketEntityList.get(i).getPriority(), actualTicketDTOList.get(i).getPriority());
            assertEquals(expectedTicketEntityList.get(i).getDelivery().getCustomerType(),
                    actualTicketDTOList.get(i).getDelivery().getCustomerType());
            assertEquals(expectedTicketEntityList.get(i).getDelivery().getDeliveryStatus(),
                    actualTicketDTOList.get(i).getDelivery().getDeliveryStatus());
            assertEquals(expectedTicketEntityList.get(i).getDelivery().getExpectedDeliveryTime(),
                    actualTicketDTOList.get(i).getDelivery().getExpectedDeliveryTime());
            assertEquals(expectedTicketEntityList.get(i).getDelivery().getDistanceToDestination(),
                    actualTicketDTOList.get(i).getDelivery().getDistanceToDestination());
            assertEquals(expectedTicketEntityList.get(i).getDelivery().getRiderRating(),
                    actualTicketDTOList.get(i).getDelivery().getRiderRating());
            assertEquals(expectedTicketEntityList.get(i).getDelivery().getAveragePrepareTime(),
                    actualTicketDTOList.get(i).getDelivery().getAveragePrepareTime());
            assertEquals(expectedTicketEntityList.get(i).getDelivery().getTimeToReachDestination(),
                    actualTicketDTOList.get(i).getDelivery().getTimeToReachDestination());
        }
    }

    @Test
    void createTickets_createsTickets() {
        // given
        List<DeliveryEntity> expectedDeliveryEntityList = getTestDeliveryEntityList();
        List<TicketEntity> expectedTicketEntityList = getTestTicketEntityList(expectedDeliveryEntityList);
        when(deliveryService.getUnpickedDeliveriesWithoutTicket()).thenReturn(expectedDeliveryEntityList);
        when(ticketRepository.save(any())).thenReturn(TicketEntity.builder().build());
        // when
        underTest.createTickets();
        // then
        verify(ticketRepository, times(3)).save(ticketEntityArgumentCaptor.capture());
        verify(deliveryService, times(3)).updateDelivery(deliveryEntityArgumentCaptor.capture());

        List<TicketEntity> actualTicketEntityList = ticketEntityArgumentCaptor.getAllValues();
        List<DeliveryEntity> actualDeliveryEntityList = deliveryEntityArgumentCaptor.getAllValues();

        assertNotNull(actualTicketEntityList);
        assertFalse(actualTicketEntityList.isEmpty());

        for (TicketEntity ticket : actualTicketEntityList) {
            if (ticket.getDelivery().getId().equals(1))
                assertEquals(expectedTicketEntityList.get(0).getPriority(), ticket.getPriority());
            if (ticket.getDelivery().getId().equals(2))
                assertEquals(expectedTicketEntityList.get(1).getPriority(), ticket.getPriority());
            if (ticket.getDelivery().getId().equals(3))
                assertEquals(expectedTicketEntityList.get(2).getPriority(), ticket.getPriority());
        }

        assertNotNull(actualDeliveryEntityList);
        assertFalse(actualDeliveryEntityList.isEmpty());
        for (DeliveryEntity delivery : actualDeliveryEntityList)
            assertNotNull(delivery.getTicket());
    }

    private static List<DeliveryEntity> getTestDeliveryEntityList() {
        List<DeliveryEntity> expectedDeliveryEntityList = List.of(
                DeliveryEntity.builder()
                        .id(1)
                        .customerType(CustomerType.NEW)
                        .deliveryStatus(DeliveryStatus.RECEIVED)
                        .expectedDeliveryTime(LocalDateTime.now().minusHours(1))
                        .distanceToDestination((short) 10)
                        .riderRating((short) 10)
                        .averagePrepareTime((short) 10)
                        .timeToReachDestination((short) 10)
                        .build(),
                DeliveryEntity.builder()
                        .id(2)
                        .customerType(CustomerType.LOYAL)
                        .deliveryStatus(DeliveryStatus.PREPARING)
                        .expectedDeliveryTime(LocalDateTime.now().plusMinutes(20))
                        .distanceToDestination((short) 20)
                        .riderRating((short) 20)
                        .averagePrepareTime((short) 20)
                        .timeToReachDestination((short) 20)
                        .build(),
                DeliveryEntity.builder()
                        .id(3)
                        .customerType(CustomerType.VIP)
                        .deliveryStatus(DeliveryStatus.PREPARING)
                        .expectedDeliveryTime(LocalDateTime.now().plusMinutes(50))
                        .distanceToDestination((short) 30)
                        .riderRating((short) 30)
                        .averagePrepareTime((short) 30)
                        .timeToReachDestination((short) 30)
                        .build());
        return expectedDeliveryEntityList;
    }

    private static List<TicketEntity> getTestTicketEntityList(List<DeliveryEntity> expectedDeliveryEntityList) {
        return List.of(
                TicketEntity.builder()
                        .priority((short) 30) // initial 10 + 20 for being late
                        .delivery(expectedDeliveryEntityList.get(0))
                        .build(),
                TicketEntity.builder()
                        .priority((short) 30) // initial 10 + 10 for loyalty + 10 for potential lateness
                        .delivery(expectedDeliveryEntityList.get(1))
                        .build(),
                TicketEntity.builder()
                        .priority((short) 40) // initial 10 + 20 for VIP + 10 for potential lateness
                        .delivery(expectedDeliveryEntityList.get(2))
                        .build());
    }
}