package com.callumc34.jstorage;

public class NoSuchObjectException extends Exception {
   public NoSuchObjectException(String errorText) {
      super(errorText);
   }  
}