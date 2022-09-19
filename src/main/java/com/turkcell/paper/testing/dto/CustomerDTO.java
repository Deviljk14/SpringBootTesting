package com.turkcell.paper.testing.dto;

import com.turkcell.paper.testing.entity.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String notificationEmail;
    private CustomerType customerType;
}
