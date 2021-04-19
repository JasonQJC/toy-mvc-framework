package com.jasonqjc.version1.controller;

import java.io.IOException;
import java.util.List;

import com.jasonqjc.test.CoffeeService;
import com.jasonqjc.version1.model.Coffee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/coffee_show")
public class CoffeeShowServlet extends HttpServlet {

  private CoffeeService service;
  
  @Override
  public void init() throws ServletException {
    service = new CoffeeService();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<Coffee> coffeeList = service.getCoffeeList();
    req.setAttribute("coffeeList", coffeeList);
    req.getRequestDispatcher("/view/coffee.jsp").forward(req, resp);
  }

}
