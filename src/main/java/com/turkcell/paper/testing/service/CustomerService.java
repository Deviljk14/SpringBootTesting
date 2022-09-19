package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDTO);
    CustomerDTO getById(Long id);
    List<CustomerDTO> getAll();
    void deleteById(Long id);
}
