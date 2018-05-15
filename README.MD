Gnome Fortress
==============

Assumptions
-----------
* The program will run until their is a lone victor or a single group remaining of multiple Gnomes.
* The floor plan can be edited and different dimensions are supported. However, it is left to the
editor of the floor plan to ensure that their are no unreachable corridors.
* Gnomes take turns to move 1 space at a time.
* If two Gnomes from different teams encounter each other, with the same strength 
* When the gameManager initialises and places Gnomes randomly, Gnomes cannot be placed on the same Tile as another Gnome.
* If there is an adjacent Gnome, the Gnome will always prefer to fight because they're crazy creatures.
* If two Gnomes strengths are equal, the attacking Gnome has advantage and kills the other.


    gnome 1 from team 2, gnome 2 from team 2 have met at (2,4) and combined into a strength 5 gnome.
    gnome 1 from team 2, gnome 4 from team 3 have fought at (7,3) and gnome 1 from team 2 was victorious.

Some ideas:

* Breakable walls.
* Loot / Strength potions.
* One way corridors.
* Teleport tile pairs.
