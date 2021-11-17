package com.callumc34.jstorage;

import java.util.Map;
import java.util.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.yaml.snakeyaml.Yaml;

class YAML {
   final private static Yaml yaml = new Yaml();
   
   public static JStorage parse(String path) {
      File file = new File(path);
      
      try (InputStream in = new FileInputStream(file)) {
         Map<String, Object> map = yaml.load(in);
         return JStorage.fromMap(map);
      } catch (IOException e) {
         return null;
      }
   }
   
   public static JStorage parseString(String yml) {
      Map<String, Object> map = yaml.load(yml);
      return JStorage.fromMap(map);
   }
      
   public static String dump(JStorageObject obj) {
      return yaml.dump(JavaMap.fromJStorage(obj));
   }
   
   public static boolean dumpToFile(String path, JStorageObject obj) throws FileNotFoundException, IOException {
      FileWriter fw = new FileWriter(new File(path));
      fw.write(dump(obj));
      fw.close();
      return true;  
   }
}