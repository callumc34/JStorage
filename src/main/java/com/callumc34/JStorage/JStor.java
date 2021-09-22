package com.callumc34.jstorage;

import java.util.Scanner;
import java.util.regex.*;

import java.io.File;
import java.io.FileNotFoundException;

public class JStor implements JStorageFile {
   //REGION: Variables
   private boolean init = false;
   private String path;
   private String name = "JStorage";
   private JStorageData data = new JStorageData();
   
   //REGION: Constructors
   public JStor(String p) throws FileNotFoundException {
      fromJStorFile(p);
   }
   
   public JStor() {
   
   }
   
   //REGION: Setters
   public void setData(JStorageData d) {
      data = d;
   }
   
   public void setPath(String p) {
      path = p;
   }
   
   //REGION: Getters
   public JStorageData getData() {
      return data;
   }
   
   public String getPath() {
      return path;
   }
   
   //REGION: Template Implementations   
   public void fromJStor(JStor storage) {
      data = storage.getData();
      path = storage.getPath();
      init = true;
   }
   
   public void fromJStorFile(String p) throws FileNotFoundException {
      path = p;
      File file = new File(p);
      Scanner scanner = new Scanner(file);
      
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
            
            String  stringValue = "";
            if (splitEquals.length > 1) {
               stringValue = splitEquals[1];
            }
            
            String[] splitCmd = splitEquals[0].split(" ");
            String type = splitCmd[0];
            String obj = splitCmd[1];
            String[] objPath = obj.split("\\.");
            
            //TODO(Callum): Add values to data member
            //Recurse down the objPath to create the final value Object
            //Add parents to it
            //Objects before hand are given the value of JStorageData if they have a child
            //SQUARE.NAME - SQUARE = new JStorageObject<JStorageData>("SQUARE", null, new JStorageData())
            // - NAME = new JStorageObject<String>("NAME", SQUARE, VALUE);
            //SQUARE.value.add(NAME)
            
            //Debug
            //System.out.printf("\nLine: %d\nCommand: %s\nType: %s\nObject: %s\nValue:%s\n",
            //   currentLine, cmd, type, obj, stringValue);
         }
         
         //Clear currentLines if all is dealt with
         currentLines = "";
      }
   
      init = true;
   }
   
   public void fromYAML() {
   
   }
   
   public void fromYAMLFile(String path) {
   
   }
   
   public void fromXML() {
   
   }
   
   public void fromXMLFile(String path) {
   
   }
   
   public void fromJSON() {
   
   }
   
   public void fromJSONFile(String path) {
   
   }
   
   public String dump() {
      return null;
   }
   
   public JStor toJStor() {
      return this;
   }
   
   public void toYAML() {
   
   }
   
   public void toXML() {
   
   }
   
   public void toJSON() {
   
   } 
   
   public boolean isInitialised() {
      return init;
   }
}