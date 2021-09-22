package com.callumc34.jstorage;

public class JStorageObject<T> {   
   public String key;
   //TODO(Callum): Make java reference to parent
   public JStorageObject parent;
   public T value;
   
   public JStorageObject(String k, JStorageObject p, T v) {
      key = k;
      parent = p;
      value = v;
   }
   
   public JStorageObject() {
   
   }
   
   public String toString() {
      return String.format("KEY: %s\nPARENT: %s\nVALUE: %s",
         key, (parent.key != null) ? parent.key : "none", value.toString());
   }
}