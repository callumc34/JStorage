package com.callumc34.jstorage;

import java.util.ArrayList;

class JStorageData {
   
   private ArrayList<JStorageObject> data;
   
   public JStorageData() {
      data = new ArrayList<JStorageObject>();
   }
   
   public int index(String key) {
      //TODO(Callum): Make search more efficient
      int index = 0;
      for (JStorageObject d : data) {
         if (d.key.equals(key)) {
            return index; 
         }
         index++;
      }
      return -1;
   }
   
   public String toString() {
      String ret = "";
      for (JStorageObject obj : data) {
         ret += String.format("\n%s\n", obj.toString());
      }
      return ret;
   }
   
   public JStorageObject get(String key) {
      return data.get(index(key));
   }
   
   public boolean exists(String key) {
      return (index(key) != -1);
   }
   
   public boolean add(JStorageObject obj) {
      return data.add(obj);
   }
}