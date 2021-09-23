package com.callumc34.jstorage;

import java.util.ArrayList;
import java.util.Arrays;

public class JStorage {
   private JStorageData data;
   
   public JStorage() {
      data = new JStorageData("JStorage");
   }
   
   public JStorageData getData() {
      return data;
   }
   
   public void setData(JStorageData d) {
      data = d;
   }
   
   static public boolean addFromObjectPath(String[] objPath, JStorageObject obj, JStorageObject parent) {
      if (objPath.length == 0) { return parent.addChild(obj); }
      
      //Iterate through the object path finding the obj in the parent's objects
      
      JStorageObject child = findObject(objPath[0], parent);
      
      
      if (child == null) {
         //Create object
         child = new JStorageData(objPath[0]);
         if (!parent.addChild(child)) {
            //Object is not a JStorageData cannot add child
            return false;
         }
      }
      
      return addFromObjectPath(Arrays.copyOfRange(objPath, 1, objPath.length), obj, child);
   }
   
   public boolean addFromObjectPath(String[] objPath, JStorageObject obj) {
      return addFromObjectPath(objPath, obj, data);
   }
   
   public boolean addObject(JStorageObject obj) {
      return data.addChild(obj);
   }
   
   //TODO(Callum): Find functionality (add tree search && add efficiency)
   static public JStorageObject findObject(String name, JStorageObject obj) {
       //TODO(Callum): Make search more efficient
      if (!obj.hasChildren()) {
         return null;
      }
      
      ArrayList<JStorageObject> list = obj.getChildren();
      for (JStorageObject d : list) {
         if (d.name.equals(name)) {
            return d; 
         }
      }
      return null;
   }
   
   public JStorageObject findObject(String name) {
      return findObject(name, data);
   }
   
   static public boolean exists(String name, JStorageData obj) {
      return findObject(name, obj) != null;
   }
   
   public boolean exists(String name) {
      return exists(name, data);
   }
   
   public String toString() {
      return data.toString();
   }
   
   static public JStorage fromJStorFile(String path) {
      return JStor.parse(path);
   }
   
//    static public JStorage fromYAMLFile(String path) {}
//    static public JStorage fromXMLFile(String path) {}
//    static public JStorage fromJSONFile(String path) {}
   
   public String dumpToJStor() {
      return JStor.dump();
   }
   
//    public String dumpToYAML() {}
//    public String dumpToXML() {}
//    public String dumpToJSON() {}
   
   public boolean dumpToJStorFile(String path) {
      return JStor.dumpToFile(path);
   }
   
//    public boolean dumpToYAMLFile(String path) {}
//    public boolean dumpToXMLFile(String path) {}
//    public boolean dumpToJSONFile(String path) {}
   
   public boolean isInitialised() {
      return true;
   }  
}