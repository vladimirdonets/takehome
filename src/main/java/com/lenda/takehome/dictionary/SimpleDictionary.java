package com.lenda.takehome.dictionary;

import com.lenda.takehome.service.game.GameFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dictionary implementation that checks if a word is in its
 * data set.
 *
 * @author vdonets
 */
public class SimpleDictionary implements Dictionary {

    private final static Logger logger = LoggerFactory.getLogger(GameFactoryImpl.class);

    private Set<String> words = new HashSet<>();

    /**
     * Creates a dictionary containing all new line
     * separated words in a file
     *
     * @param fileName name of dictionary file
     */
    public SimpleDictionary(String fileName) throws IOException {
        if (logger.isDebugEnabled())
            logger.debug("Creating dictionary from [" + fileName + "]");
        Files.lines(Paths.get(fileName)).forEach(line -> {
            line = line.toUpperCase();
            words.add(line.trim());
        });
        if (logger.isTraceEnabled())
            logger.trace("loaded [" + words.size() + "] words to dictionary");
    }

    /**
     * Creates a configured dictionary. Should be used in production
     *
     * @param config Spring's config file
     * @throws IOException
     */
    public SimpleDictionary(SimpleDictionaryConfig config) throws IOException {
        this(config.getFile());
    }

    /**
     * Loads default dictionary from classpath
     *
     * @throws IOException
     */
    public SimpleDictionary() throws IOException {
        this(SimpleDictionary.class.getClassLoader().getResource("dictionary.txt").getFile());
    }

    @Override
    public boolean isValid(String word) {
        if (words.contains(word)) {
            if (logger.isTraceEnabled())
                logger.trace("[" + word + "] is valid");
            return true;
        } else {
            if (logger.isTraceEnabled())
                logger.trace("[" + word + "] NOT valid");
            return false;
        }
    }
}
