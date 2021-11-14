package com.callumc34.jstorage;

import com.callumc34.jstorage.JStorage;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReadTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void fromJStorFileToString() throws Throwable {
      JStorage testFile = JStorage.fromJStorFile("src/test/resources/TestJStor.jstor");
      Assert.assertEquals(
         "Should pass generic parse test.",
         testFile.toString().equals(
            "NAME: JStorage - VALUE: [NAME: Weight - VALUE: 80, NAME: Height - VALUE: 194, NAME: Name - VALUE: Callum, NAME: Person - VALUE: [NAME: Weight - VALUE: 74, NAME: Height - VALUE: 164, NAME: Name - VALUE: John], NAME: PersonTwo - VALUE: [NAME: Weight - VALUE: [NAME: Value - VALUE: 55, NAME: Unit - VALUE: kg], NAME: Height - VALUE: [NAME: Value - VALUE: 158, NAME: Unit - VALUE: cm], NAME: Name - VALUE: Marcos]]"
         ),
         true
      );
    }
    
    @Test
    public void fromJStorToJStorDump() throws Throwable {
      JStorage testFile = JStorage.fromJStorFile("src/test/resources/TestJStor.jstor");
      Assert.assertEquals(
        "Should pass generic parse to dump test.",
        testFile.dumpToJStor().equals(
            "BEGIN;DEFINE INTEGER Weight = 80;DEFINE INTEGER Height = 194;DEFINE STRING Name = \"Callum\";DEFINE INTEGER Person.Weight = 74;DEFINE INTEGER Person.Height = 164;DEFINE STRING Person.Name = \"John\";DEFINE INTEGER PersonTwo.Weight.Value = 55;DEFINE STRING PersonTwo.Weight.Unit = \"kg\";DEFINE INTEGER PersonTwo.Height.Value = 158;DEFINE STRING PersonTwo.Height.Unit = \"cm\";DEFINE STRING PersonTwo.Name = \"Marcos\";END;"
        ),
        true
      );
    }
    
    @Test
    public void fromJStorToJSONDump() throws Throwable {
      JStorage testFile = JStorage.fromJStorFile("src/test/resources/TestJStor.jstor");
      Assert.assertEquals(
         "Should pass generic conversion to JSON.",
         testFile.dumpToJSON().equals(
            "{\"JStorage\":{\"Height\":194,\"PersonTwo\":{\"Height\":{\"Value\":158,\"Unit\":\"cm\"},\"Weight\":{\"Value\":55,\"Unit\":\"kg\"},\"Name\":\"Marcos\"},\"Person\":{\"Height\":164,\"Weight\":74,\"Name\":\"John\"},\"Weight\":80,\"Name\":\"Callum\"}}"
         ),
         true
      );
    }
    
    //BROKE
    @Test
    public void fromJSONToJStorDump() throws Throwable {
      JStorage testFile = JStorage.fromJSONFile("src/test/resources/TestJSON.json");
      Assert.assertEquals(
         "Should pass conversion from JSON to JStor file.",
         testFile.dumpToJStor().equals(
            "BEGIN;DEFINE STRING PersonTwo.Height.Unit = \"cm\";DEFINE INTEGER PersonTwo.Height.Value = 158;DEFINE STRING PersonTwo.Name = \"Marcos\";DEFINE INTEGER PersonTwo.Weight.Value = 55;DEFINE STRING PersonTwo.Weight.Unit = \"kg\";DEFINE INTEGER Person.Height = 164;DEFINE STRING Person.Name = \"John\";DEFINE INTEGER Person.Weight = 74;DEFINE INTEGER Height = 194;DEFINE INTEGER Weight = 80;DEFINE STRING Name = \"Callum\";END;"
         ),
         true
      );
    }
    
    @Test
    public void fromJStorToMap() throws Throwable {
      JStorage testFile = JStorage.fromJStorFile("src/test/resources/TestJStor.jstor");
      Assert.assertEquals(
         "Should pass conversion from JStor to Map<String, Object>.",
         testFile.toMap().toString().equals(
            "{JStorage={Height=194, PersonTwo={Height={Value=158, Unit=cm}, Weight={Value=55, Unit=kg}, Name=Marcos}, Person={Height=164, Weight=74, Name=John}, Weight=80, Name=Callum}}"
         ),
         true
      );
    }
    
    //BROKE
    @Test
    public void fromMapToJStorDump() throws Throwable {
      HashMap<String, Object> testMap = new HashMap<String, Object>();
      testMap.put("Name", "Callum");
      testMap.put("Height", 194);
      testMap.put("Weight", 80);
      
      HashMap<String, Object> person = new HashMap<String, Object>();
      person.put("Name", "John");
      person.put("Height", 164);
      person.put("Weight", 74);
      testMap.put("Person", person);
      
      HashMap<String, Object> personTwo = new HashMap<String, Object>();
      personTwo.put("Name", "Paul");
      HashMap<String, Object> weight = new HashMap<String, Object>();
      weight.put("Value", 55);
      weight.put("Unit", "kg");
      HashMap<String, Object> height = new HashMap<String, Object>();
      height.put("Value", 158);
      height.put("Unit", "cm");
      personTwo.put("Weight", weight);
      personTwo.put("Height", height);
      testMap.put("PersonTwo", personTwo);    
      
      Assert.assertEquals(
         "Should pass conversion from JavaMap.",
         JStorage.fromMap(testMap).dumpToJStor().equals(
            "BEGIN;DEFINE STRING PersonTwo.Height.Unit = \"cm\";DEFINE INTEGER PersonTwo.Height.Value = 158;DEFINE STRING PersonTwo.Name = \"Paul\";DEFINE INTEGER PersonTwo.Weight.Value = 55;DEFINE STRING PersonTwo.Weight.Unit = \"kg\";DEFINE INTEGER Person.Height = 164;DEFINE STRING Person.Name = \"John\";DEFINE INTEGER Person.Weight = 74;DEFINE INTEGER Height = 194;DEFINE INTEGER Weight = 80;DEFINE STRING Name = \"Callum\";END;"
         ),
         true
      );
    }
}