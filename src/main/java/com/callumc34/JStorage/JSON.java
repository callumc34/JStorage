package com.callumc34.jstorage;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

class JSON {
   public static JStorage parse(String path) throws FileNotFoundException {
      File f = new File(path);
      Scanner sc = new Scanner(f);
      String src = "";
      
      while (sc.hasNextLine()) {
         src += sc.nextLine();
      }
      
      JSONObject json = new JSONObject(src);
      
      return parse(json);
   }
   
   public static JStorage parse(JSONObject obj) {
      return JStorage.fromMap(obj.toMap());
   }
   
   public static void appendToJSON(JStorageObject obj, JSONObject json) {
      if (obj.hasChildren()) {
         JSONObject newObj = new JSONObject();
         json.put(obj.name, newObj);
         ArrayList<JStorageObject> children = obj.getChildren();
         for (JStorageObject child : children) {
            appendToJSON(child, newObj);
         }
      } else {
         json.put(obj.name, obj.get());
      }
   }
   
   public static JSONObject fromJStorage(JStorageObject obj) {
      JSONObject ret = new JSONObject();
      
      appendToJSON(obj, ret);
      
      return ret;
   } 
   
   public static String dump(JSONObject obj) {
      return obj.toString();
   }
   
   public static String dump(JStorageObject obj) {
      return fromJStorage(obj).toString();
   }
   
   public static boolean dumpToFile(String path, JSONObject obj) throws FileNotFoundException, IOException {
      FileWriter fw = new FileWriter(new File(path));
      fw.append(obj.toString(4));
      fw.close();
      return true;
   }
   
   public static boolean dumpToFile(String path, JStorageObject obj) throws FileNotFoundException, IOException {
      return dumpToFile(path, fromJStorage(obj));
   }
}