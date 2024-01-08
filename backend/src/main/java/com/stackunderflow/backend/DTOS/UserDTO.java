package com.stackunderflow.backend.DTOS;

import com.stackunderflow.backend.model.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String email;
    private String phoneNumber;
    private Double points;
    private List<Badge> badges;
    private List<Vote> votes;
}
