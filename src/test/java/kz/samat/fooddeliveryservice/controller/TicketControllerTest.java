package kz.samat.fooddeliveryservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kz.samat.fooddeliveryservice.RestResponsePage;
import kz.samat.fooddeliveryservice.model.dto.TicketDTO;
import kz.samat.fooddeliveryservice.repository.DeliveryRepository;
import kz.samat.fooddeliveryservice.repository.TicketRepository;
import kz.samat.fooddeliveryservice.service.TestDataService;
import kz.samat.fooddeliveryservice.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

/**
 * Integration tests for {@link TicketController}
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml",
        properties = {"TICKET_CREATION_JOB_PERIOD = 0 * * * * *"})
class TicketControllerTest {

    private static final String TEST_USERNAME = "test-user";

    private static final String TEST_PASSWORD = "test-user";

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void getTicketsSortedByPriority_returnsUnauthorized_whenUserCredentialsNotPassed() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tickets/undelivered")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void getTicketsSortedByPriority() throws Exception {
        // given
        ticketService.createTickets();
        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tickets/undelivered?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic(TEST_USERNAME, TEST_PASSWORD))
                        .param("page", "0")
                        .param("size", "20"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // then
        Page<TicketDTO> actualTicketDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<RestResponsePage<TicketDTO>>() {
                });

        assertNotNull(actualTicketDTOList);
        assertFalse(actualTicketDTOList.isEmpty());

        List<TicketDTO> content = actualTicketDTOList.getContent();

        // checking if tickets are sorted by priority
        for (int i = 1; i < content.size(); i++) {
            int res = content.get(i).getPriority().compareTo(content.get(i - 1).getPriority());
            assertTrue(res <= 0);
        }
    }
}