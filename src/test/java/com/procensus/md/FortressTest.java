package com.procensus.md;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FortressTest {

    private Fortress fortress;
    private Gnome gnome, gnome2;

    @Before
    public void setup() {
        fortress = new Fortress("resources/floorplan_original");
        gnome = new Gnome(1, 1, 5);     // Team 1, Gnome 1
        gnome2 = new Gnome(2, 1, 5);    // Team 2, Gnome 1
    }


    @Test
    public void moveNorth() {
        Tile tile = fortress.getFloorPlan()[2][1];
        tile.setGnome(gnome);

        fortress.moveGnome("N", gnome.getGroupId(), gnome, tile);           // Moves the Gnome in the Fortress

        assertNotEquals(gnome, fortress.getFloorPlan()[2][1].getGnome());   // check Gnome has been removed from old location
        assertNotEquals(gnome.getTile(), tile);                             // check Gnome's Tile has changed

        assertEquals(gnome, fortress.getFloorPlan()[1][1].getGnome());      // check Gnome has moved to correct Tile
        assertEquals(gnome.getTile(), fortress.getFloorPlan()[1][1]);       // check Gnome has new Tile associated with it
    }

    @Test
    public void moveSouth() {
        Tile tile = fortress.getFloorPlan()[2][1];
        tile.setGnome(gnome);

        fortress.moveGnome("S", gnome.getGroupId(), gnome, tile);           // Moves the Gnome in the Fortress

        assertNotEquals(gnome, fortress.getFloorPlan()[2][1].getGnome());   // check Gnome has been removed from old location
        assertNotEquals(gnome.getTile(), tile);                             // check Gnome's Tile has changed

        assertEquals(gnome, fortress.getFloorPlan()[3][1].getGnome());      // check Gnome has moved to correct Tile
        assertEquals(gnome.getTile(), fortress.getFloorPlan()[3][1]);       // check Gnome has new Tile associated with it
    }

    @Test
    public void moveWest() {
        Tile tile = fortress.getFloorPlan()[4][5];
        tile.setGnome(gnome);

        fortress.moveGnome("W", gnome.getGroupId(), gnome, tile);           // Moves the Gnome in the Fortress

        assertNotEquals(gnome, fortress.getFloorPlan()[4][5].getGnome());   // check Gnome has been removed from old location
        assertNotEquals(gnome.getTile(), tile);                             // check Gnome's Tile has changed

        assertEquals(gnome, fortress.getFloorPlan()[4][4].getGnome());      // check Gnome has moved to correct Tile
        assertEquals(gnome.getTile(), fortress.getFloorPlan()[4][4]);       // check Gnome has new Tile associated with it
    }

    @Test
    public void moveEast() {
        Tile tile = fortress.getFloorPlan()[4][5];
        tile.setGnome(gnome);

        fortress.moveGnome("E", gnome.getGroupId(), gnome, tile);// Moves the Gnome in the Fortress

        assertNotEquals(gnome, fortress.getFloorPlan()[4][5].getGnome());   // check Gnome has been removed from old location
        assertNotEquals(gnome.getTile(), tile);                             // check Gnome's Tile has changed

        assertEquals(gnome, fortress.getFloorPlan()[4][6].getGnome());      // check Gnome has moved to correct Tile
        assertEquals(gnome.getTile(), fortress.getFloorPlan()[4][6]);       // check Gnome has new Tile associated with it
    }

    @Test
    public void isNorthWalkable() {
        Tile tile = fortress.getFloorPlan()[2][1];
        tile.setGnome(gnome);

        assertTrue(fortress.isNorthReachable(tile).isWalkable());
    }

    @Test
    public void isSouthWalkable() {
        Tile tile = fortress.getFloorPlan()[2][1];
        tile.setGnome(gnome);

        assertTrue(fortress.isSouthReachable(tile).isWalkable());
    }

    @Test
    public void isWestWalkable() {
        Tile tile = fortress.getFloorPlan()[1][1];
        tile.setGnome(gnome);

        assertTrue(fortress.isWestReachable(tile).isWalkable());
    }

    @Test
    public void isEastWalkable() {
        Tile tile = fortress.getFloorPlan()[2][1];
        tile.setGnome(gnome);

        assertTrue(fortress.isEastReachable(tile).isWalkable());
    }

    @Test
    public void northNotWalkable() {
        Tile tile = fortress.getFloorPlan()[1][0];
        tile.setGnome(gnome);

        assertFalse(fortress.isNorthReachable(tile).isWalkable());
    }

    @Test
    public void southNotWalkable() {
        Tile tile = fortress.getFloorPlan()[1][0];
        tile.setGnome(gnome);

        assertFalse(fortress.isSouthReachable(tile).isWalkable());
    }

    @Test
    public void westNotWalkable() {
        Tile tile = fortress.getFloorPlan()[2][1];
        tile.setGnome(gnome);

        assertFalse(fortress.isWestReachable(tile).isWalkable());
    }

    @Test
    public void eastNotWalkable() {
        Tile tile = fortress.getFloorPlan()[2][2];
        tile.setGnome(gnome);

        assertFalse(fortress.isEastReachable(tile).isWalkable());
    }

    @Test
    public void invalidMoveNorth() {
        Tile tile = fortress.getFloorPlan()[0][0];
        tile.setGnome(gnome);

        assertEquals(null, fortress.isNorthReachable(tile));
    }

    @Test
    public void invalidMoveSouth() {
        Tile tile = fortress.getFloorPlan()[8][0];
        tile.setGnome(gnome);

        assertEquals(null, fortress.isSouthReachable(tile));
    }

    @Test
    public void invalidMoveWest() {
        Tile tile = fortress.getFloorPlan()[1][0];
        tile.setGnome(gnome);

        assertEquals(null, fortress.isWestReachable(tile));
    }

    @Test
    public void invalidMoveEast() {
        Tile tile = fortress.getFloorPlan()[0][22];
        tile.setGnome(gnome);

        assertEquals(null, fortress.isEastReachable(tile));
    }
}