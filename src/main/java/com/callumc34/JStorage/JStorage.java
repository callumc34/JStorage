package com.callumc34.jstorage;

public class JStorage {
   //TODO(Callum): Create static API for interacting with package
   static public JStor parseJStor(String p) {
      try {
         return new JStor(p);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}