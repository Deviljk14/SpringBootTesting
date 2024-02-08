package com.turkcell.paper.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String message;
}
