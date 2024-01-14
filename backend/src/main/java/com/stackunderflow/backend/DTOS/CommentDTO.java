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
public class CommentDTO {
    private Long answerId;
    private String body;
    private Long postId;
    private Long userId;
    private String username;
    private Long upVoteCount;
    private Long downVoteCount;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private Long revision;
}
