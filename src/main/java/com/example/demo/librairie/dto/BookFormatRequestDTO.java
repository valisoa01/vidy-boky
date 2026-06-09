package com.example.demo.librairie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFormatRequestDTO {
    private UUID bookId;
    private Integer formatId;
    private BigDecimal price;
}
