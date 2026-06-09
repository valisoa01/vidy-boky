package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "livraison")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "adresse", length = 255, nullable = false)
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutLivraison statut;

    @Column(name = "date_prevue")
    private LocalDate datePrevue;

    @Column(name = "date_effective")
    private LocalDate dateEffective;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false, unique = true)
    private Commande commande;
}