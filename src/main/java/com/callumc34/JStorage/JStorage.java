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
            pattern = Pattern.compile("(?<=BEGIN )(.)*(?=;)");
            match = pattern.matcher(currentLines);
            
            if (match.find()) {
               begin = true;
               name = match.group(1);
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
            String value = null;
            
            if (splitEquals.length > 1) {
               value = splitEquals[1];
            }
            
            String[] splitCmd = splitEquals[0].split(" ");
            String type = splitCmd[0];
            String obj = splitCmd[1];
            
            String[] objPath = obj.split("\\.");
            
            if (objPath.length == 1 && value == null) {
               data.put(objPath[0], new HashMap<String, HashMap>());
            }  else if (objPath.length == 1 && !type.equals("OBJECT")) {
               System.out.printf("Error at line: %d - Value defined to a non object variable");
               break;
            } else {
               putObject(data, objPath, value);
            }
            
            //Command handled clear String
         }
         
         currentLines = "";
         
      }
      return new JStorage(data, name);
   }
   
}