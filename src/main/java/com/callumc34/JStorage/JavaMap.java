package com.callumc34.jstorage;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONObject;

class JavaMap {
   public static JStorage parse(Map<String, Object> map) {
      HashMap<String, Object> JStorageMap = new HashMap<String, Object>();
      toJStorageMap(null, map, JStorageMap);
      
      JStorage ret = new JStorage();
      
      for (Map.Entry<String, Object> entry : JStorageMap.entrySet()) {
         String[] objPath = entry.getKey().split("\\.");
         ret.addFromObjectPath(
            Arrays.copyOf(objPath, objPath.length - 1),
            new JStorageObject(objPath[objPath.length - 1], entry.getValue())
         );
      }
         
      return ret;
   }
   
   static public void toJStorageMap(String currentKey, Map<String, Object> map, Map<String, Object> out) {
      for (Map.Entry<String, Object> entry : map.entrySet()) {
         String nextKey = (currentKey == null) ? entry.getKey() : currentKey + "." + entry.getKey();
         if (entry.getValue() instanceof Map) {
            toJStorageMap(nextKey, (Map<String, Object>) entry.getValue(), out);
         } else {
            out.put(nextKey, entry.getValue());
         }
      }
   }
   
   public static Map<String, Object> fromJStorage(JStorageObject obj) {
      return JSON.fromJStorage(obj).toMap();
   }
}