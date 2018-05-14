package com.procensus.md;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    @Test
    public void getCharacter() {
        Tile tile = new Tile('#', 0, 0);
        assertTrue(tile.getCharacter() == '#');
    }

    @Test
    public void isWalkableCorridor() {
        Tile tile = new Tile(' ', 0 ,0);
        assertTrue(tile.isWalkable());
    }

    @Test
    public void isWalkableGnome() {
        Tile tile = new Tile('G', 0 ,0);
        assertTrue(tile.isWalkable());
    }

    @Test
    public void isWalkableSpecialChar() {
        Tile tile = new Tile('x', 0 ,0);
        assertTrue(tile.isWalkable());
    }

    @Test
    public void notWalkable() {
        Tile tile = new Tile('#', 0, 0);
        assertFalse(tile.isWalkable());
    }
}