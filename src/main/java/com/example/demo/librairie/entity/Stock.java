package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "mouvement", nullable = false)
    private TypeMouvement mouvement;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @Column(name = "date_mouvement", nullable = false)
    private LocalDateTime dateMouvement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_format_id", nullable = false)
    private LivreFormat livreFormat;

}
