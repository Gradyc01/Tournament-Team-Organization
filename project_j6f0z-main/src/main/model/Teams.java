package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a team having an teamName, teamMembers, whether they're suspended,
// and their seeding(bracket placements).
public class Teams implements Writable {
    private String teamName;
    private List<Player> teamMembers;
    private boolean isSuspended;
    private int seeding;

    //REQUIRES: seeding >= 0
    //EFFECTS: Sets team name and seeding with an empty team and not suspended
    public Teams(String teamName, int seeding) {
        this.teamName = teamName;
        this.teamMembers = new ArrayList<>();
        isSuspended = false;
        this.seeding = seeding;
    }



    //MODIFIES: this, player
    //EFFECTS: adds a unique player who has no team to teamMembers,
    // if successful returns true and change his hasTeam to true
    // else returns false
    public boolean addTeamMembers(Player player) {
        if (!teamMembers.contains(player) && !player.hasTeam()) {
            teamMembers.add(player);
            //Event
            EventLog.getInstance().logEvent(new Event("Adds player " + player.getName() + " to the team " + teamName));
            player.teamChange();
            return true;
        }
        return false;
    }

    //MODIFIES: this, player
    //EFFECTS:  removes a unique player from teamMembers,
    // changes his hasTeam to false and returns true,
    // if player not in teamMembers return false;
    public boolean removeTeamMembers(Player player) {
        if (teamMembers.contains(player)) {
            Event event = new Event("Remove Player " + player.getName() + " from the team " + teamName);
            teamMembers.remove(player);
            EventLog.getInstance().logEvent(event);
            player.teamChange();
            return true;
        }
        return false;
    }



    //MODIFIES: this
    //EFFECTS: if isSuspended is false, suspends the team , else unsuspended the team
    public void suspend() {
        isSuspended = !isSuspended;
        EventLog.getInstance().logEvent(new Event("Change suspension for " + teamName + " now " + isSuspended()));
    }

    public void setSeeding(int seeding) {
        this.seeding = seeding;
        EventLog.getInstance().logEvent(new Event("Change seeding for " + teamName + " to " + seeding));
    }

    public void setTeamName(String teamName) {
        String oldName = this.teamName;
        this.teamName = teamName;
        EventLog.getInstance().logEvent(new Event("Change team name to " + teamName + " from " + oldName));
    }

    //=================================================================================

    //EFFECTS: returns as JSONObject();
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Team Name", teamName);
        json.put("Team Members", membersToJson());
        json.put("Suspension Status", isSuspended);
        json.put("Seeding", seeding);
        return json;
    }

    //EFFECTS: returns members as JSONArray
    public JSONArray membersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player player : teamMembers) {
            jsonArray.put(player.toJson());
        }

        return jsonArray;
    }

//=================================================================================

    public int getTeamSize() {
        return teamMembers.size();
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public List<Player> getTeamMembers() {
        return teamMembers;
    }

    public int getSeeding() {
        return seeding;
    }

}
