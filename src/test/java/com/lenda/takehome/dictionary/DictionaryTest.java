package com.lenda.takehome.dictionary;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author vdonets
 */
public class DictionaryTest {

    SimpleDictionary dict = new SimpleDictionary();

    public DictionaryTest() throws IOException {
    }

    @Test
    public void dictionaryTest(){
        assertTrue(!dict.isValid("do"));
        assertTrue(dict.isValid("ABAFT"));
    }
}
