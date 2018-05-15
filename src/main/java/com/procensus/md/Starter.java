package com.procensus.md;

import org.apache.log4j.PropertyConfigurator;

public class Starter {

    public static void main(String[] args) {
        initialiseLogger();

        if(args.length != 2){
            System.out.println("To play Gnome Fortress please ");
            System.out.println("Please enter two arguments: e.g. 2 4");
        }

        GameManager gameManager = new GameManager();
        gameManager.start(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    private static void initialiseLogger() {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    private void promptUserInput(){

    }
}
