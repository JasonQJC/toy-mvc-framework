package com.jasonqjc.version2.framework.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.net.MediaType;
import com.jasonqjc.version2.framework.config.ConfigHolder;
import com.jasonqjc.version2.framework.core.BeanClassInstanceHolder;
import com.jasonqjc.version2.framework.core.ControllerHandlerHolder;
import com.jasonqjc.version2.framework.core.FrameworkLoader;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    FrameworkLoader.init();
    ServletContext servletContext = config.getServletContext();
    ServletRegistration jspServletRegistration = servletContext.getServletRegistration("jsp");
    jspServletRegistration.addMapping(ConfigHolder.getJspPath() + "*");
    ServletRegistration defaultServletRegistration = servletContext.getServletRegistration("default");
    defaultServletRegistration.addMapping(ConfigHolder.getAssetPath() + "*");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String httpMethod = req.getMethod().toLowerCase();
    String requestPath = req.getPathInfo();
    HandlerClassInfo handlerClassInfo = ControllerHandlerHolder.getHandlerClassInfo(httpMethod, requestPath);
    if (handlerClassInfo != null) {
      Map<String, String[]> parameterMap = req.getParameterMap();
      parameterMap = Maps.newHashMap(parameterMap);
      String body = URLDecoder.decode(IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8),
          StandardCharsets.UTF_8);
      if (StringUtils.isNotBlank(body)) {
        String[] params = StringUtils.split(body, "&");
        if (ArrayUtils.isNotEmpty(params)) {
          for (String param : params) {
            String[] kv = StringUtils.split(param, "=");
            if (kv != null && kv.length == 2) {
              if (parameterMap.containsKey(kv[0])) {
                ArrayUtils.add(parameterMap.get(kv[0]), kv[1]);
              } else {
                parameterMap.put(kv[0], new String[] { kv[1] });
              }
            }
          }
        }
      }
      RequestParam requestParam = new RequestParam(parameterMap);
      Class<?> controllerClass = handlerClassInfo.getControllerClass();
      Object controllerBean = BeanClassInstanceHolder.getBean(controllerClass);
      Method actionMethod = handlerClassInfo.getActionMethod();
      Object result = null;
      try {
        result = actionMethod.invoke(controllerBean, requestParam);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
      if (result instanceof View) {
        View view = (View) result;
        String path = view.getPath();
        if (path.startsWith("/")) {
          resp.sendRedirect(req.getContextPath() + path);
        } else {
          Map<String, Object> model = view.getModel();
          for (Entry<String, Object> entry : model.entrySet()) {
            req.setAttribute(entry.getKey(), entry.getValue());
          }
          req.getRequestDispatcher(ConfigHolder.getJspPath() + path).forward(req, resp);
        }
      } else if(result instanceof ResponseData) {
        ResponseData data = (ResponseData) result;
        Object model = data.getModel();
        if(model != null) {
          resp.setContentType(MediaType.JSON_UTF_8.type());
          resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
          try(PrintWriter writer = resp.getWriter();) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(model));
            writer.flush();
          }
        }
      }
    }
  }

}
