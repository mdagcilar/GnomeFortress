package com.procensus.md;

import org.apache.log4j.PropertyConfigurator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Starter {

    public static void main(String[] args) {
        initialiseLogger();
        GameManager gameManager = new GameManager();

        // Error handling for command line arguments
        if (args.length != 2) {
            System.out.println("Incorrect command line arguments found");

            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Enter the number of Gnome teams you wish to simulate:");
                int groups = scanner.nextInt();
                System.out.println("Enter the number of Gnomes per team:");
                int gnomesPerGroup = scanner.nextInt();
                gameManager.start(groups, gnomesPerGroup);
            } catch (InputMismatchException e) {
                System.out.println("Incorrect argument entered please enter digits only");
            }
        } else {
            try {
                gameManager.start(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            } catch (NumberFormatException e) {
                System.out.println("Error: Program arguments must be integers");
            }
        }
    }

    private static void initialiseLogger() {
        PropertyConfigurator.configure("resources/log4j.properties");
    }
}
