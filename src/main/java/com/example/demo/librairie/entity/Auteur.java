package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "auteur")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nom_auteur", length = 100, nullable = false)
    private String nomAuteur;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "prenom", length = 100)
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @ManyToMany(mappedBy = "auteurs")
    private List<Livre> livres;
}
