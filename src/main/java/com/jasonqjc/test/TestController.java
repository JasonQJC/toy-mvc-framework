package com.jasonqjc.test;

import java.util.Map;

import com.google.common.collect.Maps;
import com.jasonqjc.version1.service.CoffeeService;
import com.jasonqjc.version2.framework.annotation.Controller;
import com.jasonqjc.version2.framework.annotation.Inject;
import com.jasonqjc.version2.framework.annotation.RequestHandler;
import com.jasonqjc.version2.framework.web.RequestParam;
import com.jasonqjc.version2.framework.web.View;

@Controller
public class TestController {

  @Inject
  CoffeeService coffeeService;
  
  @RequestHandler("GET /test/test")
  public View test(RequestParam param) {
    Map<String,Object> map = Maps.newHashMapWithExpectedSize(1);
    map.put("coffeeList",coffeeService.getCoffeeList());
    return View.builder().path("coffee.jsp").model(map).build();
  }
  
}
