package com.jasonqjc.version2.framework.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassUtil {

  public static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  public static Class<?> loadClass(String className, boolean isInitialized) {
    try {
      return Class.forName(className, isInitialized, getClassLoader());
    } catch (ClassNotFoundException e) {
      log.error("load class fail", e);
    }
    return null;
  }

  public static Set<Class<?>> getClassSet(String packageName) {
    Enumeration<URL> urls = null;
    try {
      urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Set<Class<?>> classSet = null;
    while (urls != null && urls.hasMoreElements()) {
      classSet = Sets.newHashSet();
      URL url = urls.nextElement();
      if (url != null) {
        String protocol = url.getProtocol();
        if (protocol.equals("file")) {
          String path = url.getPath();
          addClass(classSet, path, packageName);
        } else if (protocol.equals("jar")) {
          JarURLConnection jarURLConnection = null;
          try {
            jarURLConnection = (JarURLConnection) url.openConnection();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
          if (jarURLConnection != null) {
            JarFile jarFile = null;
            try {
              jarFile = jarURLConnection.getJarFile();
            } catch (IOException e) {
              e.printStackTrace();
            }
            if (jarFile != null) {
              Enumeration<JarEntry> jarEntries = jarFile.entries();
              while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                String jarEntryName = jarEntry.getName();
                if (jarEntryName.endsWith(".class")) {
                  String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                  doAddClass(classSet, className);
                }
              }
            }
          }
        }
      }
    }
    return classSet;
  }

  private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
    File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
    for (File file : files) {
      String fileName = file.getName();
      if (file.isFile()) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (StringUtils.isNotEmpty(packageName)) {
          className = packageName + "." + className;
        }
        doAddClass(classSet, className);
      } else {
        String subPackagePath = fileName;
        if (StringUtils.isNotEmpty(packagePath)) {
          subPackagePath = packagePath + "/" + subPackagePath;
        }
        String subPackageName = fileName;
        if (StringUtils.isNotEmpty(packageName)) {
          subPackageName = packageName + "." + subPackageName;
        }
        addClass(classSet, subPackagePath, subPackageName);
      }
    }
  }

  private static void doAddClass(Set<Class<?>> classSet, String className) {
    Class<?> cls = loadClass(className, false);
    classSet.add(cls);
  }

}
