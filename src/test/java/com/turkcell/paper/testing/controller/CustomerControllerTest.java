package com.turkcell.paper.testing.controller;

import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.entity.CustomerType;
import com.turkcell.paper.testing.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        CustomerDTO dto = new CustomerDTO(1L, "test.user@turkcell.com", CustomerType.INDIVIDUAL);
        when(customerService.getAll()).thenReturn(Collections.singletonList(dto));
        when(customerService.getById(1L)).thenReturn(dto);
        doNothing().when(customerService).deleteById(any());
    }

    @Test
    void getByIdShouldReturnCustomerInfo() throws Exception {
        mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.notificationEmail").value("test.user@turkcell.com"))
                .andExpect(jsonPath("$.customerType").value("INDIVIDUAL"));
    }

    @Test
    void getAllShouldReturnCustomers() throws Exception {
        mockMvc.perform(get("/api/customer/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].notificationEmail").value("test.user@turkcell.com"))
                .andExpect(jsonPath("$[0].customerType").value("INDIVIDUAL"));
    }

    @Test
    void deleteByIdShouldReturnDeletedCustomerId() throws Exception {
        int testedId = 1;
        mockMvc.perform(delete(String.format("/api/customer/%d", testedId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format("The customer with ID: %d has deleted", testedId)))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }
}