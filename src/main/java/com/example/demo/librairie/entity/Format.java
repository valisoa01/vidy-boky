package com.example.demo.librairie.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "format")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "type_format", length = 100, nullable = false)
    private String typeFormat;

    @OneToMany(mappedBy = "format", cascade = CascadeType.ALL)
    private List<LivreFormat> livreFormats;
}
