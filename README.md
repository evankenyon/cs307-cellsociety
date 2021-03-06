Cell Society
====

This project implements a cellular automata simulator.

Names:
Jeremy Cross
Tarun Amasa
Evan Kenyon
Keith Cressman

### Timeline

Start Date: October 13

Finish Date: November 1

Hours Spent: 175

### Primary Roles
Remy- Backend: Logic for Rules class, cell shapes
Tarun- Backend: Model, CSV parser, Cell
Keith- FrontEnd
Evan- Sim parser and generator, controller

### Resources Used
https://medium.com/@mglover/java-fx-testing-with-testfx-c3858b571320
https://www.w3schools.com/java/java_lambda.asp
https://www.callicoder.com/javafx-css-tutorial/
http://tutorials.jenkov.com/javafx/imageview.html
https://www.redblobgames.com/grids/hexagons/

### Running the Program

Main class: Main

Data files needed: 
* KeyAlternatives.properties
* NumCorners.properties
* ValuesAlternatives.properties
* CSSids.properties
* DisplayProperties.properties
* EdgePolicies.properties
* NeighborArrangements.properties
* ShapeCornerGenerator.properties
* StateColor.properties
* GameOfLifeRules.properties
* PercolationRules.properties
* PredatorPrey.properties.properties
* PredatorPreyDefaultParams.properties
* SegregationDefaultParams.properties
* SpreadingOfFireDefaultParams.properties
* SpreadingOfFireRules.properties

Features implemented:
* All games implemented
  * GameOfLife
  * SpreadingOfFire
  * Segregation
  * Percolation
  * PredatorPrey
* Neighbor Arrangements
  * Cardinal
  * Complete
  * Strictly one corner
* Edge Policies
  * Finite
  * Toroidal
  * Mirror
* Grid Location Shapes
  * Square
  * Triangle
  * Hexagon
* Views
  * Grid of Cells
  * Histogram
  * Info Display
* Simulation Values
  * Grid Properties
* Initial Configuration
  * Cell States for grid location
  * Probabilities
  * Raw Number Configuration
* Style
  * Grid Properties
  * Language
  * Color Style


### Notes/Assumptions

Assumptions or Simplifications:
 * In cellsociety.view.CellGridDisplay, I assume that the argument newShape is a String corresponding to a valid shape, i.e. "Rectangle", "Triangle", or "Hexagon". Currently in our code, those are the only possibilities, but if the code was extended for other purposes, people could misuse this method
 * Assumes that cells are going to be colored, not using images

How potential errors are handled:
* required value not given

Propagated from SimParser to frontend, frontend displays an error message that is contained in a
properties file.

* invalid value given (like a non-existent simulation, edge policy, neighborhood arrangement, or negative percentage)

Propagated from SimParser to frontend, frontend displays an error message that is contained in a
properties file.

* invalid cell state values given

Propagated from CSVParser to frontend, frontend displays an error message that is contained in a
properties file.

* cell locations given that are outside the bounds of the grid's size 

Propagated from CSVParser to frontend, frontend displays an error message that is contained in a
properties file.

* empty, badly formatted, or non-CSV files given

Propagated from CSVParser to frontend, frontend displays an error message that is contained in a
properties file.

Added Key Names for Config Files (all of these are optional):
* EdgePolicy (can equal Toroidal, Finite, or Mirror)
  * Default is Finite
* Shape (can equal Triangle, Hexagon, or Rectangle)
  * Default is Rectangle
* NeighborArrangement (can equal Corner, Cardinal, Edge)
  * Default is Edge
* CSVType (can equal Probability, RawNums, or Default)
  * Default is Default

CSV Type Formats:
* Probability is of the format cols,rows,probabilityStateOne,probabilityStateTwo
  * probabilityStateZero = 1 - probabilityStateOne - probabilityStateTwo
* RawNums is of the format cols,rows,numStateOne,numStateTwo
  * numStateZero = cols*rows - numStateOne - numStateTwo

Interesting data files:
* fire/bad.csv
* fire/basic.csv
* fire/probabilityAllTwo.sim (/csv)
* fire/rawNumsHalfandHalf.sim (/csv)
* game_of_life/invalidDimension.sim (/csv)
* game_of_life/rawNumsHalfAndHalf.sim (/csv)
* game_of_life/probabilityAll.sim (/csv)

Known Bugs:
* In segregation, some of the cells spontaneously disappear
* If the grid is made into a rectangle instead of a square, there are issues with triangle and hexagon cell shape
* Can't dynamically set edge policy from frontend

Noteworthy Features:
The games work for the most part besides a couple of glitches. The view has some nice features like changing the color of the window, the shape of the cell, and the animation speed. It also has a histogram of cell states and an info display.

### Impressions
The logical aspects of this project were actually enjoyable to program. We used a lot of the skills that we learned in class to improve the design of our code like lambdas and reflection in the rules classes. For many of the rules, rather than having many if statements, the reflections helped simplify our logic.


