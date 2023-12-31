package com.stackunderflow.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    @NotNull
    private String text;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Boolean isTheBest = Boolean.FALSE;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Suggestion> suggestions;
}
