package com.jasonqjc.version2.framework.web;

import java.util.Map;

import lombok.Data;

@Data
public class View {
  private String path;
  private Map<String, Object> model;
}
