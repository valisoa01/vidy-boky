package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "livre_format")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LivreFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "format_id", nullable = false)
    private Format format;

    @OneToMany(mappedBy = "livreFormat", cascade = CascadeType.ALL)
    private List<Stock> stocks;
}
