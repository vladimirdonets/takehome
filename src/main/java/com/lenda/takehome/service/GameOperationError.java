package com.lenda.takehome.service;

/**
 * Types of error that can occur when adding a word
 *
 * @author vdonets
 */
public enum GameOperationError {
    NO_GAME(1), INVALID_WORD(2), DUPLICATE_WORD(3), NOT_PLAYABLE(4);

    private int i;

    GameOperationError(int i) {
        this.i = i;
    }


    public int intValue() {
        return i;
    }
}
