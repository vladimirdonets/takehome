package com.lenda.takehome.service;

import com.lenda.takehome.dictionary.Dictionary;
import com.lenda.takehome.model.Game;
import com.lenda.takehome.model.Word;
import com.lenda.takehome.service.game.GameFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vdonets
 */
public class WordSearchService {
    private final static Logger logger = LoggerFactory.getLogger(WordSearchService.class);

    private final GameFactory factory;
    private final Dictionary dictionary;

    @Value("${service.max.games}")
    private int maxGames;

    public WordSearchService(Dictionary dictionary, GameFactory factory) {
        this.factory = factory;
        this.dictionary = dictionary;
    }

    private ConcurrentHashMap<Long, Game> games = new ConcurrentHashMap<>();

    /**
     * Creates and stores a new game
     *
     * @return a new game
     */
    public Game newGame() {
        if (games.size() >= maxGames)
            return null;
        Game game = factory.newGame();
        if (games.putIfAbsent(game.getId(), game) != null) {
            logger.trace("Games collision with id [" + game.getId() + "] -- retrying");
            return newGame();
        } else
            return game;
    }

    /**
     * Retrieves a game by id
     *
     * @param gameId id of game to retrieve
     * @return game for given id or null
     */
    public Game show(Long gameId) {
        return games.get(gameId);
    }

    /**
     * Attempts to add a word to a game.
     *
     * @param gameId id of game to add word to
     * @param word   word to add to game
     * @return a GameOperationError describing the error or null
     * if operation is successful
     */
    public GameOperationError addWord(Long gameId, Word word) {
        Game game = games.get(gameId);
        if (game == null)
            return GameOperationError.NO_GAME;
        synchronized (game) {
            if (!dictionary.isValid(word.getWord()))
                return GameOperationError.INVALID_WORD;
            if (game.getWords().contains(word))
                return GameOperationError.DUPLICATE_WORD;
            if (!game.getWordFinder().isValid(word.getWord()))
                return GameOperationError.NOT_PLAYABLE;
            game.addWord(word);
            return null;

        }

    }
}
