package com.stackunderflow.backend.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionDTOAns {
    private Long suggestionId;
    private String body;
    private Long userId;
    private LocalDateTime savedDate;
    private Boolean accepted;
    private LocalDateTime acceptedOnDate;
}
