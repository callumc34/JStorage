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
      JStor file = JStorage.parseJStor("src/test/resources/TestStorage.jstor");
      System.out.printf(file.getData().toString());
    }
}