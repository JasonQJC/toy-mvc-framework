package com.jasonqjc.test;

import java.util.Map;

import com.jasonqjc.version2.framework.annotation.Controller;
import com.jasonqjc.version2.framework.annotation.Inject;
import com.jasonqjc.version2.framework.annotation.RequestHandler;
import com.jasonqjc.version2.framework.web.RequestParam;
import com.jasonqjc.version2.framework.web.ResponseData;
import com.jasonqjc.version2.framework.web.View;

@Controller
public class TestController {

  @Inject
  CoffeeService coffeeService;

  @RequestHandler("GET /v2/coffee")
  public View test(RequestParam param) {
    return View.builder().path("coffee.jsp").build()
        .addModel("coffeeList", coffeeService.getCoffeeList());
  }

  @RequestHandler("GET /v2/coffee_show")
  public View show(RequestParam param) {
    long id = param.getLong("id");
    return View.builder().path("coffee_show.jsp").build()
        .addModel("coffee", coffeeService.getCoffee(id));
  }

  @RequestHandler("GET /v2/coffee_edit")
  public View edit(RequestParam param) {
    long id = param.getLong("id");
    return View.builder().path("coffee_edit.jsp").build().addModel("coffee", coffeeService.getCoffee(id));
  }
  
  @RequestHandler("GET /v2/coffee_create")
  public View create(RequestParam param) {
    return View.builder().path("coffee_create.jsp").build();
  }

  @RequestHandler("POST /v2/coffee_create")
  public ResponseData createSubmit(RequestParam param) {
    Map<String, Object> paramMap = param.getParamMap();
    return new ResponseData(coffeeService.createCoffee(paramMap));
  }

  @RequestHandler("PUT /v2/coffee_edit")
  public ResponseData editSubmit(RequestParam param) {
    long id = param.getLong("id");
    Map<String, Object> paramMap = param.getParamMap();
    return new ResponseData(coffeeService.updateCoffee(id, paramMap));
  }

  @RequestHandler("DELETE /v2/coffee_edit")
  public ResponseData delete(RequestParam param) {
    long id = param.getLong("id");
    return new ResponseData(coffeeService.deleteCoffee(id));
  }

}
