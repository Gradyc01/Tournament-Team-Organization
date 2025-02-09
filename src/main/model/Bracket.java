package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriter;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a bracket with the list of teams participating, the players participating in the bracket,
// and the bracket/tournament name
public class Bracket implements Writable {
    private List<Teams> teams;
    private List<Player> playerPool;
    private String bracketName;

    //EFFECTS: creates a bracket with name and an empty list of teams and players
    public Bracket(String bracketName) {
        this.bracketName = bracketName;
        EventLog.getInstance().logEvent(new Event("Bracket Made")); //Event
        teams = new ArrayList<>();
        playerPool = new ArrayList<>();
    }


    //MODIFIES: this
    //EFFECTS: adds a unique team to the teams in the bracket and returns true, if not unique returns false
    public boolean addTeam(Teams team) {
        if (unique(team)) {
            teams.add(team);
            EventLog.getInstance().logEvent(new Event("Team " + team.getTeamName() + " made and Added to Bracket"));
            return true;
        }
        return false; //stub
    }


    //MODIFIES: this
    //EFFECTS: remove a unique team to the teams in the bracket and returns true, if not unique returns false
    public boolean removeTeam(Teams team) {
        if (!unique(team)) {
            teams.remove(team);
            EventLog.getInstance().logEvent(new Event("Team " + team.getTeamName() + " Removed from the Bracket"));
            return true;
        }
        return false; //stub
    }


    //MODIFIES: this
    //EFFECT: add player to player pool;
    public void addPlayer(Player player) {
        playerPool.add(player);
        EventLog.getInstance().logEvent(new Event("Added Player " + player.getName() + " to the Player Pool"));
    }

    //MODIFIES: this
    //EFFECTS: remove player from player pool
    public void removePlayer(Player player) {
        playerPool.remove(player);
        EventLog.getInstance().logEvent(new Event("Remove Player " + player.getName() + " from the Player Pool"));
    }

    //EFFECTS: checks if the team has a unique name and unique seeding
    public boolean unique(Teams team) {
        for (Teams t : teams) {
            if (t.getTeamName().equals(team.getTeamName()) || t.getSeeding() == team.getSeeding()) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: return as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("League Name", bracketName);
        json.put("List of Teams", teamsToJson());
        json.put("List of Players", playersToJson());
        return json;
    }

    //EFFECTS: returns teams as JSONArray
    public JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Teams team : teams) {
            jsonArray.put(team.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns players as JSONArray
    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player player : playerPool) {
            jsonArray.put(player.toJson());
        }

        return jsonArray;
    }


//=====================================================================================================


    public void setBracketName(String bracketName) {
        this.bracketName = bracketName;
        EventLog.getInstance().logEvent(new Event("Set bracket name to " + bracketName)); //Event
    }

    public List<Teams> getTeams() {
        return teams;
    }

    public int getTeamBracketSize() {
        return teams.size();
    }

    public int getPlayerBracketSize() {
        return playerPool.size();
    }

    public String getBracketName() {
        return bracketName;
    }

    public List<Player> getPlayerPool() {
        return playerPool;
    }


    //EFFECTS: returns true if player name is not used, else false
    public boolean uniquePlayer(String name) {
        for (Player p : playerPool) {
            if (p.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
