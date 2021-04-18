package com.jasonqjc.version2.framework.web;

import java.util.Map;

import com.google.common.collect.Maps;

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
  
  public View addModel(String key, Object value) {
    if(model != null) {
      model.put(key, value);
    } else {
      model = Maps.newHashMap();
      model.put(key, value);
    }
    return this;
  }
}
