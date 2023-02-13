package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class EventCenter implements Writable {
    private String name;
    private final List<Event> events;
    

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: construct a list of events, no duplicated event is allowed
    public EventCenter(String name) {
        this.name = name;
        this.events = new ArrayList<>();
    }

    int fun(int n) {
        int r = 0;
        int q = 8 * n;
        for (int i = 0; i <= q; i = i + 4) {
            for (int j = 0; j < i; j++) {
                r++;
            }
        }
        return r;
    }



    public List<Event> getEvents() {
        return this.events;
    }

    public String getName() {
        return this.name;
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: sort the list in terms of date(from close to far). If same, remain the original order
    public void sortingByDate() {
        Collections.sort(events, new EventDateComparator());
        EventLog.getInstance().logEvent(new EventL("Event sorting by date method is used"));
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: sort the list in terms of price(from lowest to highest). If same, remain the original order
    public void sortingByPrice() {
        Collections.sort(events, new EventPriceComparator());
        EventLog.getInstance().logEvent(new EventL("Event sorting by price method is used"));

    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: add an event to EventCenter
    public void addEvent(Event x) {
        this.events.add(x);
        EventLog.getInstance().logEvent(new EventL("An Event: " + x.toString() + " is added to events"));
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: remove an event from EventCenter
    public void removeEvent(String name, String location, int date, String artist, int price, int ageRestriction) {
        Event eventTemp = findEvent(name, location, date, artist, price, ageRestriction);
        this.events.remove(eventTemp);

        EventLog.getInstance().logEvent(new EventL("An Event: "
                + eventTemp.toString() + " is removed from events"));
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return the same event in the list
    public Event findEvent(String name, String location, int date, String artist, int price, int ageRestriction) {
        for (Event i : this.events) {
            if (name.equals(i.getName()) && location.equals(i.getLocation())
                                         && date == i.getDate()
                                         && artist.equals(i.getArtist())
                                         && price == i.getPrice()
                                         && ageRestriction == i.getAgeRestriction()) {
                return i;
            }
        }
        return null;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return a list of events with the given name
    public EventCenter eventsWithSameName(String name) {
        EventCenter eventsWithSameName = new EventCenter("name");
        for (Event i : this.events) {
            if (name.equals(i.getName())) {
                eventsWithSameName.addEvent(i);
            }
        }
        EventLog.getInstance().logEvent(new EventL("An event searching by name: "
                + name + " is happened"));
        return eventsWithSameName;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return a list of events with the given location
    public EventCenter eventsWithSameLocation(String location) {
        EventCenter eventsWithSameLocation = new EventCenter("name");
        for (Event i : this.events) {
            if (location.equals(i.getLocation())) {
                eventsWithSameLocation.addEvent(i);
            }
        }
        EventLog.getInstance().logEvent(new EventL("An event searching by location: "
                + location + " is happened"));

        return eventsWithSameLocation;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return a list of events with the given date
    public EventCenter eventsWithSameDate(int date) {
        EventCenter eventsWithSameDate = new EventCenter("name");
        for (Event i : this.events) {
            if (date == i.getDate()) {
                eventsWithSameDate.addEvent(i);
            }
        }
        EventLog.getInstance().logEvent(new EventL("An event searching by date: "
                + date + " is happened"));

        return eventsWithSameDate;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return a list of events with the given artist
    public EventCenter eventsWithSameArtist(String artist) {
        EventCenter eventsWithSameArtist = new EventCenter("name");
        for (Event i : this.events) {
            if (artist.equals(i.getArtist())) {
                eventsWithSameArtist.addEvent(i);
            }
        }
        EventLog.getInstance().logEvent(new EventL("An event searching by artist: "
                + artist + " is happened"));
        return eventsWithSameArtist;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return a list of events under the given price
    public EventCenter eventsUnderThePrice(int price) {
        EventCenter eventsUnderThePrice = new EventCenter("name");
        for (Event i : this.events) {
            if (i.getPrice() <= price) {
                eventsUnderThePrice.addEvent(i);
            }
        }
        EventLog.getInstance().logEvent(new EventL("An event searching by price: "
                + price + " is happened"));
        return eventsUnderThePrice;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return a list of events with the given age restriction
    public EventCenter eventsUnderTheAge(int age) {
        EventCenter eventsUnderTheAge = new EventCenter("name");
        for (Event i : this.events) {
            if (i.getAgeRestriction() <= age) {
                eventsUnderTheAge.addEvent(i);
            }
        }
        EventLog.getInstance().logEvent(new EventL("An event searching by age: "
                + age + " is happened"));
        return eventsUnderTheAge;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("things", thingiesToJson());
        return json;
    }

    // EFFECTS: returns Event in this EventCenter as a JSON array
    private JSONArray thingiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Event t : events) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

