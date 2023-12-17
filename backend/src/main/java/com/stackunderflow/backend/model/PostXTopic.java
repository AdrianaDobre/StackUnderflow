package com.stackunderflow.backend.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "post_x_topic")
public class PostXTopic {
    @EmbeddedId
    private PostXTopicId id = new PostXTopicId();
}
