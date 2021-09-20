package com.callumc34.jstorage;

public class JStor implements JStorageFile {
   boolean init = false;
   String path;
   
   public JStor(String p) {
      parse(p);
      path = p;
   }
   
   public JStor() {
   
   }
   
   public void parse(String p) {
   
   }
   
   public boolean isInitialised() {
      return init;
   }
}