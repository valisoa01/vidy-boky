package com.example.demo.librairie.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFormatResponseDTO {
  private Integer id;
  private Integer bookId;
  private String bookTitle;
  private Integer formatId;
  private String typeFormat;
  private BigDecimal price;
}
