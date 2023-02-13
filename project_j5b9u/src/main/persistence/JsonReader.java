package persistence;

import model.EventCenter;
import model.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads EventCollection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads EventCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public EventCenter read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseEventCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses EventCenter from JSON object and returns it
    private EventCenter parseEventCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        EventCenter events = new EventCenter(name);
        addEvents(events, jsonObject);
        return events;
    }

    // MODIFIES: events
    // EFFECTS: parses things from JSON object and adds them to EventCollection
    private void addEvents(EventCenter events, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("things");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addEvent(events, nextThingy);
        }
    }

    // MODIFIES: events
    // EFFECTS: parses event from JSON object and adds it to EventCollection
    private void addEvent(EventCenter events, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        int date = jsonObject.getInt("date");
        String artist = jsonObject.getString("artist");
        int price = jsonObject.getInt("price");
        int ageRestriction = jsonObject.getInt("ageRestriction");
        Event event = new Event(name, location, date, artist, price, ageRestriction);
        events.addEvent(event);
    }
}