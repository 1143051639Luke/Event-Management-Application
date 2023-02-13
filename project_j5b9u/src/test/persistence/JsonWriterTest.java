package persistence;

import model.Event;
import model.EventCenter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            EventCenter events = new EventCenter("name");
            Event event1 = new Event("name1", "location1", 20221207, "artist1",
                    100, 18);
            events.addEvent(event1);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyEventCollection() {
        try {
            EventCenter events = new EventCenter("newNameForEmpty");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyEventCollection.json");
            writer.open();
            writer.write(events);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyEventCollection.json");
            events = reader.read();
            assertEquals(0, events.getEvents().size());
            assertEquals("newNameForEmpty", events.getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralEventCollection() {
        try {
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

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralEventCollection.json");
            writer.open();
            writer.write(events);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralEventCollection.json");
            events = reader.read();
            assertEquals("newName", events.getName());
            List<Event> eventList = events.getEvents();
            assertEquals(5, eventList.size());
            checkEvent("name1", "location1", 20221207, "artist1", 100, 18,
                    eventList.get(0));
            checkEvent("name1", "location1", 20221210, "artist1", 100, 3,
                    eventList.get(1));
            checkEvent("name2", "location2", 20221210, "artist2", 1000, 19,
                    eventList.get(2));
            checkEvent("name3", "location3", 20221217, "artist3", 5000, 4,
                    eventList.get(3));
            checkEvent("name4", "location4", 20230708, "artist4", 10, 4,
                    eventList.get(4));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}