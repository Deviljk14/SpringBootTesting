package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.entity.Customer;
import com.turkcell.paper.testing.exception.CustomerNotFoundException;
import com.turkcell.paper.testing.exception.InvalidCustomerRequestException;
import com.turkcell.paper.testing.repository.CustomerRepository;
import com.turkcell.paper.testing.util.EmailUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        this.validateSave(customerDTO);
        return toDto(customerRepository.save(toEntity(customerDTO)));
    }

    private void validateSave(CustomerDTO customerDTO) {
        validateId(customerDTO);
        validateNotificationEmail(customerDTO);
        validateNotificationEmailExistance(customerDTO);
    }

    private void validateNotificationEmail(CustomerDTO customerDTO) {
        if (!EmailUtil.validate(customerDTO.getNotificationEmail())) {
            throw new InvalidCustomerRequestException("Notification email is not valid");
        }
    }

    private void validateNotificationEmailExistance(CustomerDTO customerDTO) {
        if (isCustomerExist(customerDTO)) {
            throw new InvalidCustomerRequestException("Notification email has already used with the customer type");
        }
    }

    private void validateId(CustomerDTO customerDTO) {
        if (customerDTO.getId() != null) {
            throw new InvalidCustomerRequestException("Customer ID must be null while saving");
        }
    }

    @Override
    public CustomerDTO getById(Long id) {
        return customerRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        this.getById(id);
        customerRepository.deleteById(id);
    }

    public CustomerDTO toDto(Customer entity) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setCustomerType(entity.getCustomerType());
        dto.setNotificationEmail(entity.getNotificationEmail());
        return dto;
    }

    public Customer toEntity(CustomerDTO dto) {
        Customer entity = new Customer();
        entity.setId(dto.getId());
        entity.setCustomerType(dto.getCustomerType());
        entity.setNotificationEmail(dto.getNotificationEmail());
        return entity;
    }

    public boolean isCustomerExist(CustomerDTO dto) {
        return customerRepository.existsByNotificationEmailAndCustomerType(dto.getNotificationEmail(), dto.getCustomerType());
    }
}
