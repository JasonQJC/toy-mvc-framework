package com.jasonqjc.version2.framework.web;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestParam {

  private Map<String, Object> paramMap;

  public long getLong(String key) {
    return Long.parseLong((String) paramMap.get(key));
  }

}