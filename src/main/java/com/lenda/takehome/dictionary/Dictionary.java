package com.lenda.takehome.dictionary;

/**
 * Checks validity of words
 *
 * @author vdonets
 */
public interface Dictionary {

    /**
     * Checks if given word is valid
     *
     * @param word word to check
     * @return true if valid false otherwise
     */
    boolean isValid(String word);
}
