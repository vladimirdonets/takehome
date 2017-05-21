package com.lenda.takehome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lenda.takehome.service.game.MappedWordFinder;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author vdonets
 */
public class Game {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("board")
    private final List<String> board;

    @JsonProperty("score")
    private int score = 0;

    @JsonProperty("words")
    private final LinkedHashSet<Word> words = new LinkedHashSet<>();
    private final MappedWordFinder wordFinder;

    public void addWord(Word word) {
        words.add(word);
        score += word.getScore();
    }

    public Game(List<String> board) {
        this.board = board;
        wordFinder = new MappedWordFinder(board);
        id = new Long(super.hashCode());
    }

    public Long getId() {
        return id;
    }

    /**
     * Returns rows of the board in this game
     *
     * @return
     */
    public List<String> getBoard() {
        return board;
    }

    /**
     * Total score?
     *
     * @return
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Words and associated scores?
     *
     * @return
     */
    public LinkedHashSet<Word> getWords() {
        return words;
    }



    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", board=" + board +
                ", score=" + score +
                ", words=" + words +
                '}';
    }

    @JsonIgnore
    public MappedWordFinder getWordFinder() {
        return wordFinder;
    }
}
