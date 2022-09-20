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

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;


    @Before
    public void setUp() {

        Customer testEntity = new Customer();
        testEntity.setId(1L);
        testEntity.setNotificationEmail("test.user@turkcell.com");
        testEntity.setCustomerType(CustomerType.INDIVIDUAL);

        when(customerRepository.save(any())).thenReturn(testEntity);
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(testEntity));
        when(customerRepository.findById(any())).thenReturn(Optional.of(testEntity));
        doNothing().when(customerRepository).deleteById(any());
        when(customerRepository
                .existsByNotificationEmailAndCustomerType("test@turkcell.com", CustomerType.INDIVIDUAL))
                .thenReturn(true);
    }

    @Test
    public void shouldSaveCustomerAndGenerateId() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNotificationEmail("test.user@turkcell.com");
        customerDTO.setCustomerType(CustomerType.INDIVIDUAL);
        CustomerDTO savedDto = customerService.save(customerDTO);
        assertEquals("test.user@turkcell.com", savedDto.getNotificationEmail());
        assertEquals(CustomerType.INDIVIDUAL, savedDto.getCustomerType());
        assertNotNull(savedDto.getId());
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenNotificationEmailExists() {
        CustomerDTO customerDTO = getTestCustomerDTO();
        customerService.save(customerDTO);
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenNotificationEmailNotValid() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNotificationEmail("invalid.com");
        customerDTO.setCustomerType(CustomerType.INDIVIDUAL);
        customerService.save(customerDTO);
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenNotificationEmailNull() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNotificationEmail(null);
        customerDTO.setCustomerType(CustomerType.INDIVIDUAL);
        customerService.save(customerDTO);
    }

    @Test(expected = InvalidCustomerRequestException.class)
    public void shouldThrowExceptionWhenSaveIdNotNull() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(23L);
        customerDTO.setNotificationEmail("test.user@turkcell.com");
        customerDTO.setCustomerType(CustomerType.INDIVIDUAL);
        customerService.save(customerDTO);
    }

    @Test
    public void shouldGetCustomerWhenIdIsValid() {
        CustomerDTO byId = customerService.getById(1L);
        assertNotNull(byId);
    }

    @Test
    public void shouldGetAllCustomers() {
        assertFalse(customerService.getAll().isEmpty());
    }

    @Test
    public void shouldPerformDelete() {
        customerService.deleteById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    private CustomerDTO getTestCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNotificationEmail("test@turkcell.com");
        customerDTO.setCustomerType(CustomerType.INDIVIDUAL);
        return customerDTO;
    }

}