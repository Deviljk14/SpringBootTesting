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

import java.sql.SQLException;
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
    void setUp() throws SQLException {
        CustomerDTO dto = new CustomerDTO(1L, "testuser", "test.user@turkcell.com", "password");
        when(customerService.getAll()).thenReturn(Collections.singletonList(dto));
    }



    @Test
    void getAllShouldReturnCustomers() throws Exception {
        mockMvc.perform(get("/api/customer/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].notificationEmail").value("test.user@turkcell.com"))
                .andExpect(jsonPath("$[0].customerType").value("INDIVIDUAL"));
    }
}