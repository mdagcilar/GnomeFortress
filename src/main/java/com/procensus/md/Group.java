package com.procensus.md;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final int GNOME_STRENGTH = 5;
    private int id;
    private List<Gnome> gnomeList = new ArrayList<>();

    Group(int id, int numGnomes) {
        this.id = id;

        createGnomes(numGnomes);
    }

    public int getId() {
        return id;
    }

    List<Gnome> getGnomeList() {
        return gnomeList;
    }

    /**
     * Creates a List of Gnomes with their respective id's and strength values.
     *
     * @param numGnomes
     */
    private void createGnomes(int numGnomes) {
        for (int i = 1; i <= numGnomes; i++) {
            gnomeList.add(new Gnome(i, GNOME_STRENGTH));
        }
    }

    /**
     * Removes a Gnome from the Group
     * @param index
     */
    void removeGnome(int index){
        gnomeList.remove(index);
    }
}
