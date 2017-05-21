package com.lenda.takehome;

import com.lenda.takehome.model.Game;
import com.lenda.takehome.model.Word;
import com.lenda.takehome.service.GameOperationError;
import com.lenda.takehome.service.WordSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Main entry point
 * @author vdonets
 */
@EnableAutoConfiguration
@ImportResource(value = "classpath*:applicationContext.xml")
@RestController
public class WordSearch {

    private final static Logger logger = LoggerFactory.getLogger(WordSearch.class);


    @Autowired
    private WordSearchService service;

    @RequestMapping(value = "/api/game",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Game> create() {
        logger.info("Starting new game");
        Game game = service.newGame();
        if (game == null)
            return new ResponseEntity<Game>(HttpStatus.INSUFFICIENT_STORAGE);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @RequestMapping(value = "api/game/{game_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity show(@PathVariable("game_id") Long gameId) {
        logger.info("retrieving game for id [" + gameId + "]");
        if (gameId == null)
            new ResponseEntity<String>("Game id not provided", HttpStatus.BAD_REQUEST);
        Game game = service.show(gameId);
        if (game != null)
            return new ResponseEntity<Game>(game, HttpStatus.OK);
        else
            return new ResponseEntity<String>("Game not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "api/game/{game_id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity update(@PathVariable("game_id") Long gameId,
                                 @RequestBody Word word) {
        logger.info("adding [" + word + " to game [" + gameId + "]");
        GameOperationError error = service.addWord(gameId, word);
        if (error == null) {
            if (logger.isDebugEnabled())
                logger.debug("added [" + word + "] to game [" + gameId + "]");
            return new ResponseEntity<Word>(word, HttpStatus.OK);
        } else {
            logger.error("unable to add [" + word + " to game [" + gameId + "] -- " + error);
            switch (error) {
                case NO_GAME:
                    if (logger.isDebugEnabled())
                        logger.debug("NO_GAME");
                    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
                case NOT_PLAYABLE:
                    if (logger.isDebugEnabled())
                        logger.debug("NOT_PLAYABLE");
                    return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
                case INVALID_WORD:
                    if (logger.isDebugEnabled())
                        logger.debug("INVALID_WORD");
                    return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
                case DUPLICATE_WORD:
                    if (logger.isDebugEnabled())
                        logger.debug("DUPLICATE_WORD");
                    return new ResponseEntity<String>(HttpStatus.CONFLICT);
            }
            throw new IllegalStateException("This should never happen...");
        }
    }


    public static void main(String[] args) {
        new SpringApplication(WordSearch.class).run();
    }
}
