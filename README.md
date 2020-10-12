## Film Query Project
### Overview
This week 7 project uses all the OOP Java lessons we have learned this far, but this is the first project that adds in data retrieved from a SQL database.

This program is an application for users to interact with a film database. They can navigate through the app and get film/actor information.

Lessons Used
* Object Relational Mapping
* SQL database
* Maven
* MAMP
* Constructors
* Getters/Setters
* Try/Catch blocks
* Object Oriented Programming

### How to Run - Instructions
1. The user is greeted by the programs welcome message and the main menu. Internally, this is from a switch statement in a while loop.
2. The user can pick options by inputting their menu selection, which includes looking up films by an ID, or looking up films by a keyword. Information is displayed to the user about the film they are searching for by pulling from the SQL database. 
3. A submenu is prompted to the user if the user wishes to see MORE info about the film (such as rental rate, language ID, rental duration, etc) that is not displayed unless they wish to see all the info (stretch goal 1). They can exit this submenu back to the main menu as they please.
4. The main menu is a switch statement inside of a while loop, so the menu will keep prompting the user until the user decides to quit the program.
