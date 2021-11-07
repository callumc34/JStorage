package com.callumc34.jstorage;

import com.callumc34.jstorage.JStorage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReadTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void readToString() throws Throwable {
      JStorage testFile = JStorage.fromJStorFile("src/test/resources/TestStorage.jstor");
      Assert.assertEquals(
         "Should pass generic parse test",
         testFile.toString().equals(
            "NAME: JStorage - VALUE: [NAME: RECTANGLE - VALUE: [NAME: X - VALUE: 12, NAME: Y - VALUE: 10, NAME: NAME - VALUE: \"Rectangle 1\"], NAME: SQUARE - VALUE: [NAME: NAME - VALUE: \"Square 1\"]]"
         ),
         true
      );
    }
    
    @Test
    public void readToJStorString() throws Throwable {
      JStorage testFile = JStorage.fromJStorFile("src/test/resources/TestDump.jstor");
      Assert.assertEquals(
        "Should pass generic dump test",
        testFile.dumpToJStor().equals(
            "BEGIN;DEFINE INTEGER Weight = 80;DEFINE INTEGER Height = 194;DEFINE STRING Name = \"Callum\";DEFINE INTEGER Person.Weight = 74;DEFINE INTEGER Person.Height = 164;DEFINE STRING Person.Name = \"John\";DEFINE INTEGER PersonTwo.Weight.Value = 55;DEFINE STRING PersonTwo.Weight.Unit = \"kg\";DEFINE INTEGER PersonTwo.Height.Value = 158;DEFINE STRING PersonTwo.Height.Unit = \"cm\";DEFINE STRING PersonTwo.Name = \"Marcos\";END;"
        ),
        true
      );
    }
}