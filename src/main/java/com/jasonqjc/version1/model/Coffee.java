package com.jasonqjc.version1.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
  private long id;
  private String name;
  private String createBy;
  private BigDecimal price;
  private String remark;
}