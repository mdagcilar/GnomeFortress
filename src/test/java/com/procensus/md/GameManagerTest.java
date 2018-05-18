package com.procensus.md;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameManagerTest {
    private Fortress fortress;
    private Gnome gnomeA, gnomeB;
    private GameManager gameManager;

    @Before
    public void setup() {
        fortress = new Fortress("resources/floorplan_original");
        gameManager = new GameManager();
        gnomeA = new Gnome(1, 1, 5);     // Team 1, Gnome 1
        gnomeB = new Gnome(2, 1, 5);    // Team 2, Gnome 1
    }

    /**
     * This simulated encounter should show that when Gnomes A approaches Gnome B.
     * And the Gnomes have equal strength, Gnome A attacks and wins Gnome B's position on the floor.
     * The encounter() in GameManager should move the Gnome A to B's position and delete B from
     * the floor.
     */
    @Test
    public void encounterWithEqualStrength() {
        Tile tile = fortress.getFloorPlan()[1][0];      // Gnome A's location
        Tile tile2 = fortress.getFloorPlan()[2][2];     // Gnome B's location
        tile.setGnome(gnomeA);
        tile2.setGnome(gnomeB);

        fortress.moveGnome("E", gnomeA.getGroupId(), gnomeA, tile);           // Moves the Gnome in the Fortress
        fortress.moveGnome("W", gnomeB.getGroupId(), gnomeB, tile2);          // Moves the Gnome in the Fortress

        gameManager.encounter("S", gnomeA, gnomeB);                          // Gnome A encounters B by moving South

        assertEquals(gnomeA.getStrength(), 5);
        assertEquals(gnomeB.getStrength(), 0);

        assertEquals(gnomeA.getTile().getRow(), 2);                          // Gnome A should be in B's location
        assertEquals(gnomeA.getTile().getColumn(), 1);
    }

    /**
     * Tests an encounter where Gnome B is stronger than Gnome A
     */
    @Test
    public void encounterWithDiffStrength() {
        gnomeA = new Gnome(1, 1, 5);         // Team 1, Gnome A
        gnomeB = new Gnome(2, 1, 10);        // Team 2, Gnome B

        Tile tile = fortress.getFloorPlan()[1][0];      // Gnome A's location
        Tile tile2 = fortress.getFloorPlan()[2][2];     // Gnome B's location
        tile.setGnome(gnomeA);
        tile2.setGnome(gnomeB);

        fortress.moveGnome("E", gnomeA.getGroupId(), gnomeA, tile);           // Moves the Gnome in the Fortress
        fortress.moveGnome("W", gnomeB.getGroupId(), gnomeB, tile2);        // Moves the Gnome in the Fortress

        gameManager.encounter("S", gnomeA, gnomeB);                          // Gnome A encounters B by moving South

        assertEquals(gnomeA.getStrength(), 0);
        assertEquals(gnomeB.getStrength(), 10);

        assertEquals(gnomeB.getTile().getRow(), 2);
        assertEquals(gnomeB.getTile().getColumn(), 1);
    }

    /**
     * Tests an encounter where two Gnomes from the same team meet and combine into one stronger Gnome
     */
    @Test
    public void encounterCombiningGnomes() {
        gnomeA = new Gnome(1, 1, 5);         // Team 1, Gnome A
        gnomeB = new Gnome(1, 2, 10);       // Team 1, Gnome B

        Tile tile = fortress.getFloorPlan()[1][0];      // Gnome A's location
        Tile tile2 = fortress.getFloorPlan()[2][2];     // Gnome B's location
        tile.setGnome(gnomeA);
        tile2.setGnome(gnomeB);

        fortress.moveGnome("E", gnomeA.getGroupId(), gnomeA, tile);           // Moves the Gnome in the Fortress
        fortress.moveGnome("W", gnomeB.getGroupId(), gnomeB, tile2);        // Moves the Gnome in the Fortress

        gameManager.encounter("S", gnomeA, gnomeB);                          // Gnome A encounters B by moving South

        assertEquals(0, gnomeA.getStrength());
        assertEquals(15, gnomeB.getStrength());

        assertEquals(gnomeB.getTile().getRow(), 2);
        assertEquals(gnomeB.getTile().getColumn(), 1);
    }
}