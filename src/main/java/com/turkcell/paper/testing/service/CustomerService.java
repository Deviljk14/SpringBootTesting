package com.turkcell.paper.testing.service;

import com.turkcell.paper.testing.dto.ContactUsDTO;
import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.dto.UpdateCustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDTO) throws SQLException;

    CustomerDTO login(CustomerDTO customerDTO) throws SQLException;

    CustomerDTO update(UpdateCustomerDTO updateCustomerDTO) throws SQLException;

    void contactUs(ContactUsDTO contactUsDTO);

    List<CustomerDTO> getAll() throws SQLException;
}
