package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads bracket from JSON data stored in file

public class JsonReader {

    private String saved;

    //EFFECTS: makes reader
    public JsonReader(String saved) {
        this.saved = saved;
    }

    //EFFECTS: reads a bracket from file and returns it;
    //         if error occur throw IOException
    public Bracket read() throws IOException {
        EventLog.getInstance().logEvent(new Event("Reads saved file")); //Event
        String jsonData = readFile(saved);
        JSONObject jsonObject = new JSONObject(jsonData);
        return returnBracket(jsonObject);
    }

    //EFFECTS: read source file as string and returns;
    //         if error occur throw IOException
    private String readFile(String saved) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(saved), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: consumes a JSONObject and returns bracket
    public Bracket returnBracket(JSONObject jsonObject) {
        String name = jsonObject.getString("League Name");
        Bracket newBracket = new Bracket(name);
        addBracketItems(newBracket, jsonObject);
        return newBracket;
    }

    //MODIFIES: this, bracket
    //EFFECTS: adds the brackets items from JSONObject into the bracket
    public void addBracketItems(Bracket bracket, JSONObject jsonObject)  {
        JSONArray jsonArray = jsonObject.getJSONArray("List of Teams");
        JSONArray jsonArray2 = jsonObject.getJSONArray("List of Players");
        for (Object json : jsonArray) {
            JSONObject team = (JSONObject) json;
            addTeams(bracket, team);
        }
        for (Object json : jsonArray2) {
            JSONObject player = (JSONObject) json;
            addPlayers(bracket, player);
        }
    }

    //MODIFIES: this, Bracket, Teams
    //EFFECTS: Adds teams back into the bracket from JSONObject
    public void addTeams(Bracket bracket, JSONObject team) {
        String name = team.getString("Team Name");
        JSONArray jsonArray = team.getJSONArray("Team Members");
        Boolean isSuspended = team.getBoolean("Suspension Status");
        int seeding = team.getInt("Seeding");
        Teams newTeam = new Teams(name, seeding);
        if (isSuspended) {
            newTeam.suspend();
        }
        for (Object json : jsonArray) {
            JSONObject player = (JSONObject) json;
            Player p = addTeamPlayers(player);
            newTeam.addTeamMembers(p);
        }
        bracket.addTeam(newTeam);
    }

    //EFFECTS: return team players into team from JSONObject
    public Player addTeamPlayers(JSONObject player) {
        String name = player.getString("Player Name");
        int age = player.getInt("Player Age");
        return new Player(name, age);
    }

    //MODIFIES: this, Player, Bracket
    //EFFECTS: adds player to the player pool from JSONObject
    public void addPlayers(Bracket bracket, JSONObject player) {
        String name = player.getString("Player Name");
        int age = player.getInt("Player Age");
        Boolean hasTeam = player.getBoolean("Has Team?");
        bracket.addPlayer(new Player(name, age, hasTeam));
    }
}
