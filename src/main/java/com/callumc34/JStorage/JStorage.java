package com.callumc34.jstorage;

import java.util.Scanner;
import java.util.HashMap;

import java.io.FileNotFoundException;
import java.io.File;

import java.util.regex.*;

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
            String cmd = match.group(0);
            
            Pattern typePattern = Pattern.compile("(?<=(.)*)(OBJECT|STRING|INTEGER|FLOAT|DOUBLE|BOOL)(?=(.)*)");
            Matcher typeMatcher = typePattern.matcher(cmd);
            if (typeMatcher.find()) {         
               String type = typeMatcher.group(0);
               
               switch (type) {
                  case "OBJECT":
                     break;
                     
                  case "STRING":
                     break;
                     
                  case "INTEGER":
                     break;
                     
                  case "FLOAT":
                     break;
                     
                  case "DOUBLE":
                     break;
                    
                  case "BOOL":
                     break;
               }
            }
            System.out.printf("\n%s\n", currentLines);
            
            //Command handled clear String
            currentLines = "";
         }
         
      }
      return new JStorage(data, name);
   }
   
}