package com.lenda.takehome.service.game;

/**
 * Checks if a word can be found on a board
 *
 * @author vdonets
 */
public interface WordFinder {

    /**
     * Checks if given word is valid in this finder
     *
     * @param word a word to check
     * @return true if valid
     */
    boolean isValid(String word);
}
