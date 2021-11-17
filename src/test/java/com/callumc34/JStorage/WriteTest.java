package com.callumc34.jstorage;

import com.callumc34.jstorage.JStorage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriteTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    //Utility
    public static JStorage fillJStorage() {      
      JStorage ret = new JStorage();
      
      String[] objPathOne = {"DEPTH", "TWO"};
      ret.addFromObjectPath(objPathOne, new JStorageObject("TEST", "OK"));
      ret.addFromObjectPath(objPathOne, new JStorageObject("TEST2", "ALL GOOD"));
      
        
      ret.addObject(new JStorageObject("NAME", "TEST FILE"));
      String[] objPathTwo = {"SQUARE"};
        
      ret.addFromObjectPath(objPathTwo, new JStorageObject("NAME", "CALLUM"));
      ret.addFromObjectPath(objPathTwo, new JStorageObject("X", 12));
      ret.addFromObjectPath(objPathTwo, new JStorageObject("Y", 12));
      
      return ret;
    }
    
    //TODO(Callum):Add tests that should fail
    
    @Test
    public void JStorageObjectDepth1Adding() throws Throwable {
        JStorage testFile = new JStorage();
        
        testFile.addObject(new JStorageObject("NAME", "TEST FILE"));
        String[] objPath = {"SQUARE"};
        
        testFile.addFromObjectPath(objPath, new JStorageObject("NAME", "CALLUM"));
        testFile.addFromObjectPath(objPath, new JStorageObject("X", 12));
        testFile.addFromObjectPath(objPath, new JStorageObject("Y", 12));
        
        Assert.assertEquals(
            "Should pass generic depth 1 object adding test.",
            testFile.toString().equals(
                "NAME: JStorage - VALUE: [NAME: NAME - VALUE: TEST FILE, NAME: SQUARE - VALUE: [NAME: NAME - VALUE: CALLUM, NAME: X - VALUE: 12, NAME: Y - VALUE: 12]]"),
            true
        );
    }
    
    @Test
    public void JStorageObjectDepth2Adding() throws Throwable {
      JStorage testFile = new JStorage();
      
      String[] objPath = {"DEPTH", "TWO"};
      testFile.addFromObjectPath(objPath, new JStorageObject("TEST", "OK"));
      testFile.addFromObjectPath(objPath, new JStorageObject("TEST2", "ALL GOOD"));
      
      Assert.assertEquals(
         "Should pass generic depth 2 object adding test.",
         testFile.toString().equals(
            "NAME: JStorage - VALUE: [NAME: DEPTH - VALUE: [NAME: TWO - VALUE: [NAME: TEST - VALUE: OK, NAME: TEST2 - VALUE: ALL GOOD]]]"
         ),
         true
      );
    }
    
    @Test
    public void JStorageWriteToNoneObject() throws Throwable {
      JStorage testFile = new JStorage();
      
      testFile.addObject(new JStorageObject("TEST", "STRING TYPE"));
      
      String[] objPath = {"TEST"};
      Assert.assertEquals(
         "Should not be able to write to a pre-defined object with a value.", 
         testFile.addFromObjectPath(objPath, new JStorageObject("SHOULD", "FAIL")),
         false
      );
    }
    
    //TODO(Callum): Ensure dumped correctly
    //TODO(Callum): Dump to temp folder
    @Test
    public void JStorageWriteToFile() throws Throwable {
      JStorage testFile = fillJStorage();
      Assert.assertEquals(
         "Should pass generic dump to file test.",
         testFile.dumpToJStorFile("src/test/resources/out/TestDump.jstor"),
         true
      );            
    }
    
    @Test
    public void JStorageWriteToJSON() throws Throwable {
      JStorage testFile = fillJStorage();
      Assert.assertEquals(
         "Should pass generic dump to JSON test.",
         testFile.dumpToJSONFile("src/test/resources/out/TestDump.json"),
         true
      );
    }
    
    @Test
    public void JStorageWriteToYAML() throws Throwable {
      JStorage testFile = fillJStorage();
      Assert.assertEquals(
         "Should pass generic dump to YAML test.",
         testFile.dumpToYAMLFile("src/test/resources/out/TestDump.json"),
         true
      );
    }
}