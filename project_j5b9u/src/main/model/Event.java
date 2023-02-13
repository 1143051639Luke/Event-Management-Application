package model;


import org.json.JSONObject;
import persistence.Writable;


public class Event implements Writable {
    private final String name;
    private final String location;
    private final int date;
    private final String artist;
    private final int price;
    private final int ageRestriction;


    @Override
    public String toString() {
        return name + " " + location + " " + date + " " + artist + " " + price + " " + ageRestriction;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: construct an event with its name, location, date, artist, price and age restriction
    public Event(String name, String location, int date, String artist, int price,
                 int ageRestriction) {

        this.name = name;
        this.location = location;
        this.date = date;
        this.artist = artist;
        this.price = price;
        this.ageRestriction = ageRestriction;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }

    public int getPrice() {
        return price;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("location", location);
        json.put("date", date);
        json.put("artist", artist);
        json.put("price", price);
        json.put("ageRestriction", ageRestriction);
        return json;
    }
}