package ui;

import model.Bracket;
import model.Event;
import model.EventLog;
import model.Player;
import model.Teams;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import static java.lang.Integer.parseInt;
// Bracket application with GUI

public class BracketUI extends JFrame implements WindowListener {

    protected JButton loadNewBracket;
    protected JButton loadExistingBracket;
    protected JButton createTeamButton;
    protected JButton createPlayerButton;
    protected JButton changeBracketNameButton;
    protected JButton displayTeam;
    protected JButton removePlayer;
    protected JButton refreshBracket;
    protected JButton save;
    private JTextField textField;
    private JTextField teamSeedingField;

    Bracket bracket;
    private static final String JSON_FILE = "./data/bracket.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private JInternalFrame newTeam;
    private JInternalFrame newPlayer;
    private JInternalFrame displayBracket;
    private JInternalFrame prompt;
    private JInternalFrame teamsDisplay;
    private JInternalFrame teamPrompt;
    private JInternalFrame testButtons;
    private JPanel beginningPanel;
    private JPanel planet;


    private JsonWriter writer;
    private JsonReader reader;
    private ImageIcon backgroundImage;


    //MODIFIES: this
    //EFFECTS: displays the opening menu
    public BracketUI()  {
        backgroundImage = new ImageIcon("./data/images/background.jpeg");
        writer = new JsonWriter(JSON_FILE);
        reader = new JsonReader(JSON_FILE);
        boolean keepGoing = true;
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        //desktop.setLayout(new BorderLayout());


        setContentPane(desktop);
        addWindowListener(this);
        setTitle("Bracket App");
        setSize(WIDTH, HEIGHT);

        setUpButtons();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centerOnScreen();
        setVisible(true);
        setEnabled(true);
        System.out.println(desktop.getComponentCount());
    }

    //MODIFIES: this
    //EFFECTS: sets up buttons for the opening menu
    private void setUpButtons() {
        loadNewBracket = new JButton(new LoadNewBracketAction());
        loadExistingBracket = new JButton(new LoadExistingBracketAction());
        planet = new JPanel();
        JLabel background = new JLabel();
        planet.setSize(new Dimension(WIDTH, HEIGHT));
        planet.setLayout(new BorderLayout());
        background.setIcon(backgroundImage);
        planet.add(background, BorderLayout.CENTER);

        testButtons = new JInternalFrame("Start", false, false, false, false);
        testButtons.setLayout(new BorderLayout());
        testButtons.setVisible(true);
        testButtons.setLocation(WIDTH / 2 - WIDTH / 10, HEIGHT / 2 - HEIGHT / 14);
        testButtons.pack();
        testButtons.setSize(WIDTH / 5, HEIGHT / 7);

        beginningPanel = new JPanel();
        beginningPanel.setSize(WIDTH, HEIGHT);

        beginningPanel.setLayout(new BoxLayout(beginningPanel, BoxLayout.Y_AXIS));

        beginningPanel.add(loadNewBracket);
        beginningPanel.add(loadExistingBracket);

        testButtons.add(beginningPanel);

        desktop.add(testButtons);
        desktop.add(planet);
    }


    //======================================================

    // Action for "Create a new Bracket" button
    private class LoadNewBracketAction extends AbstractAction {

        //EFFECTS: names the button
        LoadNewBracketAction() {
            super("Create A New Bracket");
        }

        //MODIFIES: this, bracket
        //EFFECTS: makes a new bracket and runs bracket
        @Override
        public void actionPerformed(ActionEvent evt) {
            bracket = new Bracket("New Bracket");
            runBracket();
        }
    }

    // Action for "Load Existing Bracket" button
    private class LoadExistingBracketAction extends AbstractAction {

        //EFFECTS: names the button
        LoadExistingBracketAction() {
            super("Load Existing Bracket");
        }

        //MODIFIES: this, bracket
        //EFFECTS: performs loadBracket and runBracket()
        @Override
        public void actionPerformed(ActionEvent evt) {
            loadBracket();
            runBracket();
        }


    }

    //MODIFIES: this
    //EFFECTS: load bracket into the bracketApp
    public void loadBracket() {
        try {
            bracket = reader.read();

        } catch (IOException e) {
            System.out.println("couldn't read file");
        }
    }

    //===============================================

    //MODIFIES: this
    //EFFECTS: displays and allows the user to operate the main menu of the Bracket program
    public void runBracket() {
        beginningPanel.setVisible(false);
        testButtons.setVisible(false);
        planet.setVisible(false);

        startControlPanel();
        bracketButtons();
        displayBracket();

    }


    //Allows to detect mouse clicks
    private class DesktopFocusAction extends MouseAdapter {
        //EFFECT: detected mouse click
        @Override
        public void mouseClicked(MouseEvent e) {
            BracketUI.this.requestFocusInWindow();
        }
    }

    //EFFECTS: measures the center of the screen
    private void centerOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }


    //MODIFIES: this
    //EFFECTS: makes the control panel with all buttons for bracket
    private void startControlPanel() {
        controlPanel = new JInternalFrame(bracket.getBracketName(), false, false, false, false);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        controlPanel.setLocation(0, 0);
        controlPanel.setSize(WIDTH / 4, HEIGHT / 2);
    }

    //MODIFIES: this
    //EFFECTS: makes the buttons for the control panels
    private void bracketButtons() {
        JPanel buttons = new JPanel();

        buttons.setVisible(true);
        createTeamButton = new JButton(new CreateTeamAction());
        createPlayerButton = new JButton(new CreatePlayerAction());
        changeBracketNameButton = new JButton(new ChangeBracketNameAction());
        displayTeam = new JButton(new DisplayTeamAction());
        save = new JButton(new SaveAction());
        refreshBracket = new JButton(new RefreshBracketAction());
        removePlayer = new JButton(new RemovePlayerAction());
        buttons.add(createTeamButton, SpringLayout.VERTICAL_CENTER);
        buttons.add(createPlayerButton, SpringLayout.SOUTH);
        buttons.add(changeBracketNameButton);
        buttons.add(displayTeam);
        buttons.add(removePlayer);
        buttons.add(refreshBracket);
        buttons.add(save);
        buttons.setLayout(new GridLayout(buttons.getComponentCount(), 1));

        controlPanel.add(buttons);

    }

    //EFFECTS: displays the bracket on said JInternalFrame
    private void displayBracket() {
        displayBracket = new JInternalFrame(bracket.getBracketName(), false, false, false, false);
        displayBracket.setLayout(new BorderLayout());
        displayBracket.setVisible(true);
        displayBracket.add(displayPanel());
        displayBracket.setLocation(WIDTH / 2, 0);
        displayBracket.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT - 15));
        displayBracket.pack();
        desktop.add(displayBracket);
    }

    //EFFECTS: the panel that displays on display bracket
    private JPanel displayPanel() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.add(new JLabel(""));
        displayPanel.add(new JLabel("Teams: "));
        for (Teams t : bracket.getTeams()) {
            displayPanel.add(new JLabel("   [Team: " +  t.getTeamName() + " ]"));
            displayPanel.add(new JLabel("         Roster: "));
            for (Player p : t.getTeamMembers()) {
                displayPanel.add(new JLabel("           " + p.getName()));
            }
            displayPanel.add(new JLabel("   Status: " + suspension(t)));
            displayPanel.add(new JLabel("   Seeding: " + t.getSeeding()));
        }
        displayPanel.add(new JLabel(""));
        displayPanel.add(new JLabel("Players: "));
        for (Player p: bracket.getPlayerPool()) {
            displayPanel.add(new JLabel("   [ " + p.getName() + " ] " + availability(p)));
        }
        return displayPanel;
    }

    //EFFECTS: returns a string based on whether they are suspended
    private String suspension(Teams t) {
        if (t.isSuspended()) {
            return "Suspended";
        } else {
            return "In Tournament";
        }
    }

    //EFFECTS: returns a string based on whether they are available
    private String availability(Player p) {
        if (p.hasTeam()) {
            return "Picked Up";
        }
        return "Free Agent";
    }


//==================================================================================================

    //MODIFIES: this
    //EFFECTS: Makes the JInternalFrame for creating a team
    private void makeNewTeam() {
        newTeam = new JInternalFrame("Create a Team", false, true, false, false);
        newTeam.setLayout(new BorderLayout());
        newTeam.pack();
        newTeam.setVisible(true);
        newTeam.add(createNewTeamButtons());
        newTeam.setLocation(0, HEIGHT / 2);
        newTeam.setSize(WIDTH / 4, HEIGHT / 2);

        desktop.add(newTeam);
    }

    //MODIFIES: this
    //EFFECTS: creates the buttons for the makeNewTeam() JInternalFrame
    private JPanel createNewTeamButtons() {
        JPanel buttons = new JPanel();
        JLabel teamNameLabel = new JLabel("Team Name?");
        textField = new JTextField();
        JLabel teamSeedingLabel = new JLabel("Seeding? (Integer)");
        teamSeedingField = new JTextField();
        JButton submitTeam = new JButton(new SubmitTeamAction());

        teamSeedingField.setPreferredSize(new Dimension(200, 40));
        textField.setPreferredSize(new Dimension(200, 40));
        buttons.add(teamNameLabel);
        buttons.add(textField);
        buttons.add(teamSeedingLabel);
        buttons.add(teamSeedingField);
        buttons.add(submitTeam);
        buttons.setVisible(true);
        return buttons;
    }

    //==========================================================================
    //MODIFIES: this
    //EFFECTS: Makes the JInternalFrame for creating a player
    private void makeNewPlayer() {
        newPlayer = new JInternalFrame("Create a Player", false, true, false, false);
        newPlayer.setLayout(new BorderLayout());
        newPlayer.pack();
        newPlayer.setVisible(true);
        newPlayer.add(createNewPlayerButtons());
        newPlayer.setLocation(0, HEIGHT / 2);
        newPlayer.setSize(WIDTH / 4, HEIGHT / 2);

        desktop.add(newPlayer);
    }

    //MODIFIES: this
    //EFFECTS: Makes the buttons for the makeNewPlayer() JInternalFrame
    private JPanel createNewPlayerButtons() {
        JPanel buttons = new JPanel();
        JLabel playerNameLabel = new JLabel("Player name");
        textField = new JTextField();
        JLabel teamSeedingLabel = new JLabel("Age");
        teamSeedingField = new JTextField();
        JButton submitPlayer = new JButton(new SubmitPlayerAction());

        teamSeedingField.setPreferredSize(new Dimension(200, 40));
        textField.setPreferredSize(new Dimension(200, 40));
        buttons.add(playerNameLabel);
        buttons.add(textField);
        buttons.add(teamSeedingLabel);
        buttons.add(teamSeedingField);
        buttons.add(submitPlayer);
        buttons.setVisible(true);
        return buttons;
    }
    //============================================================

    //MODIFIES: this
    //EFFECTS: creates a JInternalFrame that allows the user to answer a certain prompt
    private void prompt(String s, Integer i) {
        prompt = new JInternalFrame(s, false, true, false, false);
        prompt.setLayout(new BorderLayout());

        prompt.setVisible(true);
        prompt.add(promptButtons(s, i));
        prompt.setLocation(0, HEIGHT / 2);
        prompt.setSize(WIDTH / 4, HEIGHT / 2);
        prompt.pack();
        desktop.add(prompt);
    }

    //MODIFIES: this
    //EFFECTS: creates the textField and label that is used in prompt();
    private JPanel promptButtons(String s, Integer i) {
        JPanel buttons = new JPanel();
        JLabel promptLabel = new JLabel(s);
        JLabel currentX;
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 40));
        JButton submitPrompt = new JButton(new SubmitPrompt(s));
        buttons.add(promptLabel);
        if (i == 1) {
            currentX = new JLabel(getTeams());
            buttons.add(currentX);
        } else if (i == 2) {
            currentX = new JLabel(getPlayers());
            buttons.add(currentX);
        }
        buttons.add(textField);
        buttons.add(submitPrompt);

        return buttons;
    }

    //MODIFIES: this
    //EFFECTS: returns a string of all teams that are currently in the bracket
    public String getTeams() {
        String value = "Current Teams: ";
        List<Teams> teams = bracket.getTeams();
        for (Teams t : teams) {
            value = value.concat("[ " + t.getTeamName() + " ]");
        }
        return value;
    }

    //=====================================================================

    //MODIFIES: this
    //EFFECTS: creates a JInternalFrame that allows the user to answer a certain prompt that evolves around teams
    private void teamPrompt(String s, Integer i, Teams t) {
        teamPrompt = new JInternalFrame(s, false, true, false, false);
        teamPrompt.setLayout(new BorderLayout());

        teamPrompt.setVisible(true);
        teamPrompt.add(teamPromptButtons(s, i, t));
        teamPrompt.setLocation(0, HEIGHT / 2);
        teamPrompt.setSize(WIDTH / 4, HEIGHT / 2);
        teamPrompt.pack();
        desktop.add(teamPrompt);
    }

    //MODIFIES: this
    //EFFECTS: creates the textField and label that is used in teamPrompt();
    private JPanel teamPromptButtons(String s, Integer i, Teams t) {
        JPanel buttons = new JPanel();
        JLabel promptLabel = new JLabel(s);
        JLabel currentX;
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 40));
        JButton submitPrompt = new JButton(new TeamSubmitPrompt(s, t));
        buttons.add(promptLabel);
        if (i == 2) {
            currentX = new JLabel(getPlayers());
            buttons.add(currentX);
        } else if (i == 3) {
            currentX = new JLabel(getRosterPlayers(t));
            buttons.add(currentX);
        }
        buttons.add(textField);
        buttons.add(submitPrompt);

        return buttons;
    }

    //EFFECTS: prints out the player pool
    public String getPlayers() {
        String value = "Current player pool: ";
        for (Player p : bracket.getPlayerPool()) {
            value = value.concat("[ " + p.getName() + " ]");
        }
        return value;
    }

    //EFFECTS: prints out the players on a certain team
    public String getRosterPlayers(Teams team) {
        String value = "Current roster: ";
        for (Player p : team.getTeamMembers()) {
            value = value.concat("[ " + p.getName() + " ]");
        }
        return value;
    }



    //=====================================================================

    //MODIFIES: this
    //EFFECTS: sets up the JInternalFrame that displays the team menu
    private void setUpTeamsDisplay(Teams t) {
        teamsDisplay = new JInternalFrame(t.getTeamName(), false, true, false, false);
        teamsDisplay.setLayout(new BorderLayout());

        teamsDisplay.setVisible(true);
        teamsDisplay.add(teamsDisplayButtons(t));
        teamsDisplay.setLocation(0, HEIGHT / 2);
        teamsDisplay.setSize(WIDTH / 4, HEIGHT / 2);
        teamsDisplay.pack();
        desktop.add(teamsDisplay);
    }

    //MODIFIES: this
    //EFFECTS: sets up the buttons for setUpTeamsDisplay()
    private JPanel teamsDisplayButtons(Teams t)  {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        JLabel teamMembers = new JLabel(printPlayerTeam(t));
        JLabel teamSuspension = new JLabel("Suspended? " + t.isSuspended());
        JLabel teamSeeding = new JLabel("Seeding: " + t.getSeeding());
        JButton changeTeamNameButton = new JButton(new ChangeTeamAction(0, t));
        JButton addTeamMembersButton = new JButton(new ChangeTeamAction(1, t));
        JButton removeTeamMembersButton = new JButton(new ChangeTeamAction(2, t));
        JButton suspendTeamButton = new JButton(new ChangeTeamAction(3, t));
        JButton changeTeamSeedingButton = new JButton(new ChangeTeamAction(4, t));
        JButton removeTeam = new JButton(new ChangeTeamAction(5, t));
        buttons.add(teamMembers);
        buttons.add(teamSuspension);
        buttons.add(teamSeeding);
        buttons.add(changeTeamNameButton);
        buttons.add(addTeamMembersButton);
        buttons.add(removeTeamMembersButton);
        buttons.add(suspendTeamButton);
        buttons.add(changeTeamSeedingButton);
        buttons.add(removeTeam);
        buttons.setVisible(true);
        return buttons;
    }

    //EFFECTS: makes a string out of the players on a certain team
    public String printPlayerTeam(Teams team) {
        String value = "Current roster: ";
        for (Player p : team.getTeamMembers()) {
            value = value.concat("[ " + p.getName() + " ]");
        }
        return value;
    }

    //=====================================================================


    //============================================================

    //Action for Create a new team button
    private class CreateTeamAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for create a new team
        CreateTeamAction() {
            super("Create a New Team");
        }

        //MODIFIES: BracketUI
        //EFFECTS: calls makeNewTeam when pressed
        @Override
        public void actionPerformed(ActionEvent evt) {
            makeNewTeam();
        }
    }

    //Action for Create a new player button
    private class CreatePlayerAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for create a new player
        CreatePlayerAction() {
            super("Create a New Player");
        }

        //MODIFIES: BracketUI
        //EFFECTS: prompts to make a new player
        @Override
        public void actionPerformed(ActionEvent evt) {
            makeNewPlayer();
        }
    }

    //Action for change bracket name button
    private class ChangeBracketNameAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for Change bracket name button
        ChangeBracketNameAction() {
            super("Change Bracket Name");
        }

        //MODIFIES: BracketUI
        //EFFECTS: prompts with the bracket name change
        @Override
        public void actionPerformed(ActionEvent evt) {
            prompt("Bracket Name?", 0);
        }
    }

    //Action for Modify team button
    private class DisplayTeamAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for Modify team button
        DisplayTeamAction() {
            super("Modify Team");
        }

        //MODIFIES: BracketUI
        //EFFECTS: prompts with "Which teams do you want to display"?
        @Override
        public void actionPerformed(ActionEvent evt) {
            prompt("Which Teams Do You Want To Display?", 1);
        }
    }

    //Action for the save button
    private class SaveAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for Save button
        SaveAction() {
            super("Save");
        }

        //MODIFIES: BracketUI
        //EFFECTS: save bracket
        @Override
        public void actionPerformed(ActionEvent evt) {
            saveBracket();
        }

        //EFFECTS: saves the current bracket
        public void saveBracket() {
            try {
                writer.open();
                writer.write(bracket);
                writer.close();
                //System.out.println("Saved " + bracket.getBracketName() + " to " + JSON_FILE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_FILE);
            }
        }
    }

    //Action for the refresh bracket button
    private class RefreshBracketAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for Refresh Bracket button
        RefreshBracketAction() {
            super("Refresh Bracket");
        }

        //MODIFIES: BracketUI
        //EFFECTS: refreshes the bracket display
        @Override
        public void actionPerformed(ActionEvent evt) {
            refreshBracket();
        }
    }

    //MODIFIES: this
    //EFFECTS: disposes and then remakes the bracket display
    private void refreshBracket() {
        displayBracket.dispose();
        displayBracket();
    }

    //Action for the remove player button
    private class RemovePlayerAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for remove player
        RemovePlayerAction() {
            super("Remove Player");
        }

        //MODIFIES: BracketUI
        //EFFECTS: prompts user with the string
        @Override
        public void actionPerformed(ActionEvent evt) {
            prompt("Which player would you like to remove?", 2);
        }
    }

    //=====================================================================

    //Action for the teamDisplayButtons
    private class ChangeTeamAction extends AbstractAction {
        int value;
        Teams team;

        //MODIFIES: BracketUI
        //EFFECTS: sets name depending on which question it is prompted with
        ChangeTeamAction(Integer i, Teams t) {
            super(getI(i));
            value = i;
            team = t;
        }

        //MODIFIES: BracketUI
        //EFFECTS: prompts them with a string depending on the value given
        @Override
        public void actionPerformed(ActionEvent evt) {
            teamsDisplay.dispose();
            if (value == 0) {
                teamPrompt("What is your new team name?", 0, team);
            } else if (value == 1) {
                teamPrompt("What player would you like to add?", 2, team);
            } else if (value == 2) {
                teamPrompt("What player would you like to remove?", 2, team);
            } else if (value == 3) {
                team.suspend();
                setUpTeamsDisplay(team);
                refreshBracket();
            } else if (value == 4) {
                teamPrompt("What is their new seeding?", 0, team);
            } else {
                removeTeam(team);
            }
        }

        //MODIFIES: BracketUI, bracket, Teams, player
        //EFFECTS: remove team from the bracket and turning on all players to Free agents
        private void removeTeam(Teams team) {
            for (Player p : team.getTeamMembers()) {
                for (Player p2 : bracket.getPlayerPool()) {
                    if (p2.getName().equals(p.getName())) {
                        p2.teamChange();
                    }
                }

            }
            bracket.removeTeam(team);
            System.out.println("Success");
            refreshBracket();
        }

    }

    //MODIFIES: returns a certain string depending on the i value
    private String getI(Integer i) {
        if (i == 0) {
            return "Change Team Name";
        } else if (i == 1) {
            return "Add Player";
        } else if (i == 2) {
            return "Remove Player";
        } else if (i == 3) {
            return "Change Suspension Status";
        } else if (i == 4) {
            return "Change Seeding";
        } else if (i == 5) {
            return "Remove Team";
        }
        return null;
    }

    //====================================================

    // Action for submit button for team
    private class SubmitTeamAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for submit button for team
        SubmitTeamAction() {
            super("Submit");
        }

        //MODIFIES: BracketUI, Bracket
        //EFFECTS: addTeam and refresh bracket if the team name is unique
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (bracket.addTeam(new Teams(textField.getText(), parseInt(teamSeedingField.getText())))) {
                newTeam.setVisible(false);
                refreshBracket();
            }

        }
    }

    // Action for submit button for player
    private class SubmitPlayerAction extends AbstractAction {

        //MODIFIES: BracketUI
        //EFFECTS: sets name for submit button for player
        SubmitPlayerAction() {
            super("Submit");
        }

        //MODIFIES: BracketUI, Bracket
        //EFFECTS: adds player and refreshes bracket if the player name is unique
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (bracket.uniquePlayer(textField.getText())) {
                bracket.addPlayer(new Player(textField.getText(), parseInt(teamSeedingField.getText())));
                newPlayer.setVisible(false);
                refreshBracket();
            }
        }
    }

    //Action for submit button for prompt
    private class SubmitPrompt extends AbstractAction {
        String submit;

        //MODIFIES: BracketUI
        //EFFECTS: sets name for submit button for prompt and sets submit to s
        SubmitPrompt(String s) {
            super("Submit");
            this.submit = s;
        }

        //MODIFIES: BracketUI, Bracket, Teams
        //EFFECTS: depending on submit does certain actions for the submit prompt
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (submit.equals("Bracket Name?")) {
                bracket.setBracketName(textField.getText());
                prompt.setVisible(false);
                controlPanel.setTitle(textField.getText());
                refreshBracket();
            } else if (submit.equals("Which Teams Do You Want To Display?")) {
                List<Teams> teams = bracket.getTeams();
                //System.out.print("Current teams in bracket: ");
                for (Teams t : teams) {
                    if (textField.getText().equals(t.getTeamName())) {
                        setUpTeamsDisplay(t);
                        prompt.setVisible(false);
                        refreshBracket();
                        break;
                    }
                }
                prompt.setVisible(false);
            } else if (submit.equals("Which player would you like to remove?")) {
                removePlayer(textField.getText());
            }
        }

        //MODIFIES: BracketUI, Player, Team, Bracket
        //EFFECTS: Removes player from the player pool and from the team if there is one
        private void removePlayer(String name) {
            for (Player player : bracket.getPlayerPool()) {
                if (name.equals(player.getName()) && player.hasTeam()) {
                    for (Teams t : bracket.getTeams()) {
                        for (Player player1 : t.getTeamMembers()) {
                            if (player1.getName().equals(player.getName())) {
                                t.removeTeamMembers(player1);
                                bracket.removePlayer(player);
                                refreshBracket();
                                prompt.setVisible(false);
                                break;
                            }
                        }
                    }
                } else if (name.equals(player.getName())) {
                    bracket.removePlayer(player);
                    prompt.setVisible(false);
                    refreshBracket();
                }
            }
        }
    }

    //Action for the submit button with Teams involved
    private class TeamSubmitPrompt extends AbstractAction {
        String submit;
        Teams team;

        //MODIFIES: BracketUI
        //EFFECTS: sets name for submit Button with teams and assigns Team and submit
        TeamSubmitPrompt(String s, Teams t) {
            super("Submit");
            this.submit = s;
            team = t;
        }

        //MODIFIES: BracketUI
        //EFFECTS: Perform actions for submitPrompt team depending on submit string
        @Override
        public void actionPerformed(ActionEvent evt) {
            Player p = findPlayer(textField.getText());
            if (submit.equals("What is your new team name?")) {
                boolean unique = true;
                for (Teams t : bracket.getTeams()) {
                    if (t.getTeamName().equals(textField.getText())) {
                        unique = false;
                    }
                }
                uniqueCheck(unique);

            } else if (submit.equals("What player would you like to add?")) {
                if (p != null && team.addTeamMembers(p)) {
                    setUpTeams();
                }
            } else if (submit.equals("What player would you like to remove?")) {
                if (p != null & team.removeTeamMembers(p)) {
                    setUpTeams();
                }
            } else if (submit.equals("What is their new seeding?")) {
                if (changeSeeding(team, parseInt(textField.getText()))) {
                    setUpTeams();
                }
            }
        }

        //MODIFIES: team
        //EFFECTS: Changes team name if it is unique
        private void uniqueCheck(Boolean unique) {
            if (unique) {
                team.setTeamName(textField.getText());
                setUpTeams();
            }
        }

        //MODIFIES: BracketUI
        //EFFECTS: sets up team display based on the team given and refreshes bracket
        private void setUpTeams() {
            setUpTeamsDisplay(team);
            teamPrompt.setVisible(false);
            refreshBracket();
        }
    }

    //MODIFIES: Teams
    //EFFECTS: Changes the team seeding to a seeding set by the user that has not been used by any other team
    public boolean changeSeeding(Teams team, Integer seed) {
        for (Teams t : bracket.getTeams()) {
            if (seed == t.getSeeding()) {
                return false;
            }
        }
        team.setSeeding(seed);
        return true;
    }

    //EFFECTS: returns player that matches the name given else returns the first player in the list
    public Player findPlayer(String name) {
        for (Player p : bracket.getPlayerPool()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
//    public void propertyChange(PropertyChangeEvent e) {
//        Object source = e.getSource();
//        if (source == teamNameField) {
//            amount = ((Number)teamNameField.getValue()).doubleValue();
////        } else if (source == rateField) {
////            rate = ((Number)rateField.getValue()).doubleValue();
////        } else if (source == numPeriodsField) {
////            numPeriods = ((Number)numPeriodsField.getValue()).intValue();
////        }
////
////        double payment = computePayment(amount, rate, numPeriods);
////        paymentField.setValue(new Double(payment));
//    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        Iterator<Event> it = EventLog.getInstance().iterator();

        while (it.hasNext()) {
            Event event = it.next();
            System.out.println("[" + event.getDate() + "] : " + event.getDescription());
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
