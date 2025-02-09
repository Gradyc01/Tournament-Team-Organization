# Tournament Organizing Application

## An application designed to make your tournament a bit more manageable

This application will allow the user to quickly create teams (details below) and allow for them to be placed into a 
bracket in order to make organizing the teams participating in the tournament a lot easier. Tournament organizers will
have a much better time making teams and moderating them in our application. This project was an interest for me as my 
obsession with these events and how they are operated made me want to explore a major aspect of running a tournament. 
Although I doubt their tool is as simple as mine I feel it still could be a good insight.

### **List of customization for teams**:
- *Team Name*
- *Team members*
- *Suspension Status*
- *Seeding*
- etc.

### **List of customization of Players**:
- *Name*
- *Age*
- *On a team?*
- etc.

## User Stories:
- As a user I want to be able to add a player to the bracket
- As a user I want to be able to create a team to the bracket
- As a user I want to be able to create a bracket with teams and players
- As a user I want to be able to remove a team from the bracket      
- As a user I want to be able to suspend a team from the bracket
- As a user I want to be able to remove a player from the bracket
- As a user I want to be able to add a player to the team within the bracket
- As a user I want to be able to change the names of my teams and the bracket itself
- As a user I want to be able to kick players off of my team roster

- As a user I want to be able to store all bracket information including team & player data
- As a user I want to be able to load all bracket information from my previously saved

- As a user, I want to be able to add multiple Teams to a Bracket, where teams and bracket are classes that I have 
  designed as part of my application.  
- As a user, I want to be able to load and save the state of the application

## INSTRUCTIONS FOR GRADER (please be kind)
- You can generate the first required action related to adding Xs to a Y by:
  - first either loading or creating a new bracket then hitting the "Create new Team" button
  - second choose you desired team name and seeding then it will create an empty team in the tournament with that name
    if the inputs required are not valid the prompt will not close until it is valid

- You can generate the second required action related to adding Xs to a Y by:
  - first making a team and maybe some players as well
  - you can then hit "modify teams"
  - there you then have to select the team you want to modify and will be prompted with a full breakdown of said team
    and ways to change it.
  - For the purpose of this example you may hit the "Change Team Name" Button
  - You'll then be prompted to make your new team name choosing a team name that is already in use will not work
  - hitting submit with a unique team name will change it to the new team name and display in the bracket on the right
    
- You can locate my visual component by booting up the application and looking at the background

- You can save the state of my application by pressing the "save" button whilst making your bracket

- You can reload the state of my application by pressing the "load existing bracket" button

## Phase 4: Task 2
-  [Wed Aug 09 16:58:55 PDT 2023] : Bracket Made
-  [Wed Aug 09 16:59:03 PDT 2023] : Team NRG made and Added to Bracket
-  [Wed Aug 09 16:59:08 PDT 2023] : Team SSG made and Added to Bracket
-  [Wed Aug 09 16:59:13 PDT 2023] : Team V1 made and Added to Bracket
-  [Wed Aug 09 16:59:32 PDT 2023] : Added Player GarrettG to the Player Pool
-  [Wed Aug 09 16:59:41 PDT 2023] : Added Player Squishy to the Player Pool
-  [Wed Aug 09 16:59:46 PDT 2023] : Added Player JSTN to the Player Pool
-  [Wed Aug 09 16:59:54 PDT 2023] : Added Player Arsenal to the Player Pool
-  [Wed Aug 09 17:00:03 PDT 2023] : Added Player LJ to the Player Pool
-  [Wed Aug 09 17:00:08 PDT 2023] : Added Player Hockser to the Player Pool
-  [Wed Aug 09 17:00:15 PDT 2023] : Added Player Comm to the Player Pool
-  [Wed Aug 09 17:00:22 PDT 2023] : Added Player Beastmode to the Player Pool
-  [Wed Aug 09 17:00:29 PDT 2023] : Added Player Daniel to the Player Pool
-  [Wed Aug 09 17:00:35 PDT 2023] : Added Player Aztral to the Player Pool
-  [Wed Aug 09 17:00:45 PDT 2023] : Adds player GarrettG to the team NRG
-  [Wed Aug 09 17:00:49 PDT 2023] : Adds player Squishy to the team NRG
-  [Wed Aug 09 17:00:53 PDT 2023] : Adds player JSTN to the team NRG
-  [Wed Aug 09 17:01:11 PDT 2023] : Adds player Arsenal to the team SSG
-  [Wed Aug 09 17:01:15 PDT 2023] : Adds player LJ to the team SSG
-  [Wed Aug 09 17:01:21 PDT 2023] : Adds player Hockser to the team SSG
-  [Wed Aug 09 17:01:30 PDT 2023] : Adds player Comm to the team V1
-  [Wed Aug 09 17:01:36 PDT 2023] : Adds player Beastmode to the team V1
-  [Wed Aug 09 17:02:03 PDT 2023] : Adds player Daniel to the team V1
-  [Wed Aug 09 17:02:16 PDT 2023] : Team GradyChens made and Added to Bracket
-  [Wed Aug 09 17:02:22 PDT 2023] : Added Player 1 to the Player Pool
-  [Wed Aug 09 17:02:35 PDT 2023] : Adds player 1 to the team GradyChens
-  [Wed Aug 09 17:02:38 PDT 2023] : Remove Player 1 from the team GradyChens
-  [Wed Aug 09 17:02:44 PDT 2023] : Change team name to C9 from GradyChens
-  [Wed Aug 09 17:02:48 PDT 2023] : Change suspension for C9 now true
-  [Wed Aug 09 17:02:54 PDT 2023] : Change seeding for C9 to 21
-  [Wed Aug 09 17:02:55 PDT 2023] : Change suspension for C9 now false
-  [Wed Aug 09 17:02:57 PDT 2023] : Team C9 Removed from the Bracket
-  [Wed Aug 09 17:02:59 PDT 2023] : Saved New Bracket to ./data/bracket.json

## Phase 4: Task 3
- My design of this project for the most part is messy. As more phases are being completed the becoming more complicated
some of my goals have more been focused on trying to understand the code I have to implement more than trying to write 
the most clean code. I had to make a major adjustment to my idea for the project as my over ambition got the better of 
me and I had to rapidly downsize as I went through the phase 1 requirements. If I had more time for my project I would
have made a ton more helpers than I currently have. The BracketUI classes has some checks to for valid teams and players
that I would have liked to be moved to their associated class. I would love to also improve the GUI and not have all the 
for the GUI in one class. Separating them would have made the code much easier to digest instead of the monster that it 
is right now. Due to my poor time management I found that the way I code isn't the best I normally spend 6 - 8 hours in 
one day working on the project and then leaving it for another two after causing me to have to remember my system of 
checks however most of the time I forgot about where I put them causing some errors that should not have occured. 
Refactoring BracketUI to separate the code and move some code that should be in the model class would be my number 1 
priority and then after maybe adding some of my original functionality ideas. 
