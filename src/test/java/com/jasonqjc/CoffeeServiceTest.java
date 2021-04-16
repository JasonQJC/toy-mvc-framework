package com.jasonqjc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.jasonqjc.version1.model.Coffee;
import com.jasonqjc.version1.service.CoffeeService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(value = Lifecycle.PER_CLASS)
public class CoffeeServiceTest {
  private final CoffeeService coffeeService;

  public CoffeeServiceTest() {
    coffeeService = new CoffeeService();
  }

  @BeforeAll
  public void init() {
    // TODO init db
  }

  @Test
  public void getCoffeeListTest() {
    List<Coffee> coffeeList = coffeeService.getCoffeeList();
    Assertions.assertEquals(2, coffeeList.size());
  }

  @Test
  public void getCoffeeTest() {
    long id = 1;
    Coffee coffee = coffeeService.getCoffee(id);
    Assertions.assertNotNull(coffee);
  }

  @Test
  public void createCoffeeTest() {
    var fieldMap = new HashMap<String, Object>();
    fieldMap.put("name", "latte");
    fieldMap.put("createBy", "unknown");
    fieldMap.put("price", BigDecimal.valueOf(9.99));
    fieldMap.put("remark", "nothing");
    boolean createCoffee = coffeeService.createCoffee(fieldMap);
    Assertions.assertTrue(createCoffee);
  }

  @Test
  public void updateCoffeeTest() {
    long id = 1;
    var fieldMap = new HashMap<String, Object>();
    fieldMap.put("price", BigDecimal.valueOf(19.99));
    boolean updateCoffee = coffeeService.updateCoffee(id, fieldMap);
    Assertions.assertTrue(updateCoffee);
  }

  @Test
  public void deleteCoffeeTest() {
    long id = 1;
    boolean deleteCoffee = coffeeService.deleteCoffee(id);
    Assertions.assertTrue(deleteCoffee);
  }

}
