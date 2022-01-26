# Minesweeper
Minesweeper for Grade 11 Computer Science by Hess Lewis

# How to play

Minesweeper is very straight forward.
There is a field of cells some of which are mines.
Your goal is to flag all the mines, or open all non mine cells.
If you open a mine you fail!

Left click on a cell to open it, right click to flag it. 
Open cells display how many mines are directly around them.
Use logic to determine which cells are mines, and flag them.

# Smiley

Smiley is the game's indicator and reset button.
Click on him to reset the field.
Upon loosing or winning Smiley's face will change to suit.

# Counters

The counter to the left of Smiley is how many flags are left.
To the right of him is how long this current session has been running.


# Assets

Assets taken from [here](https://tcrf.net/Proto:Minesweeper_(Windows,_1990)/Mine_2.6)


# Structure

Cell class handles a single Cell on the field

Field class holds an array of cells and handles interacting with them

# IO

The state of field is updated by a mouse listener, and repainted intermittently by the "Main" class.

# Requirements

- [x] Your program must be done in Java;
- [x] Your program must contain programmer comments explaining all aspects of the code;
- [x] You must use proper programming style/format (indenting, spacing, proper variable names, etc.);
- [ ] The program must contain at least one searching algorithm;
- [ ] The program must contain at least one sorting algorithm;
- [x] The program must use at least one array;
- [x] The program must use built-in methods as well as methods(function and procedure type) created by you;
- [x] Your program must access (read and write) a sequential file;
- [x] The program must be efficient (don’t write ten lines of code that can be written in two);
- [x] Your program must be original. Copying from your peers or the Internet will result in serious consequences!
- [x] Make sure that YOU can explain EVERY aspect of your program.

