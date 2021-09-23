package com.callumc34.jstorage;

import java.util.ArrayList;

public class JStorageObject<T> {
   public String name;
   protected T value;
   
   public JStorageObject(String n, T v) {
      name = n;
      value = v;
   }
   
   public JStorageObject() {
   
   }
   
   public String toString() {
      return String.format("NAME: %s - VALUE: %s", name, value.toString());
   }
   
   public T get() {
      return value;
   }
   
   public void set(T v) {
      value = v;
   }
   
   public boolean hasChildren() {
      return false;
   }
   
   public boolean addChild(JStorageObject obj) {
      return false;
   }
   
   public ArrayList<JStorageObject> getChildren() {
      return null;
   }
}