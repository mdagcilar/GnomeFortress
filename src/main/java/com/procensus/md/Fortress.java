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
    private final String FLOOR_PLAN_PATH = "resources/floorplan_original";

    Fortress() {
        readAndBuildFortress();
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
            System.out.println("FileNotFoundException: Failed to read floor plan from " + FLOOR_PLAN_PATH + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // Gets the dimensions of the floor plan from the file and initialise the floor plan 2D array
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
            logger.debug("FileNotFoundException: Failed to read floor plan from " + FLOOR_PLAN_PATH + e.getMessage());
        } catch (IOException e) {
            logger.debug("IOException: " + e.getMessage());
        }
    }

    // Prints out the floor plan to stdout
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
    void initGnomePositions(List<Group> groups) {
        List<Tile> availableTitles = getAvailableStartingPositions();
        Random random = new Random();

        for (Group group : groups) {
            for (Gnome gnome : group.getGnomeList()) {
                int randomTile = random.nextInt(availableTitles.size());    // gets a random int within range

                gnome.setTile(availableTitles.get(randomTile));             // adds a Tile to the Gnome
                availableTitles.get(randomTile).setGnome(gnome);            // adds a 'G' character to the floor plan

                availableTitles.remove(randomTile);                         // remove the Tile from the List
            }
        }
        logger.info("Gnomes have been placed");
        printFloorPlan();
    }

    // Returns all white-space characters as a List of Tiles
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

    Tile isNorthReachable(Tile currLocation) {
        if (currLocation.getRow() == 0) {
            // cannot move up
            return null;
        }
        return floorPlan[currLocation.getRow() - 1][currLocation.getColumn()];
    }

    Tile isSouthReachable(Tile currLocation) {
        if (currLocation.getRow() == floorPlan.length - 1) {
            // cannot move up
            return null;
        }
        return floorPlan[currLocation.getRow() + 1][currLocation.getColumn()];
    }

    Tile isWestReachable(Tile currLocation) {
        if (currLocation.getColumn() == 0) {

            // cannot move right
            return null;
        }
        return floorPlan[currLocation.getRow()][currLocation.getColumn() - 1];
    }

    Tile isEastReachable(Tile currLocation) {
        if (currLocation.getColumn() == floorPlan[0].length - 1) {
            // cannot move left
            return null;
        }
        return floorPlan[currLocation.getRow()][currLocation.getColumn() + 1];
    }


    void moveNorth(int groupId, Gnome gnome, Tile currLocation) {
        Tile newLocation = floorPlan[currLocation.getRow() - 1][currLocation.getColumn()];
        checkForPotionsAndBombs(newLocation, gnome);

        newLocation.moveIndicator(currLocation, groupId);
        currLocation.removeGnome();
        newLocation.setGnome(gnome);
        gnome.setTile(newLocation);
    }

    void moveSouth(int groupId, Gnome gnome, Tile currLocation) {
        Tile newLocation = floorPlan[currLocation.getRow() + 1][currLocation.getColumn()];
        checkForPotionsAndBombs(newLocation, gnome);

        newLocation.moveIndicator(currLocation, groupId);
        currLocation.removeGnome();
        newLocation.setGnome(gnome);
        gnome.setTile(newLocation);
    }

    void moveWest(int groupId, Gnome gnome, Tile currLocation) {
        Tile newLocation = floorPlan[currLocation.getRow()][currLocation.getColumn() - 1];
        checkForPotionsAndBombs(newLocation, gnome);

        newLocation.moveIndicator(currLocation, groupId);
        currLocation.removeGnome();
        newLocation.setGnome(gnome);
        gnome.setTile(newLocation);
    }

    void moveEast(int groupId, Gnome gnome, Tile currLocation) {
        Tile newLocation = floorPlan[currLocation.getRow()][currLocation.getColumn() + 1];
        checkForPotionsAndBombs(newLocation, gnome);

        newLocation.moveIndicator(currLocation, groupId);
        currLocation.removeGnome();
        newLocation.setGnome(gnome);
        gnome.setTile(newLocation);
    }

    // Checks the Tile for health potions and bombs on the floor plan
    private void checkForPotionsAndBombs(Tile newLocation, Gnome gnome) {
        if (newLocation.isHealthPotion()) {
            gnome.consumeHealthPotion();
            System.out.println("gnome " + gnome.getId() + " from team " + gnome.getGroupId()
                    + " consumed a health potion at " + newLocation.getCoordinates() + " increasing hit points by 5, new hp: " + gnome.getStrength());
        } else if (newLocation.isBomb()) {
            gnome.kill();
            System.out.println("gnome " + gnome.getId() + " from team " + gnome.getGroupId()
                    + " just uncovered a bomb at " + newLocation.getCoordinates() + " and died instantly from the blast!");
        }
    }
}
