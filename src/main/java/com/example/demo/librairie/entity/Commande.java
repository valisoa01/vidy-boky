package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commande")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_commande", nullable = false)
    private TypeCommande typeCommande;

    @Column(name = "date_commande", nullable = false)
    private LocalDateTime dateCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    private Livraison livraison;

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    private Paiement paiement;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeLigne> lignes;

}