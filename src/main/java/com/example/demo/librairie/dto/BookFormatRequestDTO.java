package com.example.demo.librairie.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFormatRequestDTO {
  private Integer bookId;
  private Integer formatId;
  private BigDecimal price;
}
