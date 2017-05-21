package com.lenda.takehome.dictionary;

import com.lenda.takehome.service.game.GameFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author vdonets
 */
@ConfigurationProperties("dictionary")
public class SimpleDictionaryConfig{

    private String filePath = System.getProperty("user.dir") + "/config/dictionary.txt";

    public String getFile() {
        return filePath;
    }

    public void setFile(String filePath) {
        this.filePath = filePath;
    }
}
