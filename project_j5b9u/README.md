# Event Calendar

## An application that  provides a publishing channel for organizers and an event searching(in terms of name,location, dates, artist and price) for generals.

**Why am I interested to me?**

For me, I like to release my stress by attending various performances or events. 
Therefore, I want to create an app for myself where I can check out recent events. 
With this app, whenever I want to attend an event, 
I can quickly find an appropriate event (in terms of location, dates, artist and price) 
for me. However, why not expand the idea and make the app available to event organizers as well. 
Event organizers could add their own event information,
and users (not just myself anymore!) can go and look up information about each event too!

**Who will use it?**

1.Event Organizers

2.People who wants to find some events to participate


**What will the application do?**

*For organizers*:

- Post events(name, location, dates, artist, price, age restriction)
- Remove(cancel) events in the app.
- Save the events that were posted

*For Generals*:

- Search the event by name
- Find an event in terms of location
- Find an event in terms of dates
- Find an event in terms of artist
- Find an event in terms of price
- Find an event in terms of age restriction
- Sort the result in terms of price
- Sort the result in terms of time
- View all the events
- Reload the events from file


## User Stories
*Context*:
- As a user(organizers), I want to be able to add an event to the app.
- As a user(organizers), I want to be able to remove a posted event.
- As a user(generals), I want to be able to search an event by name.
- As a user(generals), I want to be able to find an event in terms of location, dates, artist, price or age restriction.
- As a user(generals), I want to be able to sort the result in terms of price or time.
- As a user(generals), I want to be able to view all the events.
- As a user, I want to be able to save the events that I posted.
- As a user, I want to be able to reload the events from file.


## Instruction for Grader
- You can generate the first required event related to adding Xs to a Y by check the RadioButton
(name,location,date,artist,price or ageRestriction) and then click search button.
- You can generate the second required event related to adding Xs to a Y by check the RadioButton
  (sort by date, sort by price or no need sorting) and then click search or display button.
- You can locate my visual component by reopen the GUI
- You can save the state of my application by click the SAVE button.
- You can reload the state of my application by click the REMOVE button. 

## Phase 4: Task 2
One representative sample is adding X to Y(a list of X):


  Wed Nov 30 15:46:34 PST 2022

  An Event: 1 1 1 1 1 1 is added to Log

## Phase 4: Task 3
After finishing my UML and getting to know the singleton pattern, I think it is worth to try to make my eventCenter to
be a singleton pattern. The reason for that is making all the event into just one eventCenter(a list of events) may
increase the cohesion and make the system more organised. Like what the EventLog is doing, only one instance can exist
in program and only a single instance is passed around where it is needed. 

Besides, I found I didn't use exceptions. That might also be the aspect that I want to improve. By using
exception, It can make my application more stable, easier to revise some method in the future,
and easier to debug the application if there are some bugs.