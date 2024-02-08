package com.turkcell.paper.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerDTO {
    private Long id;
    private String username;
    private String email;
    private String newPassword;
    private String profile_pic;
    private String oldPassword;
    private String confPassword;
    private Boolean isChangePassword;
    private String user_role;
}
