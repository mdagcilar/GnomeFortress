package com.procensus.md;

import java.util.ArrayList;
import java.util.List;

class Group {
    private final int GNOME_STRENGTH = 5;
    private int groupId;
    private List<Gnome> gnomeList = new ArrayList<>();

    Group(int groupId, int numGnomes) {
        this.groupId = groupId;

        createGnomes(numGnomes);
    }

    int getId() {
        return groupId;
    }

    List<Gnome> getGnomeList() {
        return gnomeList;
    }

    /**
     * Creates a List of Gnomes with their respective id's and strength values.
     *
     * @param numGnomes - Number of Gnomes to create
     */
    private void createGnomes(int numGnomes) {
        for (int i = 1; i <= numGnomes; i++) {
            gnomeList.add(new Gnome(groupId, i, GNOME_STRENGTH));
        }
    }
}
