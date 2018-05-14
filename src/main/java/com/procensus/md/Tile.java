package com.procensus.md;

import java.util.List;

class Tile {

    private char character;
    private int row, column;

    Tile(char character, int row, int column) {
        this.character = character;
        this.row = row;
        this.column = column;
    }

    char getCharacter() {
        return character;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "character=" + character +
                ", row=" + row +
                ", column=" + column +
                '}';
    }

    // Returns true if the tile is a corridor tile. i.e. Walkable
    boolean isWalkable() {
        return character != '#';
    }

    // Returns true if the tile is a Gnome
    boolean isGnome() {
        return character != 'G';
    }

    void addGnome() {
        character = 'G';
    }

    void removeGnome() {
        character = ' ';
    }
}

