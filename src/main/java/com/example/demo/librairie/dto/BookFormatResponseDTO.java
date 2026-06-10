package com.example.demo.librairie.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
