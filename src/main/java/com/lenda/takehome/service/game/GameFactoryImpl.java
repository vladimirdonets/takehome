package com.lenda.takehome.service.game;

import com.lenda.takehome.model.Game;
import com.lenda.takehome.service.game.config.FactoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates Game instances
 *
 * @author vdonets
 */
public class GameFactoryImpl implements GameFactory{

    private final static Logger logger = LoggerFactory.getLogger(GameFactoryImpl.class);

    private final Random random = new Random();

    private List<char[]> dice;
    private int x;
    private int y;

    public GameFactoryImpl(FactoryConfig config) {
        dice = config.getDice();
        x = config.getX();
        y = config.getY();
        if (dice.size() != x * y)
            throw new IllegalStateException("Number of dice must be enough to fill the board (game.board.x * game.board.y)");
    }

    /**
     * Creates a new game
     * @return
     */
    public Game newGame() {
        List<String> rows = new ArrayList<String>(y);
        StringBuffer buffer = new StringBuffer();
        dice.forEach(die -> {
            buffer.append(die[random.nextInt(die.length)]);
            if (buffer.length() == x) {
                rows.add(buffer.toString());
                buffer.delete(0, x);
            }
        });
        logger.trace("generated game rows = " + rows);
        return new Game(rows);
    }
}
