package com.lenda.takehome.dictionary;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
