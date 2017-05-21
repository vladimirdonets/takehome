package com.lenda.takehome.service.game.config;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author vdonets
 */
public class FactoryConfigImpl implements FactoryConfig{

    @Value("#{'${game.dice}'.split(',')}")
    private List<char[]> dice;
    @Value("${game.board.x}")
    private int x;
    @Value("${game.board.y}")
    private int y;

    public List<char[]> getDice() {
        return dice;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
