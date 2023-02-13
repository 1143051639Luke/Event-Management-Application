package persistence;

import model.Event;
import model.EventCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {


    @BeforeEach
    void runBefore() throws FileNotFoundException {
        EventCenter events = new EventCenter("newName");

        Event event1 = new Event("name1", "location1", 20221207, "artist1",
                100, 18);
        Event event2 = new Event("name1", "location1", 20221210, "artist1",
                100, 3);
        Event event3 = new Event("name2", "location2", 20221210, "artist2",
                1000, 19);
        Event event4 = new Event("name3", "location3", 20221217, "artist3",
                5000, 4);
        Event event5 = new Event("name4", "location4", 20230708, "artist4",
                10, 4);

        events.addEvent(event1);
        events.addEvent(event2);
        events.addEvent(event3);
        events.addEvent(event4);
        events.addEvent(event5);

        JsonWriter writer = new JsonWriter("./data/testReaderGeneralEventCollection.json");
        writer.open();
        writer.write(events);
        writer.close();

        EventCenter secondEvents = new EventCenter("newNameForEmpty");
        JsonWriter secondWriter = new JsonWriter("./data/testReaderEmptyEventCollection.json");
        secondWriter.open();
        secondWriter.write(secondEvents);
        secondWriter.close();
    }


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            EventCenter events = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyEventCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyEventCollection.json");
        try {
            EventCenter events = reader.read();
            assertEquals("newNameForEmpty", events.getName());
            assertEquals(0, events.getEvents().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralEventCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralEventCollection.json");
        try {
            EventCenter events = reader.read();
            assertEquals("newName", events.getName());
            List<Event> things = events.getEvents();
            assertEquals(5, things.size());
            checkEvent("name1", "location1", 20221207, "artist1", 100, 18,
                    things.get(0));
            checkEvent("name1", "location1", 20221210, "artist1", 100, 3,
                    things.get(1));
            checkEvent("name2", "location2", 20221210, "artist2", 1000, 19,
                    things.get(2));
            checkEvent("name3", "location3", 20221217, "artist3", 5000, 4,
                    things.get(3));
            checkEvent("name4", "location4", 20230708, "artist4", 10, 4,
                    things.get(4));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}