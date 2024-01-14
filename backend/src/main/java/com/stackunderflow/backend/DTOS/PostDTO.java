package com.stackunderflow.backend.DTOS;

import com.stackunderflow.backend.model.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private String title;
    private String body;
    private List<String> tags;
    private Long bestAnswer;
    private List<VoteDTO> votesByLoggedUser;
}
