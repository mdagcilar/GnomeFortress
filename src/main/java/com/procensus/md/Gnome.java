package com.procensus.md;

class Gnome {
    private int id;
    private int groupId;
    private int strength;
    private Tile tile;

    Gnome(int groupId, int id, int strength) {
        this.groupId = groupId;
        this.id = id;
        this.strength = strength;
    }

    int getId() {
        return id;
    }

    int getGroupId() {
        return groupId;
    }

    int getStrength() {
        return strength;
    }

    Tile getTile() {
        return tile;
    }

    void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Merging two Gnomes together, combining their strength values into this Gnome.
     * Occurs when two Gnomes of the same team meet,
     * they combine into one larger gnome with a
     * strength value equal to the total of those gnomes.
     *
     * @param gnome - the gnome to merge with
     */
    void merge(Gnome gnome) {
        strength += gnome.getStrength();

        gnome.kill(); // kill the Gnome that this Gnome joined with.
    }

    void kill() {
        strength = 0;
        tile.removeGnome();
    }

    boolean isAlive(){
        return strength > 0;
    }
}
