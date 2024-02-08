package com.turkcell.paper.testing.controller;

import com.turkcell.paper.testing.dto.ContactUsDTO;
import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.dto.UpdateCustomerDTO;
import com.turkcell.paper.testing.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> getAll() throws SQLException {
        return ResponseEntity.ok().body(customerService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customerDTO) throws SQLException {
        return ResponseEntity.ok(customerService.save(customerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDTO> login(@RequestBody CustomerDTO customerDTO) throws SQLException {
        return ResponseEntity.ok(customerService.login(customerDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<CustomerDTO> update(@RequestBody UpdateCustomerDTO updateCustomerDTO) throws SQLException{
        return ResponseEntity.ok(customerService.update(updateCustomerDTO));
    }

    @PostMapping("/contactus")
    public void contactUs(@RequestBody ContactUsDTO contactUsDTO) {
        customerService.contactUs(contactUsDTO);
    }
}
