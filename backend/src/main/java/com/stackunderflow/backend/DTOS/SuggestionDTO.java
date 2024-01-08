package com.stackunderflow.backend.DTOS;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionDTO {
    private String body;
    private LocalDateTime createdDate;
    private Boolean accepted;
}
