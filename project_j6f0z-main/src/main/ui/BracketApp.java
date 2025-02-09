/*package ui;

import model.Bracket;
import model.Player;
import model.Teams;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

// Bracket application
public class BracketApp {
    Bracket bracket;
    private static final String JSON_FILE = "./data/bracket.json";
    private Scanner input;
    private JsonWriter writer;
    private JsonReader reader;

    //EFFECTS: runs bracket
    public BracketApp() {
        runBracket();
    }

    //MODIFIES this
    //EFFECTS: displays the opening menu and if a user presses e it shuts it down any other button it progresses to
    // processOpeningInput
    public void runBracket() {
        boolean programOn = true;
        writer = new JsonWriter(JSON_FILE);
        reader = new JsonReader(JSON_FILE);
        String userInput = null;
        input = new Scanner(System.in);


        while (programOn) {
            displayOpening();
            userInput = input.next();
            userInput = userInput.toLowerCase();
            if (userInput.equals("e")) {
                programOn = false;
                System.out.println("\nShutting down");
            } else {
                programOn = false;
                processOpeningInput(userInput);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: displays and allows the user to operate the main menu of the Bracket program
    public void bracketMainMenu() {
        boolean programOn = true;
        String userInput = null;


        while (programOn) {
            displayBracketMainMenu();
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (userInput.equals("e")) {
                programOn = false;
                System.out.println("\nShutting down");
            } else {
                processMainMenuInput(userInput);
            }
        }
    }



    //===========================================================

    //EFFECTS: displays the opening menu
    public void displayOpening() {
        System.out.println("\nBracket Maker (intriguing name)");
        System.out.println("Make new Bracket: n");
        System.out.println("Continue existing bracket: c");
        System.out.println("Exit System: e");
    }

    //EFFECTS: displays the main bracket menu
    public void displayBracketMainMenu() {
        System.out.println("\n\n\nBracket Name: " + bracket.getBracketName());
        printCurrentBracket();
        System.out.println();
        printPlayerPool();
        System.out.println();
        printAvailablePlayers();
        System.out.println("\n\nMake new team: n");
        System.out.println("Make new player: p");
        System.out.println("Change Team Settings: v");
        System.out.println("Remove Player: r");
        System.out.println("Change Bracket name: c");
        System.out.println("Save Bracket: s");
        System.out.println("Exit System: e");
    }

    //=============================================================

    //EFFECT: processes the inputs from the main bracket menu
    public void processMainMenuInput(String input) {
        if (input.equals("n")) {
            makeNewTeam();
        } else if (input.equals("p")) {
            makeNewPlayer();
        } else if (input.equals("v")) {
            changeTeamSettings();
        } else if (input.equals("c")) {
            changeName();
        } else if (input.equals("r")) {
            removePlayer();
        } else if (input.equals("s")) {
            saveBracket();
        } else {
            System.out.println("Not an available command please try again");
        }
    }

    //EFFECTS: processes the inputs from the opening menu
    public void processOpeningInput(String input) {
        if (input.equals("c")) {
            loadBracket();
            bracketMainMenu();
        } else if (input.equals("n")) {
            bracket = new Bracket("New Bracket");
            bracketMainMenu();
        } else {
            System.out.println("Not an available command please try again");
            runBracket();
        }
    }

    //EFFECTS: processes the inputs from the modify roster menu
    public void processChangeTeamInput(Teams team, String input) {
        if (input.equals("p")) {
            changeRoster(team);
        } else if (input.equals("t")) {
            changeTeamName(team);
        } else if (input.equals("s")) {
            changeSuspension(team);
        } else if (input.equals("h")) {
            if (changeSeeding(team)) {
                System.out.println("Seeding changed successfully, now: " + team.getSeeding());
            } else {
                System.out.println("Another team already has this seed");
            }
        } else if (input.equals("r")) {
            removeTeam(team);
        } else {
            System.out.println("Not an available command");
        }
    }

    //EFFECTS: processes the inputs from the modify roster menu
    public void processChangeRoster(Teams team, Player player, String input) {
        if (input.equals("p")) {
            if (team.addTeamMembers(player)) {
                System.out.println(player.getName() + " has been added to the roster");
            } else {
                System.out.println(player.getName() + " is already apart of your roster or is apart of another roster");
            }
        } else if (input.equals("r")) {
            if (team.removeTeamMembers(player)) {
                System.out.println("successfully removed " + player.getName());
            } else {
                System.out.println(player.getName() + " was not on the team");
            }
        } else {
            System.out.println("Not an available command");
        }
    }

    //================================================================

    //EFFECTS: Displays and allows the user to operate the change team settings menu with choices to change roster,
    // change team name, change suspension, and change seeding
    public void changeTeamSettings() {
        Teams team;

        System.out.println("What team would you like to modify");
        printCurrentTeams();
        String name = input.next();
        team = findTeam(name);
        System.out.println("You have selected: " + team.getTeamName());
        printPlayerTeam(team);
        if (team.isSuspended()) {
            System.out.println("\nStatus: suspended");
        } else {
            System.out.println("\nStatus: in bracket");
        }
        System.out.println("\nWhat would you like to change");
        System.out.println("Change Roster: p");
        System.out.println("Change Team Name: t");
        System.out.println("Change Suspension: s");
        System.out.println("Change Seeding: h");
        System.out.println("Remove team: r");
        String userInput = input.next();
        processChangeTeamInput(team, userInput);

    }

    //EFFECTS: Displays and allows the user to operate the change roster settings menu with choices to add player to
    //roster, or remove player from roster.
    public void changeRoster(Teams team) {
        System.out.println("What player would you like to modify: ");
        printPlayerPool();
        System.out.println();
        String name = input.next();
        Player player = findPlayer(name);
        System.out.println("Player " + player.getName() + " has been found what would you like to do");
        System.out.println("Add Player to roster: p");
        System.out.println("Remove Player off of roster: r");
        String userInput = input.next();
        processChangeRoster(team, player, userInput);
    }


    //=======================================================================

    //MODIFIES: Teams
    //EFFECTS: Changes the team name if the team name has not been taken by another team in the bracket.
    public void changeTeamName(Teams team) {
        System.out.println("What would you like to change it to: ");
        String name = input.next();
        if (bracket.unique(new Teams(name, -5))) {
            team.setTeamName(name);
        } else {
            System.out.println("Looks like someone has taken the name " + name);
        }

    }

    //MODIFIES: Teams
    //EFFECTS: Changes the team suspension status from Suspended to unsuspended or the other way around
    public void changeSuspension(Teams team) {
        if (team.isSuspended()) {
            System.out.println(team.getTeamName() + " has been unsuspended");
        } else {
            System.out.println(team.getTeamName() + " is now suspended");
        }
        team.suspend();
    }

    //MODIFIES: Teams
    //EFFECTS: Changes the team seeding to a seeding set by the user that has not been used by any other team
    public boolean changeSeeding(Teams team) {
        System.out.println("What would you like to change it to: ");
        int seed = input.nextInt();
        for (Teams t : bracket.getTeams()) {
            if (seed == t.getSeeding()) {
                return false;
            }
        }
        team.setSeeding(seed);
        return true;
    }

    //MODIFIES: Teams, this, Player
    //EFFECTS: removes team from bracket and release the players
    public void removeTeam(Teams team) {
        for (Player p : team.getTeamMembers()) {
            p.teamChange();
        }
        bracket.removeTeam(team);
        System.out.println("Successfully removed");
    }

    //=============================================================

    //REQUIRES: Inputs have no spaces
    //MODIFIES: Teams, Bracket
    //EFFECTS: creates a new team if the seeding and team name are unique and adds it to the bracket
    public void makeNewTeam() {
        System.out.println("Enter you new team name(no spaces):");
        String teamName = input.next();
        System.out.println("Enter this teams seeding(integer)");
        int seeding = input.nextInt();
        if (bracket.addTeam(new Teams(teamName, seeding))) {
            System.out.println("Success!");
        } else {
            System.out.println("Team name is taken or the seeding is already taken");
        }
        printCurrentBracket();
    }

    //REQUIRES: Inputs have no spaces
    //MODIFIES: Player, this
    //EFFECTS: creates a new player if it has a unique name and adds it into the player pool
    public void makeNewPlayer() {
        System.out.println("Enter you new players name (no spaces):");
        String playerName = input.next();
        System.out.println("Enter player age: ");
        int playerAge = input.nextInt();
        if (bracket.uniquePlayer(playerName)) {
            bracket.addPlayer(new Player(playerName, playerAge));
            System.out.println("Player added");
        } else {
            System.out.println("Someone has already registered by that name");
        }


        printPlayerPool();
    }

    //REQUIRES: Inputs have no spaces
    //MODIFIES: Bracket
    //EFFECTS: Changes the bracket name
    public void changeName() {
        System.out.println("What would you like your bracket to be called? (no spaces) ");
        String name = input.next();
        bracket.setBracketName(name);
    }



    //MODIFIES: Bracket, Player
    //EFFECTS: Removes player from the player pool
    public void removePlayer() {
        Player g = new Player("Chen", 1);
        System.out.println("Who would you like to remove from the player pool? ");
        printPlayerPool();
        System.out.println();
        String playerName = input.next();
        for (Player p : bracket.getPlayerPool()) {
            if (p.getName().equals(playerName)) {
                g = p;
                System.out.println("Player Successfully Removed");
            }
        }
        bracket.removePlayer(g);
    }
    //==========================================================================

    //MODIFIES: this
    //EFFECTS: load bracket into the bracketApp
    public void loadBracket() {
        try {
            bracket = reader.read();

        } catch (IOException e) {
            System.out.println("couldn't read file");
        }
    }

    //EFFECTS: saves the current bracket
    public void saveBracket() {
        try {
            writer.open();
            writer.write(bracket);
            writer.close();
            System.out.println("Saved " + bracket.getBracketName() + " to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FILE);
        }
    }

    //===========================================================================

    //EFFECTS: prints out the current bracket of teams excluding suspended teams
    public void printCurrentBracket() {
        List<Teams> teams = bracket.getTeams();
        System.out.print("Current teams in bracket: ");
        for (Teams t : teams) {
            if (!t.isSuspended()) {
                System.out.print("[ " + t.getTeamName() + " ]");
            }
        }
    }

    //EFFECTS: prints out the player pool
    public void printPlayerPool() {
        System.out.print("Current player pool: ");
        for (Player p : bracket.getPlayerPool()) {
            System.out.print("[ " + p.getName() + " ]");
        }
    }

    //EFFECTS: prints out the players on a certain team
    public void printPlayerTeam(Teams team) {
        System.out.print("Current roster: ");
        for (Player p : team.getTeamMembers()) {
            System.out.print("[ " + p.getName() + " ]");
        }
    }

    //EFFECTS: prints out the current bracket of teams
    public void printCurrentTeams() {
        List<Teams> teams = bracket.getTeams();
        System.out.print("All teams: ");
        for (Teams t : teams) {
            System.out.print("[ " + t.getTeamName() + " ]");

        }
        System.out.println("");
    }

    //EFFECTS: prints players who don't have a team
    public void printAvailablePlayers() {
        System.out.print("Available Players: ");
        for (Player p : bracket.getPlayerPool()) {
            if (!p.hasTeam()) {
                System.out.print("[ " + p.getName() + " ]");
            }
        }
    }

    //=====================================================

    //EFFECTS: returns team that matches the name given else returns the first team in the list
    public Teams findTeam(String name) {
        for (Teams t : bracket.getTeams()) {
            if (t.getTeamName().equals(name)) {
                return t;
            }
        }
        System.out.println("found no match so here's the first team");
        return bracket.getTeams().get(0);
    }

    //EFFECTS: returns player that matches the name given else returns the first player in the list
    public Player findPlayer(String name) {
        List<Player> first = bracket.getPlayerPool();
        for (Player p : bracket.getPlayerPool()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        System.out.println("found no match so here's the first team");
        return first.get(0);
    }










}*/
