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
    public void read() throws Throwable {
      JStorage testFile = JStorage.fromJStorFile("src/test/resources/TestStorage.jstor");
      Assert.assertEquals(
         "Should pass generic parse test",
         testFile.toString().equals(
            "NAME: JStorage - VALUE: [NAME: RECTANGLE - VALUE: [NAME: X - VALUE: 12, NAME: Y - VALUE: 10, NAME: NAME - VALUE: \"Rectangle 1\"], NAME: SQUARE - VALUE: [NAME: NAME - VALUE: \"Square 1\"]]"
         ),
         true
      );
    }
}