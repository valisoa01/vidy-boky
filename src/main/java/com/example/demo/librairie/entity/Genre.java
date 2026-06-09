package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "description", length = 100)
    private String description;

    @ManyToMany(mappedBy = "genres")
    private List<Livre> livres;
}
