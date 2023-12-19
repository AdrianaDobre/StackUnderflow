package com.stackunderflow.backend.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddVote {
    private Long commentId;
    private Long userId;
    private Boolean voteType;
}
