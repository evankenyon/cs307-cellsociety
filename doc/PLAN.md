# Cell Society Design Plan
### Team Number
4
### Names


## Design Overview


## Design Details

Here is a graphical look at my design:

![This is cool, too bad you can't see it](images/online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).


## Design Considerations

#### Design Issue #1

An important design issue that we ran into was which class should have the responsibility of knowing a
cell's neighbors. Specifically, we debated whether to have that responsibility inside of
our GridMatrix class or inside of our Cell class. The pros for having it in our GridMatrix class
was that it would fit well with having a 2D array of cell locations, since the math for rectangular
cell neighbors is fairly simple. Additionally, the Cell class and GridMatrix classes would be 
simpler, giving us more time to work on other parts of the project. However, the main con was that
this design would be inflexible to different shaped grids. Since more requirements can be added,
we thought that one potential requirement we should be ready for would be different shaped grids
(since triangle grids and hexagonal grids could both be used for apps like this). This was the main 
pro to shifting the responsibility of knowing a Cell's neighbors to the Cell class itself. The con
to this was increasing the amount of code in both the GridMatrix and Cell classes (at least We 
believe that this will happen) and decreasing efficiency, however even though we would have more
code and lower efficiency, that is not as important as the flexibility of the design. Therefore,
we have decided to put the responsibility of knowing a Cell's neighbors in the Cell class.


#### Design Issue #2

 * Alernative #1

 * Alernative #2

 * Trade-offs



## User Interface

Here is our amazing UI:

![This is cool, too bad you can't see it](images/29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).


## Team Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3

 * Team Member #4


#### Proposed Schedule
