package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "livre")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titre", length = 100, nullable = false)
    private String titre;

    @Column(name = "isbn", length = 100, unique = true)
    private String isbn;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @ManyToMany
    @JoinTable(
        name = "livre_genre",
        joinColumns = @JoinColumn(name = "livre_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
        name = "livre_auteur",
        joinColumns = @JoinColumn(name = "livre_id"),
        inverseJoinColumns = @JoinColumn(name = "auteur_id")
    )
    private List<Auteur> auteurs;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    private List<LivreFormat> formats;
}
