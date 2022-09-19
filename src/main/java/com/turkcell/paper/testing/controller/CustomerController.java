package com.turkcell.paper.testing.controller;

import com.turkcell.paper.testing.dto.CustomerDTO;
import com.turkcell.paper.testing.service.CustomerService;
import com.turkcell.paper.testing.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> getAll() {
        return ResponseEntity.ok().body(customerService.getAll());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.save(customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        customerService.deleteById(id);
        Map<String, Object> body = ResponseUtil.getObjectMap(String.format("The customer with ID: %d has deleted", id));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
