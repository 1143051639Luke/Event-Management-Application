package persistence;

import model.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    /*
    Title: JsonSerializationDemo
    Name: CPSC210
    GitHub Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    */


    protected void checkEvent(String name, String location, int date,String artist,
    int price, int ageRestriction, Event event) {
        assertEquals(name, event.getName());
        assertEquals(location, event.getLocation());
        assertEquals(date, event.getDate());
        assertEquals(artist, event.getArtist());
        assertEquals(price, event.getPrice());
        assertEquals(ageRestriction, event.getAgeRestriction());
    }
}
