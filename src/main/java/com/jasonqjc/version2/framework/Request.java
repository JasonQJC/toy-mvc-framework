package com.jasonqjc.version2.framework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

  private String httpMethod;
  private String requestPath;
  
}
