package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class EventCenterTest {
    private Event event1;
    private Event event2;
    private Event event3;
    private Event event4;
    private Event event5;
    private EventCenter emptyEvents;
    private EventCenter events;



    @BeforeEach
    void runBefore() {
        this.event1 = new Event("name1", "location1", 20221207, "artist1",
                100, 18);
        this.event2 = new Event("name1", "location1", 20221210, "artist1",
                100, 3);
        this.event3 = new Event("name2", "location2", 20221210, "artist2",
                1000, 19);
        this.event4 = new Event("name3", "location3", 20221217, "artist3",
                5000, 4);
        this.event5 = new Event("name4", "location4", 20230708, "artist4",
                10, 4);
        this.emptyEvents = new EventCenter("name");
        this.events = new EventCenter("name");

        events.addEvent(event1);
        events.addEvent(event2);
        events.addEvent(event3);
        events.addEvent(event4);
        events.addEvent(event5);
    }

    @Test
    void testConstructor() {
        EventCenter constructor = new EventCenter("name");
        assertEquals("name", constructor.getName());
        assertEquals(0, constructor.getEvents().size());
    }

    @Test
    void testAddEvent() {
        assertEquals(0, emptyEvents.getEvents().size());
        emptyEvents.addEvent(event1);
        assertEquals(1, emptyEvents.getEvents().size());
        assertEquals(event1, emptyEvents.getEvents().get(0));
        assertEquals("name", emptyEvents.getName());
    }
    @Test
    void testRemoveEvent() {
        assertEquals(5, events.getEvents().size());
        events.removeEvent("name1", "location1", 20221207, "artist1", 100,
                18);
        assertEquals(4, events.getEvents().size());
        assertEquals("name", events.getName());
    }



    @Test
    void testFindEventSucceed() {
        assertEquals(event1, events.findEvent("name1", "location1", 20221207, "artist1",
                100, 18));
        assertEquals(event2, events.findEvent("name1", "location1", 20221210, "artist1",
                100, 3));
        assertEquals(event3, events.findEvent("name2", "location2", 20221210, "artist2",
                1000, 19));
        assertEquals(event4, events.findEvent("name3", "location3", 20221217, "artist3",
                5000, 4));
        assertEquals(event5, events.findEvent("name4", "location4", 20230708, "artist4",
                10, 4));
    }

    @Test
    void testFindEventFailed() {
        assertNull(events.findEvent("name100", "location1", 20221207, "artist1",
                100, 18));
        assertNull(events.findEvent("name1", "location100", 20221210, "artist1",
                100, 3));
        assertNull(events.findEvent("name2", "location2", 2022121000, "artist2",
                1000, 19));
        assertNull(events.findEvent("name3", "location3", 20221217, "artist300",
                5000, 4));
        assertNull(events.findEvent("name4", "location4", 20230708, "artist4",
                10, 400));
        assertNull(events.findEvent("name4", "location4", 20230708, "artist4",
                100000, 4));
    }

    @Test
    void testFindEventButNone() {
        assertNull(events.findEvent("name5", "location4", 20230708, "artist4",
                10, 4));
    }


    @Test
    void testEventsWithSameName() {
        emptyEvents = events.eventsWithSameName("name1");
        EventCenter sameNameEvents = new EventCenter("newName");
        sameNameEvents.addEvent(event1);
        sameNameEvents.addEvent(event2);
        assertEquals(2, emptyEvents.getEvents().size());
        assertEquals("newName", sameNameEvents.getName());
        assertEquals("name", emptyEvents.getName());
        assertEquals(sameNameEvents.getEvents(), sameNameEvents.getEvents());
        assertEquals(event1, emptyEvents.getEvents().get(0));
        assertEquals(event2, emptyEvents.getEvents().get(1));
    }
    @Test
    void testEventsWithSameNameWithNoSuchName() {
        emptyEvents = events.eventsWithSameName("name100");
        assertEquals("name", emptyEvents.getName());
        assertEquals(0, emptyEvents.getEvents().size());
    }



    @Test
    void testEventsWithSameLocation() {
        emptyEvents = events.eventsWithSameLocation("location1");
        EventCenter sameNameLocation = new EventCenter("newName");
        sameNameLocation.addEvent(event1);
        sameNameLocation.addEvent(event2);
        assertEquals(sameNameLocation.getEvents(), emptyEvents.getEvents());
        assertEquals(2, emptyEvents.getEvents().size());
        assertEquals(event1, emptyEvents.getEvents().get(0));
        assertEquals(event2, emptyEvents.getEvents().get(1));
        assertEquals("newName", sameNameLocation.getName());
        assertEquals("name", emptyEvents.getName());
    }
    @Test
    void testEventsWithSameNameWithNoSuchLocation() {
        emptyEvents = events.eventsWithSameLocation("Location100");
        assertEquals(0, emptyEvents.getEvents().size());
        assertEquals("name", emptyEvents.getName());
    }



    @Test
    void testEventsWithSameDate() {
        emptyEvents = events.eventsWithSameDate(20221210);
        EventCenter sameNameDate = new EventCenter("newName");
        sameNameDate.addEvent(event2);
        sameNameDate.addEvent(event3);
        assertEquals(sameNameDate.getEvents(), emptyEvents.getEvents());
        assertEquals(2, emptyEvents.getEvents().size());
        assertEquals(event2, emptyEvents.getEvents().get(0));
        assertEquals(event3, emptyEvents.getEvents().get(1));
        assertEquals("newName", sameNameDate.getName());
        assertEquals("name", emptyEvents.getName());
    }
    @Test
    void testEventsWithSameNameWithNoSuchDate() {
        emptyEvents = events.eventsWithSameDate(20240101);
        assertEquals(0, emptyEvents.getEvents().size());
        assertEquals("name", emptyEvents.getName());
    }



    @Test
    void testEventsWithSameArtist() {
        emptyEvents = events.eventsWithSameArtist("artist1");
        EventCenter sameNameArtist = new EventCenter("newName");
        sameNameArtist.addEvent(event1);
        sameNameArtist.addEvent(event2);
        assertEquals(sameNameArtist.getEvents(), emptyEvents.getEvents());
        assertEquals(2, emptyEvents.getEvents().size());
        assertEquals(event1, emptyEvents.getEvents().get(0));
        assertEquals(event2, emptyEvents.getEvents().get(1));
        assertEquals("newName", sameNameArtist.getName());
        assertEquals("name", emptyEvents.getName());
    }
    @Test
    void testEventsWithSameArtistButNone() {
        emptyEvents = events.eventsWithSameArtist("artist10");
        assertEquals(0, emptyEvents.getEvents().size());
        assertEquals("name", emptyEvents.getName());
    }



    @Test
    void testEventsUnderThePrice() {
        emptyEvents = events.eventsUnderThePrice(200);
        EventCenter underThePrice = new EventCenter("newName");
        underThePrice.addEvent(event1);
        underThePrice.addEvent(event2);
        underThePrice.addEvent(event5);
        assertEquals(underThePrice.getEvents(), emptyEvents.getEvents());
        assertEquals(3, emptyEvents.getEvents().size());
        assertEquals(event1, emptyEvents.getEvents().get(0));
        assertEquals(event2, emptyEvents.getEvents().get(1));
        assertEquals(event5, emptyEvents.getEvents().get(2));
        assertEquals("newName", underThePrice.getName());
        assertEquals("name", emptyEvents.getName());
    }
    @Test
    void testEventsUnderThePriceButNone() {
        emptyEvents = events.eventsUnderThePrice(5);
        assertEquals(0, emptyEvents.getEvents().size());
        assertEquals("name", emptyEvents.getName());
    }




    @Test
    void testEventsUnderTheAge () {
        emptyEvents = events.eventsUnderTheAge(18);
        EventCenter underTheAge = new EventCenter("newName");
        underTheAge.addEvent(event1);
        underTheAge.addEvent(event2);
        underTheAge.addEvent(event4);
        underTheAge.addEvent(event5);
        assertEquals(underTheAge.getEvents(), emptyEvents.getEvents());
        assertEquals(4, emptyEvents.getEvents().size());
        assertEquals(event1, emptyEvents.getEvents().get(0));
        assertEquals(event2, emptyEvents.getEvents().get(1));
        assertEquals(event4, emptyEvents.getEvents().get(2));
        assertEquals(event5, emptyEvents.getEvents().get(3));
        assertEquals("newName", underTheAge.getName());
        assertEquals("name", emptyEvents.getName());
    }
    @Test
    void testEventsUnderTheAgeButNone () {
        emptyEvents = events.eventsUnderTheAge(2);
        assertEquals(0, emptyEvents.getEvents().size());
        assertEquals("name", emptyEvents.getName());
    }

    @Test
    void testSortingByDateWithNoDuplicate () {
        EventCenter eventWithNWrongOrder = new EventCenter("newName1");
        EventCenter eventWithNRightOrder = new EventCenter("newName2");

        eventWithNWrongOrder.addEvent(event5);
        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event1);
        eventWithNWrongOrder.addEvent(event4);

        eventWithNRightOrder.addEvent(event1);
        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event4);
        eventWithNRightOrder.addEvent(event5);
        eventWithNWrongOrder.sortingByDate();
        assertEquals(eventWithNRightOrder.getEvents(), eventWithNWrongOrder.getEvents());
        assertEquals("newName1", eventWithNWrongOrder.getName());
        assertEquals("newName2", eventWithNRightOrder.getName());
    }

    @Test
    void testSortingByDateWithDuplicate () {
        EventCenter eventWithNWrongOrder = new EventCenter("newName1");
        EventCenter eventWithNRightOrder = new EventCenter("newName2");

        eventWithNWrongOrder.addEvent(event5);
        eventWithNWrongOrder.addEvent(event4);
        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event3);
        eventWithNWrongOrder.addEvent(event1);

        eventWithNRightOrder.addEvent(event1);
        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event3);
        eventWithNRightOrder.addEvent(event4);
        eventWithNRightOrder.addEvent(event5);
        eventWithNWrongOrder.sortingByDate();
        assertEquals(eventWithNRightOrder.getEvents(), eventWithNWrongOrder.getEvents());
        assertEquals("newName1", eventWithNWrongOrder.getName());
        assertEquals("newName2", eventWithNRightOrder.getName());
    }

    @Test
    void testSortingByDateWithAllDuplicate () {
        EventCenter eventWithNWrongOrder = new EventCenter("newName1");
        EventCenter eventWithNRightOrder = new EventCenter("newName2");

        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event3);
        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event3);
        eventWithNWrongOrder.addEvent(event3);

        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event3);
        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event3);
        eventWithNRightOrder.addEvent(event3);
        eventWithNWrongOrder.sortingByDate();
        assertEquals(eventWithNRightOrder.getEvents(), eventWithNWrongOrder.getEvents());
        assertEquals("newName1", eventWithNWrongOrder.getName());
        assertEquals("newName2", eventWithNRightOrder.getName());
    }

    @Test
    void testSortingByPriceWithNoDuplicate () {
        EventCenter eventWithNWrongOrder = new EventCenter("newName1");
        EventCenter eventWithNRightOrder = new EventCenter("newName2");

        eventWithNWrongOrder.addEvent(event3);
        eventWithNWrongOrder.addEvent(event4);
        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event5);

        eventWithNRightOrder.addEvent(event5);
        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event3);
        eventWithNRightOrder.addEvent(event4);
        eventWithNWrongOrder.sortingByPrice();
        assertEquals(eventWithNRightOrder.getEvents(), eventWithNWrongOrder.getEvents());
        assertEquals("newName1", eventWithNWrongOrder.getName());
        assertEquals("newName2", eventWithNRightOrder.getName());
    }

    @Test
    void testSortingByPriceWithDuplicate () {
        EventCenter eventWithNWrongOrder = new EventCenter("newName1");
        EventCenter eventWithNRightOrder = new EventCenter("newName2");

        eventWithNWrongOrder.addEvent(event5);
        eventWithNWrongOrder.addEvent(event1);
        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event4);
        eventWithNWrongOrder.addEvent(event3);

        eventWithNRightOrder.addEvent(event5);
        eventWithNRightOrder.addEvent(event1);
        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event3);
        eventWithNRightOrder.addEvent(event4);
        eventWithNWrongOrder.sortingByPrice();
        assertEquals(eventWithNRightOrder.getEvents(), eventWithNWrongOrder.getEvents());
        assertEquals("newName1", eventWithNWrongOrder.getName());
        assertEquals("newName2", eventWithNRightOrder.getName());
    }

    @Test
    void testSortingByPriceWithAllDuplicate () {
        EventCenter eventWithNWrongOrder = new EventCenter("newName1");
        EventCenter eventWithNRightOrder = new EventCenter("newName2");

        eventWithNWrongOrder.addEvent(event1);
        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event1);
        eventWithNWrongOrder.addEvent(event2);
        eventWithNWrongOrder.addEvent(event2);

        eventWithNRightOrder.addEvent(event1);
        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event1);
        eventWithNRightOrder.addEvent(event2);
        eventWithNRightOrder.addEvent(event2);
        eventWithNWrongOrder.sortingByPrice();
        assertEquals(eventWithNRightOrder.getEvents(), eventWithNWrongOrder.getEvents());
        assertEquals("newName1", eventWithNWrongOrder.getName());
        assertEquals("newName2", eventWithNRightOrder.getName());
    }

    @Test
    void testLogForSortingByDate() {
        EventLog el = EventLog.getInstance();
        el.clear();
        events.sortingByDate();
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("Event sorting by date method is used", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForSortingByPrice() {
        EventLog el = EventLog.getInstance();
        el.clear();
        events.sortingByPrice();
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("Event sorting by price method is used", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForAddEvent() {
        EventLog el = EventLog.getInstance();
        el.clear();
        events.addEvent(event1);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An Event: " + event1.toString() + " is added to Log", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForRemoveEvent() {
        EventLog el = EventLog.getInstance();
        el.clear();
        events.removeEvent("name1", "location1", 20221207, "artist1", 100,
                18);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An Event: " + event1.toString() + " is removed from Log", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForEventWithSameName() {
        String name = "name100";
        EventLog el = EventLog.getInstance();
        el.clear();
        events.eventsWithSameName(name);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An event searching by name: "
                + name + " is happened", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForEventWithSameLocation() {
        String location = "location100";
        EventLog el = EventLog.getInstance();
        el.clear();
        events.eventsWithSameLocation(location);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An event searching by location: "
                + location + " is happened", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForEventWithSameDate() {
        int date = 1;
        EventLog el = EventLog.getInstance();
        el.clear();
        events.eventsWithSameDate(date);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An event searching by date: "
                + date + " is happened", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForEventWithSameArtist() {
        String artist = "Artist";
        EventLog el = EventLog.getInstance();
        el.clear();
        events.eventsWithSameArtist(artist);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An event searching by artist: "
                + artist + " is happened", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForEventUnderThePrice() {
        int price = 1;
        EventLog el = EventLog.getInstance();
        el.clear();
        events.eventsUnderThePrice(price);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An event searching by price: "
                + price + " is happened", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    void testLogForEventUnderTheAge() {
        int age = 1;
        EventLog el = EventLog.getInstance();
        el.clear();
        events.eventsUnderTheAge(age);
        Iterator<EventL> itr = el.iterator();
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertEquals("An event searching by age: "
                + age + " is happened", itr.next().getDescription());
        assertFalse(itr.hasNext());

        System.out.println(events.fun(5));

    }
}