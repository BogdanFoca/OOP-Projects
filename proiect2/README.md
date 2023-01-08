# Santa Claus is coming to ACS students. Part 2

The data from the file is read by the JSONReader object, which reads the data in JSON format and creates instances of the corresponding classes with the read parameters.

For the Child class, a ChildBuilder is used instead of a constructor, with an optional niceScoreBonus parameter.

All of the information is stored in the Database, a singleton object.

Before the start of a new simulation, the Database is cleared.

The simulation is calculated by the Singleton type SimulationManager object.

It initializes a JSONOutput object which stores the output for each year of the simulation.

**In year 0:**
- the budget for each child is calculated;
- a list of outputChildren is created and added to the JSONOutput, and all children in the Database are added to it, converted to OutputChild;
- a Strategy Factory is used to create a strategy for sorting the children by ID;
- gifts are distributed to the children;
- those who have become adults are removed;
- the children from outputChildren are added to the JSONOutput.

**In each subsequent year:**
- a Strategy Factory is used to create a strategy for sorting the children by ID;
- the budget for each child is calculated;
- the Database is updated with the information from the current year's JSONReader
    - each child becomes one year older and if they become adults they are removed from the Database;
    - the new nice score, gift preferences, and new elf are added to the children;
    - new children who are not adults are added;
- a list of outputChildren is created and added to the JSONOutput, and all children in the Database are added to it, converted to OutputChild;
- a Strategy Factory is used to create a strategy for sorting the children according to the strategy for that year;
- gifts are distributed to the children;
- gifts are distributed to the children who have the YELLOW elf
    - for each child who has not received a gift, the least expensive gift from the first category in their list of preferred categories is found, and if the quantity is greater than 0 it is attributed to them;
- those who have become adults are removed;
- outputChildren is sorted by ID;
- the children from outputChildren are added to the JSONOutput.

The information from the JSONOutput is written to a file.