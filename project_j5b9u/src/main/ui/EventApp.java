package ui;

import model.Event;
import model.EventCenter;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class EventApp {
    private EventCenter eventCenter;
    private static final String JSON_STORE = "./data/EventCollection.json";
    private Scanner scanner;
    int operation;
    String content;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;



    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: initialize 5 different events, put all these events into the list and start to run the EventApp
    public EventApp() {
        eventCenter = new EventCenter("EventCollection");
        scanner = new Scanner(System.in);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        startToRun();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input through two section
    public void startToRun() {
        boolean continueOrNot = true;
        while (continueOrNot) {
            System.out.println("\nOrganizer       -----> 1" + "\n"
                             + "General         -----> 2" + "\n"
                             + "Save the file   -----> 3" + "\n"
                             + "Reload the file -----> 4" + "\n"
                             + "or" + "\n"
                             + "enter others to quit");
            scanner = new Scanner(System.in);
            operation = scanner.nextInt();
            if (operation == 1) {
                runForOrganizer();
            } else if (operation == 2) {
                runForGeneral();
            } else if (operation == 3) {
                saveEventCenter();
            } else if (operation == 4) {
                loadEventCenter();
            } else {
                continueOrNot = false;
            }
        }
    }

    // EFFECTS: saves the eventCenter to file
    private void saveEventCenter() {
        try {
            jsonWriter.open();
            jsonWriter.write(eventCenter);
            jsonWriter.close();
            System.out.println("Saved " + eventCenter.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads eventCenter from file
    private void loadEventCenter() {
        try {
            eventCenter = jsonReader.read();
            System.out.println("Loaded " + eventCenter.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input if it is organizer
    public void runForOrganizer() {
        System.out.println("Post an event   -----> 1" + "\n"
                         + "Remove an event -----> 2");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        if (operation == 1) {
            postAnEvent();
        } else if (operation == 2) {
            removeAnEvent();
        }
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input if it is general
    public void runForGeneral() {
        System.out.println("view all the event -----> 1" + "\n"
                         + "search event       -----> 2");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        if (operation == 1) {
            allEvents();
        } else if (operation == 2) {
            searchByTerm();
        }
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: ask user if requiring sort and return 1 for no,2 for sorting by date or 3 for sorting by price
    public int sortRequiring() {
        System.out.println("Before showing your request, do you want to sort it?"
                         + "\nNo            -----> 1"
                         + "\nSort By date  -----> 2"
                         + "\nSort By Price -----> 3");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        return operation;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of posting or removing an event for Name
    public String returnName() {
        System.out.println("name of your event");
        scanner = new Scanner(System.in);
        content = scanner.nextLine();
        return content;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of posting or removing an event for Location
    public String returnLocation() {
        System.out.println("location of your event");
        scanner = new Scanner(System.in);
        content = scanner.nextLine();
        return content;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of posting or removing an event for Date
    public int returnDate() {
        System.out.println("date of your event(enter Integer in the form of YYYYMMDD)");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        return operation;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of posting or removing an event for Artist
    public String returnArtist() {
        System.out.println("artist of your event");
        scanner = new Scanner(System.in);
        content = scanner.nextLine();
        return content;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of posting or removing an event for Price
    public int returnPrice() {
        System.out.println("price of your event(enter Integer)");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        return operation;
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of posting or removing an event for age restriction
    public int returnAgeRestriction() {
        System.out.println("age restriction of your event(enter Integer)");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        return operation;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of posting an event
    public void postAnEvent() {
        Event event;
        int originalSize = eventCenter.getEvents().size();
        String name = returnName();
        String location = returnLocation();
        int date = returnDate();
        String artist =  returnArtist();
        int price = returnPrice();
        int age = returnAgeRestriction();
        event = new Event(name, location, date, artist, price, age);
        eventCenter.addEvent(event);

        if (originalSize == eventCenter.getEvents().size()) {
            System.out.println("Fail to post...");
        } else {
            System.out.println("Done!");
        }
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of removing an event
    public void removeAnEvent() {
        int originalSize = eventCenter.getEvents().size();
        String name = returnName();
        String location = returnLocation();
        int date = returnDate();
        String artist = returnArtist();
        int price = returnPrice();
        int ageRestriction = returnAgeRestriction();
        eventCenter.removeEvent(name, location, date, artist, price, ageRestriction);

        if (originalSize == eventCenter.getEvents().size()) {
            System.out.println("There is no such event...");
        } else {
            System.out.println("Done!");
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: displaying the result to user
    public void displayingResult(EventCenter events) {
        int no = 1;
        for (Event i: events.getEvents()) {
            System.out.println(no + ": " + "Event Name is " + i.getName() + "\n" +  "Event Location is "
                    + i.getLocation() + "\n" + "Event Date is " + i.getDate() +  "\n" + "Event Artist is "
                    + i.getArtist() + "\n" + "Event Price is " + i.getPrice() + "\n" + "Age Restriction is "
                    + i.getAgeRestriction() + "\n");
            no++;
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user sorting requesting
    public void processSorting(EventCenter events) {
        int require = sortRequiring();
        if (require == 2) {
            events.sortingByDate();
            displayingResult(events);
        } else if (require == 3) {
            events.sortingByPrice();
            displayingResult(events);
        } else if (require == 1) {
            displayingResult(events);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of viewing all events
    public void allEvents() {
        processSorting(eventCenter);
        System.out.println("That's all!");
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of selecting searching by term
    public void searchByTerm() {
        System.out.println("Which key word do you want to use for searching?"
                         + "\nName          -----> 1"
                         + "\nLocation      -----> 2"
                         + "\nDate          -----> 3"
                         + "\nArtist        -----> 4"
                         + "\nBelow a Price -----> 5"
                         + "\nUnder an Age  -----> 6");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        if (operation == 1) {
            eventWithSameName();
        } else if (operation == 2) {
            eventWithSameLocation();
        } else if (operation == 3) {
            eventWithSameDate();
        } else if (operation == 4) {
            eventWithSameArtist();
        } else if (operation == 5) {
            eventBelowPrice();
        } else if (operation == 6) {
            eventUnderAge();
        }
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of searching by name
    private void eventWithSameName() {
        System.out.println("Enter the name you want to search");
        scanner = new Scanner(System.in);
        content = scanner.nextLine();
        EventCenter eventWithSameName = eventCenter.eventsWithSameName(content);
        processSorting(eventWithSameName);
        System.out.println("That's all!");
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of searching by location
    private void eventWithSameLocation() {
        System.out.println("Enter the location you want to search");
        scanner = new Scanner(System.in);
        content = scanner.nextLine();
        EventCenter eventWithSameLocation = eventCenter.eventsWithSameLocation(content);
        processSorting(eventWithSameLocation);
        System.out.println("That's all!");
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of searching by date
    private void eventWithSameDate() {
        System.out.println("Enter the date (Integer in the form YYYYMMDD) you want to search");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        EventCenter eventWithSameDate = eventCenter.eventsWithSameDate(operation);
        processSorting(eventWithSameDate);
        System.out.println("That's all!");
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of searching by artist
    private void eventWithSameArtist() {
        System.out.println("Enter the artist you want to search");
        scanner = new Scanner(System.in);
        content = scanner.nextLine();
        EventCenter eventWithSameArtist = eventCenter.eventsWithSameArtist(content);
        processSorting(eventWithSameArtist);
        System.out.println("That's all!");
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of searching under certain price
    private void eventBelowPrice() {
        System.out.println("Enter the max price (Integer) you want to search");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        EventCenter eventBelowPrice = eventCenter.eventsUnderThePrice(operation);
        processSorting(eventBelowPrice);
        System.out.println("That's all!");
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: process user input of searching under certain age
    private void eventUnderAge() {
        System.out.println("Enter the max age (Integer) you want to search");
        scanner = new Scanner(System.in);
        operation = scanner.nextInt();
        EventCenter eventUnderAge = eventCenter.eventsUnderTheAge(operation);
        processSorting(eventUnderAge);
        System.out.println("That's all!");
    }

    public static void main(String[] args) {
        new EventApp();
    }

}


//    Event event1;
//    Event event2;
//    Event event3;
//    Event event4;
//    Event event5;
//        event1 = new Event("name1", "location1", 20221207, "artist1",
//                100, 18);
//                event2 = new Event("name1", "location1", 20221210, "artist1",
//                100, 3);
//                event3 = new Event("name2", "location2", 20221210, "artist2",
//                1000, 19);
//                event4 = new Event("name3", "location3", 20221217, "artist3",
//                5000, 4);
//                event5 = new Event("name4", "location4", 20230708, "artist4",
//                10, 4);
//                eventCenter.addEvent(event1);
//                eventCenter.addEvent(event2);
//                eventCenter.addEvent(event3);
//                eventCenter.addEvent(event4);
//                eventCenter.addEvent(event5);
