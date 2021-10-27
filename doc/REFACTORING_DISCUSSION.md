## Lab Discussion
### Team
4
### Names
Evan Kenyon, Remy Cross, Keith Cressman, Tarun Amasa


### Issues in Current Code
Exception handlers need to preserve the original exceptions. This is an issue in SimGenerator at lines 62, 65, 37, and 40, in Model at lines 91 and 98, at lines 123 and 126 in FileSavePopup, at line 31 in ViewResourceHandler, at line 39 in Controller, in SimulationDisplay at lines 83 and 201, and at line 161 in MainView.

Another issue is that classes should not be coupled to too many other classes. This is an issue for Controller, SimulationDisplay, FileSavePopup, and MainView.

Another issue is that magic values should not be used in SimulationDisplay as lines 112 and 113, in SegregationRules at line 11, in Cell at lines 47, 34, and 53, in Main at line 19, and in PredatorPreyRules at line 9.

#### Method or Class
Class: Controller
* Design issue
  Coupled with too many other classes

* Design issue
  Has an exception handler that is not preserving the original exception

#### Method or Class
Class: SimulationDisplay

* Design issue
  Has several issues with exception handlers not preserving the original exceptions.

* Design issue
  Uses magic values at lines 112 and 113 that need to be extracted to a .properties file.


### Refactoring Plan

* What are the code's biggest issues?
  The code’s biggest issues are the classes that are coupled to too many other classes (Controller, MainView, FileSavePopup, and SimulationDisplay). Also, there are quite a few protected variables and methods that should be changed to private in SimulationDisplay, ChangeableDisplay, CornerLocationGenerator, and Rules. Also, very few exceptions are being properly handled, and those that are are being handled. Finally, in general, classes need to be more active.

* Which issues are easy to fix and which are hard?


##Easy

Changing variables and methods to private instead of protected.

Make certain variables private final static instead of just private final (error occurs in many classes)


Preserve original exceptions (error in many classes, lots in view package)


In simulationDisplay, make oneStepButton a local variable instead of instance variable (finished)


Remove Cell[][] with a more flexible data structure

Shouldn’t need Node in the Model class (just need to delete getCellDisplays())





##Hard(er)
Single responsibility: a lot of the classes in the View package are coupled (MainView, SimulationDisplay, FileSavePopup, and Controller)

Use reflection for methods



* What is your plan to implement the changes without losing control of the process?


### Refactoring Work

* Issue chosen: Fix and Alternatives
    * Single responsibility:
        * Controller class does not need a MainView instance variable, so we can remove this (done)


* Issue chosen: Fix and Alternatives
* Exception handling:
* Just need to change exceptions to proper exceptions, and then add specific information based on what’s going wrong. Also, need to handle properly (i.e. instead of printing stack trace, we should show the user an error message on the frontend to explain the problem and why it occurred).
