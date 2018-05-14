package com.procensus.md;

import org.apache.log4j.PropertyConfigurator;

public class Starter {

    public static void main(String[] args) {
        initialiseLogger();

        GameManager gameManager = new GameManager();
        gameManager.start(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    private static void initialiseLogger() {
        PropertyConfigurator.configure("resources/log4j.properties");
    }
}
