package persistence;

import org.json.JSONObject;

//Writes as JSON
public interface Writable {

    //EFFECTS: returns as JSONObject
    JSONObject toJson();
}
