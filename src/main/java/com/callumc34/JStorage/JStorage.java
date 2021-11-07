package com.callumc34.jstorage;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileNotFoundException;

public class JStorage {
   private JStorageData data;
   
   public JStorage(JStorageData d) {
      d = data;
   }
   
   public JStorage() {
      data = new JStorageData("JStorage");
   }
   
   public JStorageData getData() {
      return data;
   }
   
   public void setData(JStorageData d) {
      data = d;
   }  
   
   public String toString() {
      return data.toString();
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
      
      ArrayList<JStorageObject> children = obj.getChildren();
      for (JStorageObject child : children) {
         if (child.name.equals(name)) {
            return child; 
         }
      }
      return null;
   }
   
   public JStorageObject findObject(String name) {
      return findObject(name, data);
   }
   
   static private void allChildren(JStorageObject obj, ArrayList<JStorageObject> refList) {
      if (obj.hasChildren()) {
         ArrayList<JStorageObject> temp = obj.getChildren();
         for (JStorageObject children : temp) {
            allChildren(children, refList);
         }
      }
      refList.add(obj);
   }
   
   static public ArrayList<JStorageObject> allChildren(JStorageObject obj) {
      ArrayList<JStorageObject> ret = new ArrayList<JStorageObject>();
      allChildren(obj, ret);
      return ret;
   }
   
   public ArrayList<JStorageObject> allChildren() {
      ArrayList<JStorageObject> ret = new ArrayList<JStorageObject>();
      allChildren(data, ret);
      return ret;
   }
   
   static public boolean exists(String name, JStorageData obj) {
      return findObject(name, obj) != null;
   }
   
   public boolean exists(String name) {
      return exists(name, data);
   }
   
   static public JStorage fromJStorFile(String path) throws FileNotFoundException {
      return JStor.parse(path);
   }
   
//    static public JStorage fromYAMLFile(String path) {}
//    static public JStorage fromXMLFile(String path) {}
//    static public JStorage fromJSONFile(String path) {}
   
   public String dumpToJStor() {
      return JStor.dump(data);
   }
   
//    public String dumpToYAML() {}
//    public String dumpToXML() {}
//    public String dumpToJSON() {}
   
   public boolean dumpToJStorFile(String path) {
      return JStor.dumpToFile(path, data);
   }
   
//    public boolean dumpToYAMLFile(String path) {}
//    public boolean dumpToXMLFile(String path) {}
//    public boolean dumpToJSONFile(String path) {}
   
   public boolean isInitialised() {
      return true;
   }  
}