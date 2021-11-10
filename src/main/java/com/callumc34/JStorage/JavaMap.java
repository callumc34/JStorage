package com.callumc34.jstorage;

import java.util.ArrayList;
import java.util.Map;

class JavaMap {
   public static JStorage parse(Map<String, Object> map) {
      JStorage ret = new JStorage();
      addToJStorage(map, ret, new ArrayList<String>());
      return ret;
   }
      
   public static void addToJStorage(Map<String, Object> map, JStorage jstor, ArrayList<String> path) {
      for (Map.Entry<String, Object> entry : map.entrySet()) {
         if (entry.getValue() instanceof Map) {
            path.add(entry.getKey());
            addToJStorage((Map<String, Object>) entry.getValue(), jstor, path);
         } else {
            String[] objPath = new String[path.size()];
            path.toArray(objPath);
            jstor.addFromObjectPath(
               objPath,
               new JStorageObject(entry.getKey(), entry.getValue())
            );
         }
      }
   }
   
   public static Map<String, Object> fromJStorage(JStorageObject obj) {
      return null;
   }
}