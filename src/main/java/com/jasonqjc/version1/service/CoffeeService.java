package com.jasonqjc.version1.service;

import java.util.List;
import java.util.Map;

import com.jasonqjc.version1.model.Coffee;
import com.jasonqjc.version1.util.DBUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoffeeService {

  public List<Coffee> getCoffeeList() {
    String sql = "select * from coffee";
    return DBUtil.getEntityList(Coffee.class, sql);
  }
  
  public Coffee getCoffee(long id) {
    String sql = "select * from coffee where id = ?";
    return DBUtil.getEntity(Coffee.class, sql, id);
  }

  public boolean createCoffee(Map<String, Object> fieldMap) {
    return DBUtil.insertEntity(Coffee.class, fieldMap);
  }

  public boolean updateCoffee(long id, Map<String, Object> fieldMap) {
    return DBUtil.updateEntity(Coffee.class, id, fieldMap);
  }

  public boolean deleteCoffee(long id) {
    return DBUtil.deleteEntity(Coffee.class, id);
  }

}