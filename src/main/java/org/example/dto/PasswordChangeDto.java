package org.example.dto;

import lombok.Data;

@Data
public class PasswordChangeDto {
    private String username;
    private String oldPassword;
    private String newPassword;
}
