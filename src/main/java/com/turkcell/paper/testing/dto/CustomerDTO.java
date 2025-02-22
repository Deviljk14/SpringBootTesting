package com.turkcell.paper.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String profile_pic;
    private String user_role;
}
