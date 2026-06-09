package com.example.demo.librairie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFormatRequestDTO {
    private Integer bookId;
    private Integer formatId;
    private BigDecimal price;
}
