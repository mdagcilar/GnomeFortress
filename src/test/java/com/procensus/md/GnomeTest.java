package com.procensus.md;

import org.junit.Test;

import static org.junit.Assert.*;

public class GnomeTest {

    @Test
    public void getId() {
        Gnome gnome = new Gnome(1, 1, 5);

        assertTrue(gnome.getId() == 1);
    }

    @Test
    public void getGroupId() {
        Gnome gnome = new Gnome(3, 1, 5);

        assertTrue(gnome.getGroupId() == 3);
    }

    @Test
    public void getStrength() {
        Gnome gnome = new Gnome(1, 2, 5);

        assertTrue(gnome.getStrength() == 5);
    }

    @Test
    public void merge() {
        Gnome gnome = new Gnome(1, 1, 3);
        Gnome gnome2 = new Gnome(1, 2, 5);

        // create fake tile for Gnome 2, so the merge method doesn't fail when trying to destroy the Gnome
        Tile tile = new Tile('1', 0, 0);
        gnome2.setTile(tile);

        gnome.merge(gnome2);

        assertTrue(gnome.getStrength() == 8);
    }

    @Test
    public void testIsAlive(){
        Gnome gnome = new Gnome(1, 1, 5);
        assertTrue(gnome.isAlive());
    }

    @Test
    public void testIsNotAlive(){
        Gnome gnome = new Gnome(1, 1, 0);
        assertFalse(gnome.isAlive());
    }

    @Test
    public void testConsumeHealthPotion(){
        Gnome gnome = new Gnome(1, 1, 5);

        gnome.consumeHealthPotion();

        assertEquals(10, gnome.getStrength());
    }

    @Test
    public void testGetTile(){
        Gnome gnome = new Gnome(1, 1, 3);

        Tile tile = new Tile('1', 0, 0);
        gnome.setTile(tile);

        assertEquals(tile, gnome.getTile());
    }

}