package com.jasonqjc.version1.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Coffee {
   private long id;
   private String name;
   private String createBy;
   private BigDecimal price;
   private String remark;
}