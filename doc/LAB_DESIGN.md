# Cell Society Design Discussion
### Team Number 4
### Names Tarun Amasa, Remy Cross, Keith Cressman, Evan Kenyon


# Class: Cell

**Responsibilities:**



**Collaborators**:



Reference Variables:

Private int currentState

Private int futureState



**Methods**

Public cell(i,j, initialState){



}



Public void setFutureState(int state){

this.futureState = futureState

}



Public void updateCurrentState(){

this.currentState = futureState

}




# Class: GridMatrix (Model)

**Responsibilities**:

-   Maintain a 2d array/grid of the cells in the program.

-   At each time step, loop through each cell in the grid so that it is updated to have the correct state at the next time step




**Collaborators**:

-   Has a collection of Cells

-   Has a Rule


Reference Variables:

Cell[][] cellGrid;




**Methods**

Void setCellGrid(Cell[][]cellGrid){



}

cellGrid getCellGrid(){

Return this.cellGrid

}

List<Cell> getNeighbors(int i, int j) //return a list of the cells neighboring the cell at [i, j]

Void findNextStateForEachCell() //loop through each cell, set its future state

Void setNextStateForEachCell() //loop through each cell, set its current state to future state, calls updateCurrentStateMethod






# Class: Parser

**Responsibilities**: Parses .csv file



**Collaborators**:



**Reference Variables:**

Csv csvFile

Methods



setCsv(CSV csvFile){

this.csvFile = csvFile

}



[][]Cell createCellArray(){



//creates a cellArray

return cellGrid

}







# Class: Rule (Abstract Parent)

**Responsibilities:**
Keep track of each specific rule as it pertains to the game



**Collaborators:**



**Reference Variables:**




**Methods**





# Class: Rule

**Responsibilities:**
Keep track of specific rules as it pertains to the game



**Collaborators:**
Cell

Takes in a cell and returns a state based on the rules



Reference Variables:

Cell c




**Methods**

RuleForXNeighbors{}--> different rules depending on the rules of the game, dependent on number of neighbors and state of neighbors





# Class Controller





**Reference Variables:**

csvFile csvFIle



**Methods**



setCSVFile(){



}




loadFile()

Parser parser = new parser()

parser.setCsv(csvFile)

Cell[][] currentCellGrid = parser.createCellGrid()

model.setCellGrid(currentCellGrid)



}



createCSV(){

csvGenerator = new csvGenerator()

csvGenerator.createCSV(model.getCellGrid())



}





# Class: MainView

**Responsibilities:**

Hold a stage, where we call stage.setScene() when changing to a new game or going back to the home screen.

Collaborators:



**Reference Variables:**

Void changeGame()

Void setSceneGameOfLife()

Void setSceneFire()



**Methods**

Scene void makeHomeScreen()

void changeScene()



Abstract Class: GameView

**Responsibilities:**




**Collaborators: Controller**



**Reference Variables:**

Model model; //Model class might be called GridMatrix

Rectangle[][] cellGridDisplay; //could be a gridPane instead



**Methods**

Public Scene makeScene()

void fillCellGridDisplay() //use the controller to get the size of the grid and what to display in each one

void readFile() //call a method of the controller to read the file and make an appropriate grid

void startAnimation()

void pauseAnimation()

createCSVbutton()

uploadFileButton()






# Class: GameOfLifeView extends GameView

**Responsibilities**:



Collaborators: Controller



**Reference Variables:**

Controller controller;




**Methods**

### Discussion Questions

 * How does a Cell know what rules to apply for its simulation?

 * How does a Cell know about its neighbors?

 * How can a Cell update itself without affecting its neighbors update?

 * What behaviors does the Grid itself have?

 * How can a Grid update all the Cells it contains?

 * How is configuration information used to set up a simulation?

 * How is the GUI updated after all the cells have been updated?


### Alternate Designs

#### Design Idea #1

 * Data Structure #1 and File Format #1

 * Data Structure #2 and File Format #2


#### Design Idea #2

 * Data Structure #1 and File Format #1

 * Data Structure #2 and File Format #2


#### Design Idea #3

* Data Structure #1 and File Format #1

 * Data Structure #2 and File Format #2



### CRC Card Classes

This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](images/order_crc_card.png "Order Class")


This class's purpose or value is to represent a customer's order:

|Order| |
|---|---|
|boolean isInStock(OrderLine)         |OrderLine|
|double getTotalPrice(OrderLine)      |Customer|
|boolean isValidPayment (Customer)    | |
|void deliverTo (OrderLine, Customer) | |


This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```
 

### Use Cases

 * Apply the rules to a cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```

 * Move to the next generation: update all cells in a simulation from their current state to their next state
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```

 * Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```
