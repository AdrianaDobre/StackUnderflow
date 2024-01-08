package com.stackunderflow.backend.DTOS;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUserDTO {
    private String username;
    private String phoneNumber;
}
