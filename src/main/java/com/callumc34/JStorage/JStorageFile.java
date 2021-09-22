package com.callumc34.jstorage;

import java.io.FileNotFoundException;

//TODO(Callum): Implement external file formats
interface JStorageFile {
   public void fromJStor(JStor storage);
   public void fromJStorFile(String path) throws FileNotFoundException;
   public void fromYAML();
   public void fromYAMLFile(String path);
   public void fromXML();
   public void fromXMLFile(String path);
   public void fromJSON();
   public void fromJSONFile(String path);
   
   public String toString();
   public JStor toJStor();
   public void toYAML();
   public void toXML();
   public void toJSON(); 
   
   public boolean isInitialised();
}