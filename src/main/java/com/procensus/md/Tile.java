package com.procensus.md;

class Tile {

    private Character character;
    private int row, column;
    private Gnome gnome;

    Tile(char character, int row, int column) {
        this.character = character;
        this.row = row;
        this.column = column;
    }

    char getCharacter() {
        return character;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    String getCoordinates() {
        return "(" + getRow() + "," + getColumn() + ")";
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
        return Character.isDigit(character);
    }

    // Returns true if the tile is a BOMB 'b'
    boolean isBomb() {
        return character == 'b';
    }

    // Returns true if the tile is a health potion 'h'
    boolean isHealthPotion() {
        return character == 'h';
    }

    // moves the Gnome character on the map
    void moveIndicator(Tile oldLocation, int groupId) {
        oldLocation.character = ' ';
        character = (char) (groupId + '0');
    }

    Gnome getGnome() {
        return gnome;
    }

    void setGnome(Gnome gnome) {
        this.gnome = gnome;
        character = (char) (gnome.getGroupId() + '0');
    }

    void removeGnome() {
        gnome = null;
        character = ' ';
    }
}

