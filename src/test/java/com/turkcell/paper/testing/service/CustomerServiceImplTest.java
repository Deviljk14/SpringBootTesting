package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.entity.Customer;
import com.turkcell.paper.testing.entity.CustomerType;
import com.turkcell.paper.testing.exception.InvalidCustomerRequestException;
import com.turkcell.paper.testing.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerServiceDBConnect customerServiceDBConnect;

    @InjectMocks
    CustomerServiceImpl customerService;


    @Before
    public void setUp() throws SQLException {

        CustomerDTO testDTO = new CustomerDTO();
        testDTO.setId(1L);
        testDTO.setEmail("test.user@turkcell.com");
        testDTO.setUsername("testuser");
        testDTO.setPassword("password");

        when(customerServiceDBConnect.save(any())).thenReturn(testDTO);
        when(customerServiceDBConnect.getAll()).thenReturn(Collections.singletonList(testDTO));
        when(customerServiceDBConnect
                .checkEmailAlreadyExists("test@turkcell.com"))
                .thenReturn(true);
    }

    @Test
    public void shouldSaveCustomerAndGenerateId() throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@gmail.com");
        customerDTO.setUsername("test");
        customerDTO.setPassword("password");
        CustomerDTO savedDto = customerService.save(customerDTO);
        customerDTO.setEmail("test2@gmail.com");
        customerDTO.setUsername("test");
        customerDTO.setPassword("password");
        assertNotNull(savedDto.getId());
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenNotificationEmailExists() throws SQLException {
        CustomerDTO customerDTO = getTestCustomerDTO();
        customerService.save(customerDTO);
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenNotificationEmailNotValid() throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@gmail.com");
        customerDTO.setUsername("test");
        customerDTO.setPassword("password");
        customerService.save(customerDTO);
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenNotificationEmailNull() throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@gmail.com");
        customerDTO.setUsername("test");
        customerDTO.setPassword("password");
        customerService.save(customerDTO);
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenSaveIdNotNull() throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(23L);
        customerDTO.setEmail("test@gmail.com");
        customerDTO.setUsername("test");
        customerDTO.setPassword("password");
        customerService.save(customerDTO);
    }


    @Test
    public void shouldGetAllCustomers() throws SQLException {
        assertFalse(customerService.getAll().isEmpty());
    }

    private CustomerDTO getTestCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@gmail.com");
        customerDTO.setUsername("test");
        customerDTO.setPassword("password");
        return customerDTO;
    }

}