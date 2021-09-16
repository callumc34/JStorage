package com.callumc34.jstorage;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
import java.util.regex.*;

import java.io.FileNotFoundException;
import java.io.File;

public class JStorage {
   private Scanner fileScan;
   private HashMap<String, HashMap> FileData;
   private String name = "JStorage";
   
   public JStorage(HashMap<String, HashMap> data, String n) {
      FileData = data;
      name = n;
   }
   
   public JStorage(HashMap<String, HashMap> data) {
      FileData = data;
   }
   
   public JStorage() {
   }
   
   public String toString() {
      return String.format("%s - %s", name, FileData.toString());
   }
   
   static public Object castStringToType(String type, String value) {
      switch (type) {
         case "OBJECT":
            return new HashMap<String, HashMap>();
            
         case "STRING":
            return value;
           
        case "BOOLEAN":
            return value.isEmpty();
            
        case "INTEGER":
            return Integer.parseInt(value);
            
        case "FLOAT":
            return Float.parseFloat(value);
            
        case "DOUBLE":
            return Double.parseDouble(value);
      }
      return value;
   }
   
   static public HashMap<String, Object> objectStringToHashMap(String objString) {
      if (objString.isEmpty()) {
         return new HashMap<String, Object>();
      }
   }
      
   static private void putObject(HashMap<String, HashMap> ref, String[] objPath, Object value) {
		//Cannot have objPath of less than 2
      if (objPath.length == 1) {
			return;
		}
		
      //Add the object value
		if (objPath.length == 2) {
			if (ref.containsKey(objPath[0])) {
				ref.get(objPath[0]).put(objPath[1], value);
				return;
			} 
			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put(objPath[1], value);
			ref.put(objPath[0], temp);
			return;
		}
		
      //Append
		if (ref.containsKey(objPath[0])) {
			putObject(ref.get(objPath[0]), Arrays.copyOfRange(objPath, 1, objPath.length), value);
		} else {
			HashMap<String, HashMap> temp = new HashMap<String, HashMap>();
			ref.put(objPath[0], temp);
			putObject(temp, Arrays.copyOfRange(objPath, 1, objPath.length), value);
		}      
   }
   
   static public JStorage parse(String path) throws FileNotFoundException {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      
      int currentLine = 0;
      boolean begin = false;
      boolean end = false;
      String currentLines = "";
      String name = "JStorage";
      HashMap<String, HashMap> data = new HashMap<String, HashMap>();
      
      Pattern pattern;
      Matcher match;
      
      while (sc.hasNextLine() || end) {
         currentLines += sc.nextLine().trim();
         currentLine++;
         
         //Deal with comments
         pattern = Pattern.compile("#");
         match = pattern.matcher(currentLines);
         if (match.find()) {
            currentLines = currentLines.substring(0, match.start());
         }         
         
         //Read until end of command found
         pattern = Pattern.compile(";");
         match = pattern.matcher(currentLines);
         
         if (!match.find()) {
            continue;
         }
         
         //Find beginning of JStorage file
         if (!begin) {
            pattern = Pattern.compile("(?<=BEGIN )[aA-zZ]+?(?=;)");
            match = pattern.matcher(currentLines);
            
            if (match.find()) {
               begin = true;
               name = match.group(0);
               currentLines = currentLines.substring(match.end() + 1, currentLines.length());
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
            Object value = null;
            
            String[] splitCmd = splitEquals[0].split(" ");
            String type = splitCmd[0];
            String obj = splitCmd[1];
            
            if (splitEquals.length > 1) {
               value = castStringToType(type, splitEquals[1]);
            }
            
            String[] objPath = obj.split("\\.");
            
            //Create new hashmap for object defined
            if (objPath.length == 1 && !type.equals("OBJECT")) {
               //Cannot set value of a non class member variable
               System.out.printf("Error at line: %d - Value defined to a non object variable");
               break;
            } else {
               //Add object to data hashmap
               putObject(data, objPath, value);
            }
            
            //Command handled clear String
         }
         
         currentLines = "";
         
      }
      return new JStorage(data, name);
   }
   
}