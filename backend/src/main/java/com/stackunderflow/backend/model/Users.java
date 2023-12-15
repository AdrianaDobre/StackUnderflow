package com.stackunderflow.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique=true)
    private String username;

    @NotNull
    @Column(unique=true)
    private String email;

    @Column(unique=true)
    private String phoneNumber;

    @NotNull
    private String password;

    @NotNull
    private Double points = (double) 0;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Post> posts;
}
