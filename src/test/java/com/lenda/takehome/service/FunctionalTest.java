package com.lenda.takehome.service;

import com.lenda.takehome.model.Game;
import com.lenda.takehome.model.Word;
import com.lenda.takehome.service.game.GameFactoryImpl;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author vdonets
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml", initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(locations = "classpath:testApplication.properties")
@SpringBootTest
@EnableAutoConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FunctionalTest {

    private final static Logger logger = LoggerFactory.getLogger(FunctionalTest.class);

    @Autowired
    private WordSearchService service;

    private Game game;

    @Test
    public void serviceTests() {
        createGameTest();
        addWordTest();
        addDuplicateTest();
        addInvalidWordTest();
        addUnplayableTest();
        noGameTest();
        showGameTest();
    }

    public void createGameTest() {
        game = service.newGame();
        assertTrue(game != null);
        logger.info("Game created");
    }

    public void addWordTest() {
        assertTrue(service.addWord(game.getId(), new Word("PIERS")) == null);
        assertTrue(service.addWord(game.getId(), new Word("CLEAR")) == null);
        logger.info("Words added");
    }

    public void addDuplicateTest() {
        assertTrue(service.addWord(game.getId(), new Word("PIERS")) == GameOperationError.DUPLICATE_WORD);
        logger.info("Duplicate found");
    }

    public void addInvalidWordTest() {
        assertTrue(service.addWord(game.getId(), new Word("ABCD")) == GameOperationError.INVALID_WORD);
        logger.info("Invalid word found");
    }

    public void addUnplayableTest() {
        assertTrue(service.addWord(game.getId(), new Word("MAN")) == GameOperationError.NOT_PLAYABLE);
        logger.info("Unplayable word found");
    }

    public void noGameTest() {
        assertTrue(service.addWord(game.getId() + 1, new Word("PIERS")) == GameOperationError.NO_GAME);
        logger.info("No game passed");
    }

    public void showGameTest() {
        Game game = service.show(this.game.getId());
        Iterator<Word> iterator = game.getWords().iterator();
        Word piers = iterator.next();
        assertTrue(piers.getWord().equals("PIERS"));
        assertTrue(piers.getScore() == 3);
        Word clear = iterator.next();
        assertTrue(clear.getWord().equals("CLEAR"));
        assertTrue(clear.getScore() == 3);
        assertTrue(game.getScore() == 6);
        logger.info("Game is valid");
    }
}
