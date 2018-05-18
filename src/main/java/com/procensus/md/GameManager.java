package com.procensus.md;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        beginBattle();
    }

    /**
     * Creates N groups of M gnomes and add them to the List of Groups.
     *
     * @param noOfGroups       - number of Groups to create
     * @param noGnomesPerGroup - number of Gnomes to have per Group created
     */
    private void createGroups(int noOfGroups, int noGnomesPerGroup) {
        // Error handling to check if their are an available number of spaces to create all the Gnomes
        int maxNumberOfGnomes = fortress.getAvailableStartingPositions().size();

        if (noOfGroups * noGnomesPerGroup > maxNumberOfGnomes) {
            logger.error("Number of Gnomes to create (" + (noOfGroups * noGnomesPerGroup)
                    + ") exceeds the number of free corridor spaces in the floor plan (" + maxNumberOfGnomes + ")");
            System.exit(1);
        }

        for (int i = 1; i <= noOfGroups; i++) {
            groups.add(new Group(i, noGnomesPerGroup));
        }

        logger.info(noOfGroups + " groups have been made with " + noGnomesPerGroup + " gnomes per group");
    }


    /**
     * Whilst at least 2 Gnomes from different groups still exist continue the battle of the Gnomes
     * Make moves, fight, and combine strengths
     * End game and declare the winning Group or if only a single Gnome announce the Team and individual Gnome
     */
    private void beginBattle() {
        logger.info("Battle begins ");

        while (atLeastTwoEnemyGnomesAlive()) {
            move();
        }
        logger.info("Congratulations battle has ended team " + getWinningGroup() + " is victorious");
    }

    // Return true if at least 2 Gnomes still live
    private boolean atLeastTwoEnemyGnomesAlive() {
        int gnomeCounter = 0;
        for (Group group : groups) {
            for (Gnome gnome : group.getGnomeList()) {
                if (gnome.isAlive()) {
                    gnomeCounter++;
                    break;
                }
            }
        }
        return gnomeCounter > 1;
    }

    // Returns the winning Group ID to display at the end of the Game
    private String getWinningGroup() {
        for (Group group : groups) {
            for (Gnome gnome : group.getGnomeList()) {
                if (gnome.isAlive()) {
                    return String.valueOf(gnome.getGroupId());
                }
            }
        }
        return null;
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
                if (gnome.isAlive() && moveToAdjGnome(gnome)) {
                    // made the encounter move
                    fortress.printFloorPlan();
                    break;
                } else if (gnome.isAlive()) {
                    moveRandom(gnome);
                }
            }
        }
    }

    // Checks if a Gnome is N, S, E, W are next to each other, and executes the move
    private boolean moveToAdjGnome(Gnome gnome) {
        Tile currLocation = gnome.getTile();

        // check if a Gnome is North
        if (fortress.isNorthReachable(currLocation) != null && fortress.isNorthReachable(currLocation).isGnome()) {
            Gnome otherGnome = fortress.isNorthReachable(currLocation).getGnome();     // store that unknown Gnome
            encounter("N", gnome, otherGnome);
            return true;

            // check if a Gnome is South
        } else if (fortress.isSouthReachable(currLocation) != null && fortress.isSouthReachable(currLocation).isGnome()) {
            Gnome otherGnome = fortress.isSouthReachable(currLocation).getGnome();     // store that unknown Gnome
            encounter("S", gnome, otherGnome);
            return true;

            // check if a Gnome is East
        } else if (fortress.isEastReachable(currLocation) != null && fortress.isEastReachable(currLocation).isGnome()) {
            Gnome otherGnome = fortress.isEastReachable(currLocation).getGnome();     // store that unknown Gnome
            encounter("E", gnome, otherGnome);
            return true;

            // check if a Gnome is West
        } else if (fortress.isWestReachable(currLocation) != null && fortress.isWestReachable(currLocation).isGnome()) {
            Gnome otherGnome = fortress.isWestReachable(currLocation).getGnome();     // store that unknown Gnome
            encounter("W", gnome, otherGnome);
            return true;
        }
        return false;
    }

    /**
     * When Gnomes meet/encounter each other in the Fortress, 3 things can potentially occur
     * 1 - Gnomes are from the same team, and combine strengths into a larger Gnome
     * 2 - Gnomes A is stronger or equal to Gnome B's strength. Gnome A kills B.
     * 3 - Gnome B is stronger than Gnome A, B kills A
     */
    void encounter(String direction, Gnome gnome, Gnome otherGnome) {
        if (gnome.getGroupId() == otherGnome.getGroupId()) {
            // same team - combine Gnome strengths

            logger.info("gnome " + otherGnome.getId() + " from team " + otherGnome.getGroupId() +
                    ", gnome " + gnome.getId() + " from team " + gnome.getGroupId() +
                    " have met at " + otherGnome.getTile().getCoordinates() + " and combined into a strength " +
                    (otherGnome.getStrength() + gnome.getStrength()) + " gnome");

            otherGnome.merge(gnome);
            gnome.getTile().removeGnome();      // remove this Gnome from the fortress
            fortress.moveGnome(direction, otherGnome.getGroupId(), otherGnome, gnome.getTile());     // move current Gnome to that location
        } else if (gnome.getStrength() >= otherGnome.getStrength()) {
            // fight
            logger.info("gnome " + gnome.getId() + " from team " + gnome.getGroupId() +
                    ", gnome " + otherGnome.getId() + " from team " + otherGnome.getGroupId() +
                    " have fought at " + otherGnome.getTile().getCoordinates() +
                    " and gnome " + gnome.getId() + " from team " + gnome.getGroupId() + " was victorious");

            otherGnome.kill();          // remove the defeated Gnome from the Fortress
            fortress.moveGnome(direction, gnome.getGroupId(), gnome, gnome.getTile());              // move current Gnome to that location
        } else {
            // fight
            logger.info("gnome " + gnome.getId() + " from team " + gnome.getGroupId() +
                    ", gnome " + otherGnome.getId() + " from team " + otherGnome.getGroupId() +
                    " have fought at " + otherGnome.getTile().getCoordinates() +
                    " and gnome " + otherGnome.getId() + " from team " + otherGnome.getGroupId() + " was victorious");

            gnome.kill();               // remove the defeated Gnome from the Fortress
            fortress.moveGnome(direction, otherGnome.getGroupId(), otherGnome, gnome.getTile());   // move current Gnome to that location
        }
    }

    // Loops through a shuffled list of directions, until a valid move is found and executed
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

    // Checks the validity of the move and returns a boolean flag
    private boolean validMove(Gnome gnome, String direction) {
        Tile currLocation = gnome.getTile();

        switch (direction) {
            case "N":
                return fortress.isNorthReachable(currLocation) != null && fortress.isNorthReachable(currLocation).isWalkable();
            case "S":
                return fortress.isSouthReachable(currLocation) != null && fortress.isSouthReachable(currLocation).isWalkable();
            case "E":
                return fortress.isEastReachable(currLocation) != null && fortress.isEastReachable(currLocation).isWalkable();
            case "W":
                return fortress.isWestReachable(currLocation) != null && fortress.isWestReachable(currLocation).isWalkable();
        }
        return false;
    }

    // Moves the Gnome in the given direction
    private void moveGnome(Gnome gnome, String direction) {
        Tile currLocation = gnome.getTile();

        switch (direction) {
            case "N":
                fortress.moveGnome("N", gnome.getGroupId(), gnome, currLocation);
                break;
            case "S":
                fortress.moveGnome("S", gnome.getGroupId(), gnome, currLocation);
                break;
            case "E":
                fortress.moveGnome("E", gnome.getGroupId(), gnome, currLocation);
                break;
            case "W":
                fortress.moveGnome("W", gnome.getGroupId(), gnome, currLocation);
                break;
        }
    }

    // prints out the Gnome coordinates
    private void getGnomePositions() {
        for (Group group : groups) {
            for (Gnome gnome : group.getGnomeList()) {
                System.out.println(gnome.getTile().toString());
            }
        }
    }
}
