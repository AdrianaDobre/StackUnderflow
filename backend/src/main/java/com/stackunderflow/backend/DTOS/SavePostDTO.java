package com.stackunderflow.backend.DTOS;

import com.stackunderflow.backend.model.Topic;
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
public class SavePostDTO {
    private String title;
    private String body;
    private List<Topic> tags;
}
