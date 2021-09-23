package com.callumc34.jstorage;

import java.util.ArrayList;
import java.util.Collections;

//Special class for handling with children
class JStorageData extends JStorageObject<ArrayList<JStorageObject>> {
   
   public JStorageData(JStorageObject[] children) {
      value = new ArrayList<JStorageObject>();
      Collections.addAll(value, children);
   }
   
   public JStorageData(ArrayList<JStorageObject> v) {
      value = v;
   }
   
   public JStorageData(String n) {
      name = n;
      value = new ArrayList<JStorageObject>();
   }
   
   public JStorageData() {
      value = new ArrayList<JStorageObject>();   
   }
   
   @Override
   public boolean hasChildren() {
      return value.size() > 0;
   }
   
   @Override
   public boolean addChild(JStorageObject obj) {
      return value.add(obj);
   }
   
   @Override
   public ArrayList<JStorageObject> getChildren() {
      return value;
   }
}