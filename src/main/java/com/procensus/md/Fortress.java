package com.procensus.md;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Fortress {
    private static org.apache.log4j.Logger logger = Logger.getLogger(Fortress.class);

    private Tile[][] floorPlan;
    private final String FLOOR_PLAN_PATH = "resources/floorplan";

    Fortress() {
        readAndBuildFortress();
    }

    public Tile[][] getFloorPlan() {
        return floorPlan;
    }


    // Reads in the floor plan and builds a 2D array of tiles.
    private void readAndBuildFortress() {
        initialiseFloorPlan();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FLOOR_PLAN_PATH))) {

            String line;
            int row = 0;
            while ((line = bufferedReader.readLine()) != null) {
                int column = 0;

                // loop through each character of the line
                for (char character : line.toCharArray()) {
                    floorPlan[row][column] = new Tile(character, row, column);
                    column++;
                }
                row++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Failed to read floor plan from resources/floorplan " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Gets the dimensions of the floor plan from the file and initialise the floor plan 2D array
     */
    private void initialiseFloorPlan() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FLOOR_PLAN_PATH))) {

            String line;
            int height = 0, width = 0;
            while ((line = bufferedReader.readLine()) != null) {
                width = line.length();
                height++;
            }
            // initialise the floorPlan
            floorPlan = new Tile[height][width];

        } catch (FileNotFoundException e) {
            logger.debug("FileNotFoundException: Failed to read floor plan from resources/floorplan " + e.getMessage());
        } catch (IOException e) {
            logger.debug("IOException: " + e.getMessage());
        }
    }

    /**
     * Prints out the floor plan to stdout
     */
    void printFloorPlan() {
        for (Tile[] tileArray : floorPlan) {
            for (Tile t : tileArray) {
                System.out.print(t.getCharacter());
            }
            System.out.println();
        }
    }

    /**
     * Loops through all of the Gnomes in each Group and adds them to a random tile.
     * Gnomes cannot be placed on the same tile
     */
    void initGnomePositions(List<Group> groups){
        List<Tile> availableTitles = getAvailableStartingPositions();
        Random random = new Random();

        for (Group group : groups){
            for (Gnome gnome : group.getGnomeList()){
                int randomTile = random.nextInt(availableTitles.size());    // gets a random int within range

                gnome.setTile(availableTitles.get(randomTile));             // adds a Tile to the Gnome
                availableTitles.get(randomTile).addGnome();                 // adds a 'G' character to the floor plan
                availableTitles.remove(randomTile);                         // remove the Tile from the List
            }
        }
        logger.info("Gnomes have been placed");
        printFloorPlan();
    }

    /**
     * Returns all white-space characters as a List of Tiles
     */
    private List<Tile> getAvailableStartingPositions() {
        List<Tile> availableTiles = new ArrayList<>();

        for (Tile[] tileArray : floorPlan) {
            for (Tile tile : tileArray) {
                if (tile.isWalkable()) {
                    availableTiles.add(tile);
                }
            }
        }
        return availableTiles;
    }
}
