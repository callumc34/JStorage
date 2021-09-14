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
   
   static JStorage parse(String path) throws FileNotFoundException {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      
      boolean begin = false;
      boolean end = false;
      String currentLines = "";
      String name = "JStorage";
      HashMap<String, HashMap> data = new HashMap<String, HashMap>();
      
      Pattern pattern;
      Matcher match;
      
      while (sc.hasNextLine() || end) {
         currentLines += sc.nextLine();
         
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
         
         System.out.printf("%s\n", currentLines);
         
      }
      return new JStorage(data, name);
   }
   
}