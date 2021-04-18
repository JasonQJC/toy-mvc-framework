package com.jasonqjc.test;

import java.util.Map;

import com.google.common.collect.Maps;
import com.jasonqjc.version1.model.Coffee;
import com.jasonqjc.version1.util.DBUtil;
import com.jasonqjc.version2.framework.annotation.Controller;
import com.jasonqjc.version2.framework.annotation.RequestHandler;
import com.jasonqjc.version2.framework.web.RequestParam;
import com.jasonqjc.version2.framework.web.View;

@Controller
public class TestController {

  @RequestHandler("GET /test/test")
  public View test(RequestParam param) {
    System.out.println(param);
    String sql = "select * from coffee";
    View view = new View();
    view.setPath("coffee.jsp");
    Map<String,Object> map = Maps.newHashMapWithExpectedSize(1);
    map.put("coffeeList",DBUtil.getEntityList(Coffee.class, sql));
    view.setModel(map);
    return view;
  }
  
}
