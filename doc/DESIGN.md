# Cell Society Design Final

### Names

Evan Kenyon, Remy Cross, Tarun Amasa, Keith Cressman

## Team Roles and Responsibilities

* Evan Kenyon

Overall, my role on the team was mostly related to the Controller, the classes it used for parsing,
and the backend. I spent the most time on the Controller and the classes it uses (except for
DefaultCSVParser) and reflection across our entire project. I also took the lead on implementing
neighbor arrangements, being able to update neighbor arrangement and shape from the frontend, being
able to set neighbor arrangement, edge policy, and shape through .sim file keys, and being able to
change the type of CSV used (i.e. we allow for one that uses probabilities and one that uses raw
numbers). Also, I did most of the exception handling during the last week (although another member,
Keith, did most before that). Also, I took the lead on refactoring our classes to better embody the
encapsulation design principle.

* Team Member #2

* Team Member #3

* Team Member #4

## Design goals

#### What Features are Easy to Add

## High-level Design

SimulationDisplay’s purpose is to represent the frontend for a single simulation (including its
settings, saving the simulation as a file, etc) and to display the various frontend components
involved in that representation. It directly interacts with several classes, specifically all of the
concrete frontend classes except for CellDisplay and MainView. It uses the Controller to parse
uploaded sim files and make the backend “step” (i.e. update the cells). It uses the CellGridDisplay
to get the number of cells with each state in order to update HistogramDisplay and InfoDisplay.
CellGridDisplay is responsible for maintaining the CellDisplays that make up the frontend CellGrid
that is shown to the user. It interacts with Controller to set up the cell displays basic
information, as well as to add an observer to the relevant Cell objects so that the changeState
function for the CellDisplay will be called when the Cell object is an observer of changes state. It
is also used when the user wants to change the cell shape.

SettingsDisplay is responsible for the various settings that the user can control from the frontend.
It uses Controller to change cell neighbor arrangement and cell edge policy based on user input.
Besides that, it uses SimulationDisplay to show/hide various frontend components when the user
desires and to step through the frontend side of things. It also is internally responsible for the
pause/resume button and for the FileSavePopup.

FileSavePopup uses the Controller to save the simulation to files based on the current state and
user input (ex. the user can title the simulation whatever they want).

CellDisplay is responsible for the front end of individual cells. It uses the Controller to update
cell states based on user input (and ImmutableCell to get the Cell’s info in order to update the
Cell that it should).

Controller is responsible for the interactions between Model and the frontend. Controller’s uses
SimParser (responsible for parsing sim files) and CSVParser (responsible for parsing CSV files) to
extract the necessary information from the user uploaded files in order to initialize the Model. It
also uses SimGenerator and CSVGenerator when the saveFile is called. It uses Model in all of its
functions, whether that be to update the model based on user input or to get model information for
the user to manipulate or see.

Model is responsible for updating the Cells based on the desired Rule. Its public methods are called
through Controller in order to initialize all the Cells, update all Cell states and neighbor states,
change the shape of the Cells, update the neighbor arrangement, set the relevant simulation info,
get its number of rows, columns, and a cell at a particular location, and to find the next state for
each Cell based on the desired Rule. It uses the Cell class for all of these, since its purpose is
to update Cells, and the Rule subclasses for finding the next state for each Cell.

The Rules classes are responsible for changing Cell states based on their neighbors. The different
subclasses offer a wide variety of different implementations that show how flexible the Rules parent
class is made to be. This is done by using the Cell classes public methods to get the information
necessary to figure out how to update the Cell (ex. its number of neighbors with state 1 for
GameOfLifeRules).

The Cell class is responsible for representing a cell in any given simulation. It uses the
CornerLocationGenerator classes to generate its corners. Model calls its functions in order to
change its shape, get its location within the grid, update its state to its future state, set/get
its future and current states, and update its neighbors.

The CornerLocationGenerator classes are responsible for generating corners of a Cell based on its i
and j indices as well as the total number of rows and columns of the simulation. The different
implementations are used for different shapes.

CSVGenerator is responsible for generating a CSV based on the current state of all Cells in the
model, and this is done through the Controller (by being called on through FileSavePopup).
SimGenerator is responsible for generating a Sim file based on user input and simulation
information (and it is used through the same pathway as CSVGenerator).

## Assumptions that Affect the Design

#### Features Affected by Assumptions

## Significant differences from Original Plan

## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done


