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
      JStorage readTest = JStorage.parse("../../../../resources/TestStorage.jstor");
      Assert.assertEquals(
         "Check test jstor file for correct parsing should succeed",
         readTest.toString().equals(
         "TEST_JSTOR - {RECTANGLE={X=12, Y=10, NAME=\"Rectangle 1\"}, SQUARE={NAME=\"Square 1\"}}"
         ), true
      );
    }
}