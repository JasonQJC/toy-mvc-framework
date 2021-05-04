package com.jasonqjc.test;

import java.util.List;
import java.util.Map;

import com.jasonqjc.core.annotation.Service;
import com.jasonqjc.core.annotation.Transaction;
import com.jasonqjc.core.util.DBUtil;
import com.jasonqjc.test.model.Coffee;

@Service
public class CoffeeService {

  public List<Coffee> getCoffeeList() {
    String sql = "select * from coffee";
    return DBUtil.getEntityList(Coffee.class, sql);
  }
  
  public Coffee getCoffee(long id) {
    String sql = "select * from coffee where id = ?";
    return DBUtil.getEntity(Coffee.class, sql, id);
  }

  @Transaction
  public boolean createCoffee(Map<String, Object> fieldMap) {
    return DBUtil.insertEntity(Coffee.class, fieldMap);
  }

  @Transaction
  public boolean updateCoffee(long id, Map<String, Object> fieldMap) {
    return DBUtil.updateEntity(Coffee.class, id, fieldMap);
  }

  @Transaction
  public boolean deleteCoffee(long id) {
    return DBUtil.deleteEntity(Coffee.class, id);
  }

}