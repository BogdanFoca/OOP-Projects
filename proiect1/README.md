# Santa Claus is coming to ACS students.

The JSONReader object reads the data from the file in JSON format and creates instances of the corresponding classes with the read parameters.

All of the information is stored in the Database, a singleton object.

Before the start of a new simulation, the Database is cleared.

The simulation is calculated by the Singleton type SimulationManager object.

It initializes a JSONOutput object which stores the output for each year of the simulation.

**In year 0:**
- the budget for each child is calculated;
- a list of outputChildren is created and added to the JSONOutput, and all children in the Database are added to it, converted to OutputChild;
- gifts are distributed to the children;
- those who have become adults are removed;
- the children from outputChildren are added to the JSONOutput.

**In each subsequent year:**
- the budget for each child is calculated;
- the Database is updated with the information from the current year's JSONReader
    - each child becomes one year older and if they become adults they are removed from the Database;
    - the new nice score and gift preferences are added to the children; new children who are not adults are added;
    - a list of outputChildren is created and added to the JSONOutput, and all children in the Database are added to it, converted to OutputChild;
- gifts are distributed to the children;
- those who have become adults are removed;
- the children from outputChildren are added to the JSONOutput.
    
The information from the JSONOutput is written to a file.
