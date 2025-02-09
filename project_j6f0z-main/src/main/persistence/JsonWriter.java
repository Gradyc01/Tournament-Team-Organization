package persistence;

import model.Bracket;
import model.Event;
import model.EventLog;
import org.json.JSONObject;

import java.io.*;
// Represents a writer that writes JSON representation of bracket to file

public class JsonWriter {

    private static final int INDENT = 4;
    private String destination;
    private PrintWriter writer;

    //EFFECTS: writes to the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //         be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes bracket into a JSON file
    public void write(Bracket bracket) {
        JSONObject json = bracket.toJson();
        EventLog.getInstance().logEvent(new Event("Saved " + bracket.getBracketName() + " to " + destination));
        saveToFile(json.toString(INDENT));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes the string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
