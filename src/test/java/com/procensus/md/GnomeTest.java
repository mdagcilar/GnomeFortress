package com.procensus.md;

import org.junit.Test;

import static org.junit.Assert.*;

public class GnomeTest {

    @Test
    public void getId() {
        Gnome gnome = new Gnome(1, 5);

        assertTrue(gnome.getId() == 1);
    }

    @Test
    public void getStrength() {
        Gnome gnome = new Gnome(2, 5);

        assertTrue(gnome.getStrength() == 5);
    }

    @Test
    public void merge() {
        Gnome gnome = new Gnome(1, 3);
        Gnome gnome2 = new Gnome(2, 5);

        gnome.merge(gnome2);

        assertTrue(gnome.getStrength() == 8);
    }
}