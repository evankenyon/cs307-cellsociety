# Cell Society Design Final

### Names

Evan Kenyon, Remy Cross, Tarun Amasa, Keith Cressman

## Team Roles and Responsibilities

* Evan Kenyon

  * Overall, my role on the team was mostly related to the Controller, the classes it used for parsing,
and the backend. I spent the most time on the Controller and the classes it uses (except for
DefaultCSVParser) and reflection across our entire project. I also took the lead on implementing
neighbor arrangements, being able to update neighbor arrangement and shape from the frontend, being
able to set neighbor arrangement, edge policy, and shape through .sim file keys, and being able to
change the type of CSV used (i.e. we allow for one that uses probabilities and one that uses raw
numbers). Also, I did most of the exception handling during the last week (although another member,
Keith, did most before that). Also, I took the lead on refactoring our classes to better embody the
encapsulation design principle.

* Keith Cressman
  * I was responsible for the front end. I wrote nearly everything in the view package, although Evan contributed to the CellGridDisplay class as well. I also wrote almost the entirety of the resourceHandlers package, as the front end code uses LanguageResourceHandler.java and ViewResourceHandler.java to get various parameters for the display. 

* Remy Cross
  * I was responsible for the logic in the Rules classes. While Evan did a lot of refactoring to add reflections to the Rules classes, I was the one that set up the initial logic that the reflections were adapted from. Furthermore, I was responsible for generating the cell shapes for display through the CornerLocationGenerator package. I also assisted on a lot of the backend logic. 

* Team Member #4

## Design goals
One of our major design goals was to make our code as extendable as possible for future simulations to be added. One of the specifications of this project was that there were going to be major changes implemented to the project. Because of this, we made it very simple to add new simulations with different Rules. By implementing reflections in many parts of our code, it also made it very easy to add new variations of different features. We sought to make it as easy as possible to make new types of simulations. Using our design, once we finished GameOfLife, it was very easy to create the other games because it was just a matter of adding in a new Rules subclass.

#### What Features are Easy to Add
One feature that was especially easy to implement based on how we designed our code was changing the shape of the cells. Rather than doing convoluted math to figure out where the neighbors would be in a grid for each cell, we calculated the corners for each cell based on the i and j index of the cell. This made it simple to implement different shaped cells because we just changed the algorithm used to calculate the corners. Furthermore, it made it easier to change our definition of "neighbor" because it was just based on how many corners they shared. Another aspect of the code that was very easy to implement was new types of simulations. Because the Rules of the simulations were abstracted from the model, it made it easier to change which rules we would adhere to. We would need to create a new class that contains the rules for the specific simulation and then initialize that class specifically in order to have that game run. The way that we designed our code really made it easier to extend our project further than the initial specifications. Our code is extremely flexible and adaptable to the user's preferences. 

## High-level Design
  * An instance of MainView is instantiated by Main.java when starting the program. MainView is responsible for organizing all of the front end components. It has a method which creates the window, along with options to change some basic settings like language/style, and components to upload a .SIM file or start a new window to view another simulation. When a user uploads a file, MainView uses it to create a new SimulationDisplay, which is responsible for managing the view components associated with a single simulation.

  * SimulationDisplay’s purpose is to represent the frontend for a single simulation (including its
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

  * SettingsDisplay is responsible for the various settings that the user can control from the frontend.
It uses Controller to change cell neighbor arrangement and cell edge policy based on user input.
Besides that, it uses SimulationDisplay to show/hide various frontend components when the user
desires and to step through the frontend side of things. It also is internally responsible for the
pause/resume button and for the FileSavePopup.

  * CellGridDisplay is responsible for representing/organizing the display for the grid in the simulation. SimulationDisplay has an instance of this class.
  * HistogramDisplay is responsible for representing/organizing the display for the histogram of cell states. SimulationDisplay has an instance of this class.
  * InfoDisplay is responsible for representing/organizing the display for the grid in the simulation. SimulationDisplay has an instance of this class.

  * ChangeableDisplay is an abstract class extended by MainView, SimulationDisplay, CellGridDisplay, HistogramDisplay, InfoDisplay, SettingsDisplay, and FileSavePopup. This class uses the classes in view.ViewUtilities in its methods allowing for one line creation of buttons with associated action, labels, and nodes with drop down boxes, all of which can be easily changed to display in another language. The classes in view.ViewUtilies just create an interface called NodeWithText, implemented by Button2 and Label2, which we used in ChangeableDisplay to have a collection of nodes, the language of which could be easily changed. 
    * ChangeableDisplay also uses the resourceHandlers.LanguageResourceHandler.java to control the language of the text on the nodes. LanguageResourceHandler is responsible for going through properties files to get the text for a given node, in the correct language. 
  * ViewResourceHandler is responsible for going through properties files to get various display parameters, like with the width of the window. Several classes in the view package use an instance of ViewResourceHandler to get parameters for the display. 


  * FileSavePopup uses the Controller to save the simulation to files based on the current state and
user input (ex. the user can title the simulation whatever they want).

  * CellDisplay is responsible for the front end of individual cells. It uses the Controller to update
cell states based on user input (and ImmutableCell to get the Cell’s info in order to update the
Cell that it should).

  * Controller is responsible for the interactions between Model and the frontend. Controller’s uses
SimParser (responsible for parsing sim files) and CSVParser (responsible for parsing CSV files) to
extract the necessary information from the user uploaded files in order to initialize the Model. It
also uses SimGenerator and CSVGenerator when the saveFile is called. It uses Model in all of its
functions, whether that be to update the model based on user input or to get model information for
the user to manipulate or see.

  * Model is responsible for updating the Cells based on the desired Rule. Its public methods are called
through Controller in order to initialize all the Cells, update all Cell states and neighbor states,
change the shape of the Cells, update the neighbor arrangement, set the relevant simulation info,
get its number of rows, columns, and a cell at a particular location, and to find the next state for
each Cell based on the desired Rule. It uses the Cell class for all of these, since its purpose is
to update Cells, and the Rule subclasses for finding the next state for each Cell.

  * The Rules classes are responsible for changing Cell states based on their neighbors. The different
subclasses offer a wide variety of different implementations that show how flexible the Rules parent
class is made to be. This is done by using the Cell classes public methods to get the information
necessary to figure out how to update the Cell (ex. its number of neighbors with state 1 for
GameOfLifeRules).

  * The Cell class is responsible for representing a cell in any given simulation. It uses the
CornerLocationGenerator classes to generate its corners. Model calls its functions in order to
change its shape, get its location within the grid, update its state to its future state, set/get
its future and current states, and update its neighbors.

  * The CornerLocationGenerator classes are responsible for generating corners of a Cell based on its i
and j indices as well as the total number of rows and columns of the simulation. The different
implementations are used for different shapes.

  * CSVGenerator is responsible for generating a CSV based on the current state of all Cells in the
model, and this is done through the Controller (by being called on through FileSavePopup).
SimGenerator is responsible for generating a Sim file based on user input and simulation
information (and it is used through the same pathway as CSVGenerator).

## Assumptions that Affect the Design

#### Features Affected by Assumptions

## Significant differences from Original Plan
One of the most significant changes from our initial plans was the data structure we used to hold the cells themselves.  Initially, the model had held a 2 dimensional array of cell objects, and the indexing of each cell was done by its position in the 2-d array.When adding more functionality, we figured out that this structure was burdensome and gave away too much data. In order to operate on one cell, we would need to pass the entire matrix, leaving the rest of the cells open for modification. Therefore in order to keep the data protected as much as possible, we changed the entire structure of this data. We modified the functionality of parsing the CSV file to construct each cell to also have an iIndex and jIndex attribute. Then, we modified the model to hold a list of cells instead of a 2d array. The location of the cells was unknown to the model, leaving the cells hidden.

Another significant change to functionality was how we determined the neighbors of cells. In the early stages of the project, we resolved to use the index of the cells to determine the neighbors. Then, we would use math to add and subtract from the indexes to find the respective neighbors of each cell. With this implementation, the neighbors of a cell were determined within the model itself. In this implementation, the neighbors were determined within the model itself. This worked relatively simply for squares, as a square grid fit neatly with the implementation of a 2d array. However, we quickly realized that this implementation would break down for other types of neighbors. We later changed our neighbor logic to use the view itself, where the visualization of the corners of the cell objects would determine if cells were neighbors or not. The switch of logic from using the model to using the view to determine the neighbors ended up paying off for the other shapes, and led to a more robust design of finding neighbors.




## Describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline
Our group implemented all the backend features laid out in the specifications, but did not get to all of the front end features. Listed below is how to implement those front end features.
  * About button
    * When the user presses the about button, it currently does nothing. To make it work, the showAbout() method in SettingsDisplay.java should create a popup and use myController to get info about the state of the simulation. Ideally, when myController.parseFile() is called, the controller should store a String describing the simulation, just by filling in a template like "Author: %s \n Simulation Type: %s ..." with values from the Sim File, and then store that as an instance variable to be retrieved when the About button is pressed.
  * Change the color/image on the cell's display
    * The first thing to add on would be giving users the option to do this on the GUI. Within view.SettingsDisplay.java, in the createSettingsDisplay() method, a Node should be added to that VBox with components allowing the user to select a state number (from drop down box) and a color or image file to set as the display for that state. 
    * Next, within CellDisplay.java, there should be two new methods: changeColor(Color c){myDisp.setFill(c);} and changeImage(Image i){myDisp.setFill(new ImagePattern(i));}. 
    * CellDisplay.java should have a map that maps state to a lambda function calling either changeColor or changeImage, so that when the Cell Display changes state, it changes the color/image on the node. Thus, line 158 on the changeState method would be replaced by calling the method from the aforementioned map corresponding to the state argument.
    * Finally, when the user uses the GUI to submit their change of color/image for a given state, the SimulationDisplay should pass the state number and color/image to a method in the CellGridDisplay class that will loop through each CellDisplay and call a method changing the CellDisplay's map entry for the given state so that it now maps that state to a function changing the color/image appropriately.
  * Finish up the Info Display (include name and image/color for each state)
    * To get the name for each state, there should be a properties file that has a key for each SimulationType with a corresponding value of names for each state, delimited by some string. Then, the createInfoDisplay() method could take in an argument that is a String representing the simulation type, and use that in the makeColumnForName() method to get the state names in the properties file. For the display of each state, it could instantiate a new CellDisplay with the appropriate state in the makeColumnForDisplay() method.

  
  Here's how to add some other features
  * New language:
    * Within the data folder, there is a resource bundle called 'Language'. You would need to add a new file for the new language. Then, within resourceHandlers.LanguageResourceHandler.java you would need to add a new entry to the langToLocale map that has a key like "Portuguese" or any other desired language. 
  * New styling (CSS):
    * Within view.CSSFiles, you will need to add the new CSS file. Then, in view.MainView.java, you will need to add on to the setUpStyleNameToPathMap() method so that it also adds an entry with a key that gives the name of the style, and an associated value that is a path to the CSS file. 



