# UC1. Game Setup

### Actor: 3-6 Players

### Description
This use case covers the setup phase of the game. This breaks down into player creation, deciding the order of players, and placing the initial settlements and roads.

### Basic Flow
1. The Game displays the start menu. 
2. Player 1 clicks the "New Game" button 
3. The Game displays the following options to select the number of players. 
   1. 2 players
   2. 3 players
   3. 4 players
   4. 5 players
   5. 6 players
4. Player 1 selects the amount of players to play with. 
5. The Game displays a list of all players with text fields asking for their names and a color dropdown menu for a color. 
6. Each player fills in their name and selects a color. 
7. Player 1 clicks the "Start Game" button. 
8. The Game enters a loading screen where it sets up the domain. 
9. The Game displays the domain with two dice. the Game informs the players to click the dice to roll for the order of players. This process in repeated for each player.
   1. The game displays the message for Player 1 using their name. Player 1 clicks the dice to roll.
   2. The game displays the message for Player 2 using their name. Player 2 clicks the dice to roll.
   3. The game displays the message for Player 3 using their name. Player 3 clicks the dice to roll.
   4. Continues for each additional player...
10. The Game displays the order of players based on the dice rolls. 
11. The Game displays the domain for the first player's turn. The Game prompts the player to place a settlement. The possible spots are highlighted green. 
12. The first player clicks on a spot to place their settlement. 
13. The Game prompts the first player to place a road. The possible paths are highlighted green.
14. The first player clicks on a path to place their road. 
15. This process from step 11-14 for each player in their order. 
16. The Game prompts the players that the order of placement will now be reversed. 
17. The Game prompts the last player to place a settlement. The possible spots are highlighted green. 
18. The last player clicks on a spot to place their settlement. 
19. The Game prompts the last player to place a road. The possible paths are highlighted green. 
20. The last player clicks on a path to place their road. 
21. This process from step 17-20 for each player in reverse order. 
22. The Game prompts the first player to begin their turn. 
23. The Player clicks the "Begin Turn" button.

### Alternate Flow
Step 4: The player hits the "Back" button.
1. Jump to step 1

Step 6 or 7: The player hits the "Back" button.
1. Jump to step 3

### Exceptions
Any Step: The player exits the game.
1. The Game returns to the main menu.

Any Step: The player closes the Game.
1. The Game closes, killing the process.
### Preconditions
The Game is on the main menu

### Postconditions
All players have placed their initial settlements and roads.
The first turn for the first player starts

### System or subsystem
Computer

### Other Stakeholders
None

### Special Requirements:
The system shall accurately track the player's choices throughout the setup phase.
The system shall complete a user's selection (such as placing a settlement) within 2 seconds.


