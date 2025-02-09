package model;

// Represents a player with a name, age, and if he's a part of a team


import org.json.JSONObject;
import persistence.JsonWriter;
import persistence.Writable;

import java.util.Objects;

public class Player implements Writable {

    private String name;
    private int age;
    private boolean hasTeam;



    //REQUIRES: Age >= 0
    //EFFECTS: creates new player with name and age and hasTeam set to false;
    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        this.hasTeam = false;
    }

    //REQUIRES: Age >= 0
    //EFFECTS: creates new player with name and age and hasTeam set to false;
    public Player(String name, int age, Boolean hasTeam) {
        this.name = name;
        this.age = age;
        this.hasTeam = hasTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;

        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    //EFFECTS: returns as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Player Name", name);
        json.put("Player Age", age);
        json.put("Has Team?", hasTeam);
        return json;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean hasTeam() {
        return hasTeam;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void teamChange() {
        hasTeam = !hasTeam;
    }

}
