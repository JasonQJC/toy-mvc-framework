package com.jasonqjc.version2.framework.web;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View {
  private String path;
  private Map<String, Object> model;
}
