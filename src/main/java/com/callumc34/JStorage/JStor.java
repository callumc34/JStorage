package com.callumc34.jstorage;

import java.lang.StringBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

class JStor {
   public static JStorage parse(String path) throws FileNotFoundException {
      JStorage ret = new JStorage();
      
      Scanner scanner = new Scanner(new File(path));
      
      int currentLine = 0;      
      boolean begin = false;
      boolean end = false;
      String currentLines = "";
      
      Pattern pattern;
      Matcher match;
   
      while (scanner.hasNextLine() || end) {
         currentLines += scanner.nextLine().trim();
         currentLine++;
         
         //Handle comments
         pattern = Pattern.compile("#");
         match = pattern.matcher(currentLines);
         if (match.find()) {
            currentLines = currentLines.substring(0, match.start());
         }
         
         //Read until end of next command found
         pattern = Pattern.compile(";");
         match = pattern.matcher(currentLines);
         
         if (!match.find()) {
            continue;
         }
         
         //Find beginning of JStorage file
         if (!begin) {
            pattern = Pattern.compile("BEGIN;");
            match = pattern.matcher(currentLines);
            
            if (match.find()) {
               begin = true;
               currentLines = "";
            }
            continue;
         }
         
         //Check if end of JStorage file
         pattern = Pattern.compile("END(?=;)");
         match = pattern.matcher(currentLines);
         if (match.find()) {
            end = true;
            break;
         }
         
         
         //Handle definition commands
         pattern = Pattern.compile("(?<=DEFINE )(.)*?(?=;)");
         match = pattern.matcher(currentLines);
         while (match.find()) {
            //Find variable type
            String cmd = match.group(0).strip();
            String[] splitEquals = cmd.split(" = ");
            
            String stringValue = "";
            if (splitEquals.length > 1) {
               stringValue = splitEquals[1];
            }
            
            String[] splitCmd = splitEquals[0].split(" ");
            String type = splitCmd[0];
            String obj = splitCmd[1];
            String[] fullPath = obj.split("\\.");
            String[] objPath;
            if (fullPath.length == 1) {
               objPath = Arrays.copyOfRange(fullPath, 0, 0);
            } else {
               objPath = Arrays.copyOfRange(fullPath, 0 , fullPath.length - 1);
            }
            
            String objName = fullPath[fullPath.length - 1];
            
            JStorageObject jstorObj;
            
            //Remove surrounding quotation marks
            stringValue = stringValue.replace("\"", "");
            
            //Debug
            //System.out.printf("\nLine: %d\nCommand: %s\nType: %s\nObject: %s\nValue:%s\n",
            //   currentLine, cmd, type, obj, stringValue);
            
            switch (type) {
               case "OBJECT":
                  jstorObj = new JStorageData(objName);
                  break;
                  
               case "STRING":
                  jstorObj = new JStorageObject<String>(objName, stringValue);
                  break;
                  
               case "INTEGER":
                  jstorObj = new JStorageObject<Integer>(
                     objName, Integer.parseInt(stringValue));
                  break;
                  
               case "DOUBLE":
                  jstorObj = new JStorageObject<Double>(
                     objName, Double.parseDouble(stringValue));
                  break;
                  
               case "FLOAT":
                  jstorObj = new JStorageObject<Float>(
                     objName, Float.parseFloat(stringValue)); 
                  break;
                  
               default:
                  //TODO(Callum): Throw error
                  jstorObj = new JStorageObject<String>(objName, stringValue);
                  break;                 
            }
            
            //Debug
            //System.out.printf("\nObject Path: %s\nObject: %s\n", String.join(", ", objPath), jstorObj.toString());
            //System.out.printf("\nData: %s\n", ret.toString());
            
            if (!ret.addFromObjectPath(objPath, jstorObj)) {
               //TODO(Callum): Handle errors in parsing
               System.out.printf("Error at line: %d - Could not add object: %s", currentLine, obj);
               return null;
            }
         }
         
         //Clear currentLines if all is dealt with
         currentLines = "";
      }
      return ret;
   }
   
   //TODO(Callum): Combine these two functions
   public static String dumpObject(JStorageObject obj, String parentPath) {   
      if (!obj.hasChildren()) {
         String type = javaClassToJStor(obj.get().getClass().getName());
         String name = obj.name;
         String val = obj.get().toString();
         if (type.equals("STRING")) {
            val = String.format("\"%s\"", val);
         }
         return String.format("DEFINE %s %s.%s = %s;", type, parentPath, name, val);
      }      
      
      StringBuilder ret = new StringBuilder();
      ArrayList<JStorageObject> children = obj.getChildren();
      for (JStorageObject child : children) {
         ret.append(dumpObject(child, String.format("%s.%s", parentPath, obj.name)));
      }
      return ret.toString();
   }
   
   public static String dumpObject(JStorageObject obj) {
      if (!obj.hasChildren()) {
         String type = javaClassToJStor(obj.get().getClass().getName());
         String name = obj.name;
         String val = obj.get().toString();
         if (type.equals("STRING")) {
            val = String.format("\"%s\"", val);
         }
         return String.format("DEFINE %s %s = %s;", type, name, val);
      }
      
      StringBuilder ret = new StringBuilder();
      ArrayList<JStorageObject> children = obj.getChildren();
      for (JStorageObject child : children) {
         ret.append(dumpObject(child, obj.name));
      }
      return ret.toString();
   }
      
   public static String dump(JStorageObject data) {
      StringBuilder ret = new StringBuilder("BEGIN;");
      ArrayList<JStorageObject> children = data.getChildren();
      
      for (JStorageObject child : children) {
         ret.append(dumpObject(child));
      }
      
      ret.append("END;");
      return ret.toString();
   }
   
   public static boolean dumpToFile(String path, JStorageData data) throws FileNotFoundException, IOException {
      FileWriter fw = new FileWriter(new File(path));
      String dumps = dump(data);
      String[] lines = dumps.split(";");
      
      for (String line : lines) {
         fw.append(String.format("%s;\n", line));
      }
      fw.close();
      return true;
   }
   
   private static String javaClassToJStor(String cls) {
      String[] clsPath = cls.split("\\.");
      return clsPath[clsPath.length-1].toUpperCase();
   }
}