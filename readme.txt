Laboratory Manager:

Manage a list of labs and log lab activities. View details by clicking on lab entries in scroll panel.


UBC Software Construction Personal Project Weekly Demo:

11. GUI/Visuals
(referred to SmartHomeUI for tab creation)
3 tabs on frame: Main, Labs, Log
added functional load, save, add, start, delete, view, and clear buttons
on the LabsTab, added scrollpane that displays available labs, and shows lab contents on double click
on the LogTab, added scrollpane that displays lab activity (logged after a lab has been started and completed by clicking Start on LabsTab)

10. Design Patterns and the Web
retrieved data from CORE EVIDENCE journal with API

9. Principled Code
Refactored private methods for duplicated code

8. Associations and Data Relationships
Added material class and implemented/tested reflexive relationship with lab
Delegated the implementation of handle-methods from LabAppManager(refactored name from LabAppHandlers) to LabHandlers

7. Added view and clear lab history functions 

6. Robustness
changed if-statements to exceptions
added finally to handleDeleteLab try-catch
added tests for exception-catching of viewLabs

5. Abstracts and Extends
Refactored Lab to abstract class, and added SafeLab and DangerousLab as subclasses
Refactored handler methods into GeneralHandlers abstract class, with subclasses LabAppHandlers and LabSimulation
Did not implement LabSimulations yet

4. Types, interfaces and Saving
added 2 interfaces: Loadable and Savable (referred to FileReaderWriter)
implemented Loabable and Savable to LabAppHandlers
added tests for save and load

3. Abstraction and Testing
specified methods with REQUIRES/MODIFIES/EFFECTS clauses
added more implementations (referred to FitnessGymKiosk for analyzeInput and handleUserInput)
tested for Quit in LabAppHandlers
tested for Lab and LabEntry constructors

2. Basic Interaction
(referred to LoggingCalculator's LogEntry for LabEntry)
(referred to FrontDeskKiosk's Fitness Class for making lab entries)
Make at least 1 package: ui
Make at least one class named appropriately for your application
Make a main method that gets things going
Construct an object (Note: this should probably happen within your main method)
Declare a field and use it (not necessarily at the same time!)
Pass a parameter
Use a local variable
Return a value and use it
Use a condition, and a loop
Make a call graph from your main method
Draw the flowchart for a method with a condition or a loop
Get user input, and reflect it back to the user
Use the debugger

1. Get Started
package
class
main (psvm)
methods
print statements
call the methods
