package com.example.demo.librairie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookFormatResponseDTO {
    private UUID bookId;
    private String bookTitle;
    private UUID formatId;
    private String typeFormat;
    private BigDecimal price;
}
