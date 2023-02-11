package kz.samat.fooddeliveryservice.service;

import kz.samat.fooddeliveryservice.model.CustomerType;
import kz.samat.fooddeliveryservice.model.dto.TicketDTO;
import kz.samat.fooddeliveryservice.model.entity.DeliveryEntity;
import kz.samat.fooddeliveryservice.model.entity.TicketEntity;
import kz.samat.fooddeliveryservice.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class that holds business logic related to ticket sub-domain
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@Slf4j
@Service
@AllArgsConstructor
public class TicketService {

    private final ModelMapper modelMapper;

    private final DeliveryService deliveryService;

    private final TicketRepository ticketRepository;

    /**
     * Retrieves the list of tickets sorted by ticket priority
     *
     * @param pageable {@link Pageable}
     * @return the list of tickets
     */
    public Page<TicketDTO> getTicketsSortedByPriority(Pageable pageable) {
        Page<TicketEntity> ticketEntityPage = ticketRepository.findTicketsSortedByPriority(pageable);
        List<TicketDTO> ticketDTOList = ticketEntityPage.stream()
                .map(entity -> modelMapper.map(entity, TicketDTO.class))
                .toList();

        return new PageImpl<>(ticketDTOList, pageable, ticketEntityPage.getTotalElements());
    }

    /**
     * Creates tickets for deliveries without ticket
     */
    @Scheduled(cron = "${TICKET_CREATION_JOB_PERIOD}")
    public void createTickets() {
        log.info("Ticket creation job has started, datetime={}", LocalDateTime.now());
        deliveryService.getUnpickedDeliveriesWithoutTicket().parallelStream().forEach(this::saveTicket);
        log.info("Ticket creation job has finished, datetime={}", LocalDateTime.now());
    }

    /**
     * Creates ticket by using delivery information and updates delivery also
     *
     * @param delivery {@link DeliveryEntity}
     */
    @Transactional
    public void saveTicket(DeliveryEntity delivery) {
        TicketEntity ticket = TicketEntity.builder()
                .priority(getPriority(delivery))
                .delivery(delivery)
                .build();
        ticket = ticketRepository.save(ticket);
        delivery.setTicket(ticket);
        deliveryService.updateDelivery(delivery);
    }

    /**
     * Generates ticket priority according to delivery information
     *
     * @param delivery {@link DeliveryEntity}
     * @return ticket priority
     */
    private Short getPriority(DeliveryEntity delivery) {
        short priority = 10;
        short estimatedTimeToDeliver = 0;
        estimatedTimeToDeliver += delivery.getAveragePrepareTime() != null ? delivery.getAveragePrepareTime() : 0;
        estimatedTimeToDeliver += delivery.getTimeToReachDestination() != null
                ? delivery.getTimeToReachDestination() : 0;

        if (delivery.getCustomerType() == CustomerType.VIP)
            priority += 20;
        if (delivery.getCustomerType() == CustomerType.LOYAL)
            priority += 10;
        if (delivery.getExpectedDeliveryTime() != null) {
            if (LocalDateTime.now().isAfter(delivery.getExpectedDeliveryTime()))
                priority += 20;
            else {
                if (estimatedTimeToDeliver > 0 && LocalDateTime.now().plusMinutes(estimatedTimeToDeliver)
                        .isAfter(delivery.getExpectedDeliveryTime()))
                    priority += 10;
            }
        }

        return priority;
    }
}
