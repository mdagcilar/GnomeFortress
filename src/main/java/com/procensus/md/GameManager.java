package com.procensus.md;

import org.apache.log4j.Logger;

import java.util.*;

class GameManager {
    private static org.apache.log4j.Logger logger = Logger.getLogger(GameManager.class);

    private List<Group> groups = new ArrayList<>();
    private Fortress fortress = new Fortress();

    /**
     * Starts the execution of the Gnome Fortress
     *
     * @param noOfGroups       - number of Groups to create
     * @param noGnomesPerGroup - number of Gnomes to have per Group created
     */
    void start(int noOfGroups, int noGnomesPerGroup) {
        createGroups(noOfGroups, noGnomesPerGroup);
        fortress.initGnomePositions(groups);
        getGnomePositions();
        beginFight();
    }

    /**
     * Creates N groups of M gnomes and add them to the List of Groups
     *
     * @param noOfGroups       - number of Groups to create
     * @param noGnomesPerGroup - number of Gnomes to have per Group created
     */
    private void createGroups(int noOfGroups, int noGnomesPerGroup) {
        for (int i = 1; i <= noOfGroups; i++) {
            groups.add(new Group(i, noGnomesPerGroup));
        }
        logger.info("Groups have been made");
    }


    /**
     * Whilst at least 2 Gnomes from different groups still exist continue playing the game.
     * Make moves, fight, and combine strengths
     * End game and declare the winning Group or if only a single Gnome announce the Team and individual Gnome
     */
    void beginFight() {
        logger.info("Let the game begin");

        move();

//        while (atLeast2GnomesAlive()) {
//            move();
//        }
        logger.info("Winner! Game has ended");
    }

    // Return true if at least 2 Gnomes still live
    private boolean atLeast2GnomesAlive() {

        return true;
    }

    /**
     * Loop through all Gnomes in each Group and make one move.
     * If an adjacent Gnome is found in any direction, make that move
     * otherwise move in a random direction one space at a time, in
     * one of the four cardinal directions: North, South, East or West.
     */
    private void move() {
        for (Group group : groups) {
            for (Gnome gnome : group.getGnomeList()) {
                if (moveToAdjGnome(gnome)) {
                    // made the move, now fight or combine
                    break;
                } else {
                    moveRandom(gnome);
                    // made the move, now fight or combine
                }
            }
        }
        logger.info("All Gnomes have moved one space");
        fortress.printFloorPlan();
    }

    /**
     * Check
     *
     * @return
     */
    private boolean moveToAdjGnome(Gnome gnome) {
        return false;
    }

    private void moveRandom(Gnome gnome) {
        List<String> directions = Arrays.asList("N", "S", "E", "W");
        Collections.shuffle(directions);    // randomly sort array

        for (String direction : directions) {
            if (validMove(gnome, direction)) {
                moveGnome(gnome, direction);
                return;
            }
        }
    }

    private boolean validMove(Gnome gnome, String direction) {
        Tile[][] floorPlan = fortress.getFloorPlan();
        Tile currLocation = gnome.getTile();

        switch (direction) {
            case "N":
                return floorPlan[currLocation.getRow() - 1][currLocation.getColumn()].isWalkable();
            case "S":
                return floorPlan[currLocation.getRow() + 1][currLocation.getColumn()].isWalkable();
            case "E":
                return floorPlan[currLocation.getRow()][currLocation.getColumn() - 1].isWalkable();
            case "W":
                return floorPlan[currLocation.getRow()][currLocation.getColumn() + 1].isWalkable();
        }
        return false;
    }

    private void moveGnome(Gnome gnome, String direction) {
        Tile[][] floorPlan = fortress.getFloorPlan();
        Tile currLocation = gnome.getTile();
        Tile nextLocation;

        switch (direction) {
            case "N":
                nextLocation = floorPlan[currLocation.getRow() - 1][currLocation.getColumn()];
                currLocation.removeGnome();
                nextLocation.addGnome();
                gnome.setTile(nextLocation);
            case "S":
                nextLocation = floorPlan[currLocation.getRow() + 1][currLocation.getColumn()];
                currLocation.removeGnome();
                nextLocation.addGnome();
                gnome.setTile(nextLocation);
            case "E":
                nextLocation = floorPlan[currLocation.getRow()][currLocation.getColumn() - 1];
                currLocation.removeGnome();
                nextLocation.addGnome();
                gnome.setTile(nextLocation);
            case "W":
                nextLocation = floorPlan[currLocation.getRow()][currLocation.getColumn() + 1];
                currLocation.removeGnome();
                nextLocation.addGnome();
                gnome.setTile(nextLocation);
        }
    }

    private void getGnomePositions() {
        for (Group group : groups) {
            for (Gnome gnome : group.getGnomeList()) {
                System.out.println(gnome.getTile().toString());
            }
        }
    }
}
