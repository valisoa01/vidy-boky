package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiement")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_paiement", nullable = false)
    private TypePaiement typePaiement;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false, unique = true)
    private Commande commande;

}
