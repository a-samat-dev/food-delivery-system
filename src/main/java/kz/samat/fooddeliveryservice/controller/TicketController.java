package kz.samat.fooddeliveryservice.controller;

import kz.samat.fooddeliveryservice.model.dto.TicketDTO;
import kz.samat.fooddeliveryservice.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * APIs for working with tickets
 *
 * Created by Samat Abibulla on 2023-01-31
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    /**
     * Returns tickets sorted by priority (desc)
     *
     * @param page number
     * @param size of page
     * @return page object with tickets
     */
    @GetMapping("/undelivered")
    public Page<TicketDTO> getTicketsSortedByPriority(@RequestParam(required = false, defaultValue = "0") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("Incoming request to get list of sorted tickets: page={}, size={}", page, size);
        return ticketService.getTicketsSortedByPriority(PageRequest.of(page, size));
    }
}
