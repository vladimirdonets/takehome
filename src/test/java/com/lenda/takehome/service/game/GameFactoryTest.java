package com.lenda.takehome.service.game;

import com.lenda.takehome.model.Game;
import com.lenda.takehome.service.game.config.FactoryConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author vdonets
 */
public class GameFactoryTest {
    private final static Logger logger = LoggerFactory.getLogger(GameFactoryTest.class);

    private char[] d1 = new char[]{'a', 'b', 'c', 'd'};
    private char[] d2 = new char[]{'e', 'f', 'g', 'h'};
    private char[] d3 = new char[]{'1', '2', '3', '4'};
    private char[] d4 = new char[]{'5', '6', '7', '8'};

    private GameFactoryImpl factory = new GameFactoryImpl(
            new FactoryConfig() {
                @Override
                public List<char[]> getDice() {
                    return Arrays.asList(d1, d2, d3, d4);
                }

                @Override
                public int getX() {
                    return 2;
                }

                @Override
                public int getY() {
                    return 2;
                }
            });

    @Test
    public void gameCreationTest() {
        Game game = factory.newGame();
        logger.info("game = " + game);

        assertTrue(toSet(d1).contains(game.getBoard().get(0).toCharArray()[0]));
        assertTrue(toSet(d2).contains(game.getBoard().get(0).toCharArray()[1]));
        assertTrue(toSet(d3).contains(game.getBoard().get(1).toCharArray()[0]));
        assertTrue(toSet(d4).contains(game.getBoard().get(1).toCharArray()[1]));

        assertTrue(game.getWordFinder().isValid(
                String.valueOf(game.getBoard().get(0).toCharArray()[0])
                        + game.getBoard().get(0).toCharArray()[1]));

        assertTrue(game.getWordFinder().isValid(
                String.valueOf(game.getBoard().get(1).toCharArray()[0])
                        + game.getBoard().get(1).toCharArray()[1]));

        assertTrue(game.getWordFinder().isValid(
                String.valueOf(game.getBoard().get(1).toCharArray()[0])
                        + game.getBoard().get(0).toCharArray()[0]));

        assertTrue(game.getWordFinder().isValid(
                String.valueOf(game.getBoard().get(0).toCharArray()[1])
                        + game.getBoard().get(1).toCharArray()[1]));

        assertFalse(game.getWordFinder().isValid(
                String.valueOf(game.getBoard().get(1).toCharArray()[1])
                        + game.getBoard().get(1).toCharArray()[1]));

        assertFalse(game.getWordFinder().isValid(
                String.valueOf(game.getBoard().get(0).toCharArray()[0])
                        + game.getBoard().get(1).toCharArray()[1]));

    }

    private Set toSet(char[] chars) {
        Set set = new HashSet();
        for (int i = 0; i < chars.length; i++) {
            set.add(chars[i]);
        }
        return set;
    }
}
