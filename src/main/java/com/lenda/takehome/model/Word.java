package com.lenda.takehome.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A word that a user will find playing the game
 *
 * @author vdonets
 */
public class Word {

    private int score;
    private String value;

    @Override
    public String toString() {
        return "Word{" +
                "score=" + score +
                ", value='" + value + '\'' +
                '}';
    }

    @JsonCreator
    public Word(@JsonProperty("word") String word) {
        this.value = word;
        if (value.length() > 8)
            this.score = 6;
        else
            this.score = value.length() - 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return value.equals(word.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Score associated with this word
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * String literal of this word
     *
     * @return
     */
    public String getWord() {
        return value;
    }
}
