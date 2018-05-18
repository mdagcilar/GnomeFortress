Gnome Fortress
==============

Assumptions
-----------
* The program will run until their is a lone victor or a one team remaining of multiple victorious Gnomes.
* The floor plan can be edited and different dimensions are supported. However, it is left to the
editor of the floor plan to ensure that their are no unreachable corridors.
* If there is a Gnome adjacent to the moving Gnome, the Gnome will always prioritise combining or fighting than moving randomly away.
* If two Gnomes strengths are equal, the attacking Gnome has advantage and kills the other.
* When Gnomes are placed randomly at the beginning of the battle, Gnomes are not placed on the same tile as another Gnome.

Additional features implemented:
--------------------------------
* Strength potions
* Bombs
* Editable floor plans

How to run program:
-------------------
The code was written in Java as a maven project in IntelliJ.
* 1- Start IntelliJ
* 2- Open project and locate to project folder
* 3- Edit Run Configuration and add program arguments or simply run 'Starter.main()' and enter arguments through standard input
* 4- Program should simulate the battle and end with a victorious team

* JUnit tests can be found in GnomeFortress_test_metin\src\test\java\com\procensus\md\

How to edit the floorplan:
-------------------
* 1- Locate file in GnomeFortress_Metin/resources/floorplan_original
* 2- Edit file to the desired new layout
* 3- Run 'Starter.main()'
* 4- Should see when the program prints out to the console that the floor plan has changed

