package ui;

import model.Event;
import model.EventCenter;
import model.EventL;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    private JRadioButton name = new JRadioButton("Name",true);
    private JRadioButton location = new JRadioButton("Location");
    private JRadioButton date = new JRadioButton("Date");
    private JRadioButton artist = new JRadioButton("Artist");
    private JRadioButton price = new JRadioButton("Under What Price?");
    private JRadioButton age = new JRadioButton("Under What AgeRestriction?");
    private JRadioButton sortPrice = new JRadioButton("Sort By Price");
    private JRadioButton sortDate = new JRadioButton("Sort By Date");
    private JRadioButton noSort = new JRadioButton("No Need Sorting",true);

    private JButton searchButton = new JButton("SEARCH AND SORT");
    private JButton displayButton = new JButton("DISPLAY ALL");
    private JButton addButton = new JButton("ADD");
    private JButton removeButton = new JButton("REMOVE");
    private JButton saveButton = new JButton("SAVE");
    private JButton reloadButton = new JButton("RELOAD");

    private JTextField searchingField = new JTextField("Enter KeyWord");
    private JTextField nameField = new JTextField("Enter Name");
    private JTextField locationField = new JTextField("Enter Location");
    private JTextField dateField = new JTextField("Enter YYYYMMDD");
    private JTextField artistField = new JTextField("Enter Artist");
    private JTextField priceField = new JTextField("Enter Price");
    private JTextField ageField = new JTextField("Enter AgeRestriction");


    private EventCenter events = new EventCenter("EventCollection");
    private EventCenter result = new EventCenter("Result");
    private static final String JSON_STORE = "./data/EventCollectionGUI.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: running the flash screen, assembling all components of GUI and then output
    public GUI() {
        flashing();
        setTitle("EventApp");
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - 722) / 2, ((int) d.getHeight() - 401) / 2,1000,885);
        setLayout(null);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        add(organizerPanel()).setBounds(15,25,470,725);
        add(generalPanel()).setBounds(515,25,470,725);
        add(saveButton).setBounds(15,760,485,100);
        add(reloadButton).setBounds(500,760,485,100);
        buttonUsing();



        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        addingWindowListener();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: output all the EventLog to console when the GUI is closed
    public void addingWindowListener() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (EventL event : EventLog.getInstance()) {
                    System.out.println(event.toString() + "\n");
                }
                System.exit(EXIT_ON_CLOSE);
            }
        });
    }


    /*
        Title: How to make a splash screen for GUI?
        Name: kaushik dey
        StackOverFlow Link: https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-
        for-gui/57380639?r=SearchResults&s=1%7C8.0523#57380639
        */
    //REQUIRES:
    //MODIFIES:
    //EFFECTS: making flash screen
    public void flashing() {
        JWindow j = new JWindow();

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        Icon img = new ImageIcon("data/flashscreen.gif");
        JLabel label = new JLabel(img);
        j.getContentPane().add(label);
        j.setBounds(((int) d.getWidth() - 375) / 2, ((int) d.getHeight() - 200) / 2, 200, 1500);
        j.pack();
        j.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        j.setVisible(false);
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: assembling organizerPanel
    public JPanel organizerPanel() {
        JPanel panelOrganizer = new JPanel();
        panelOrganizer.setLayout(null);
        panelOrganizer.setBackground(Color.lightGray);
        panelOrganizer.add(new JLabel("ORGANIZER")).setBounds(205,40,100,20);
        panelOrganizer.add(nameField).setBounds(160,80,150,50);
        panelOrganizer.add(locationField).setBounds(160,160,150,50);
        panelOrganizer.add(dateField).setBounds(160,240,150,50);
        panelOrganizer.add(artistField).setBounds(160,320,150,50);
        panelOrganizer.add(priceField).setBounds(160,400,150,50);
        panelOrganizer.add(ageField).setBounds(160,480,150,50);
        panelOrganizer.add(addButton).setBounds(30,580,160,75);
        panelOrganizer.add(removeButton).setBounds(280,580,160,75);

        return panelOrganizer;
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: assembling generalPanel
    public JPanel generalPanel() {
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(null);
        panelGeneral.setBackground(Color.lightGray);
        panelGeneral.add(new JLabel("GENERAL")).setBounds(210,40,100,20);

        groupingButton(name,location,date,artist,price,age,sortPrice,sortDate,noSort);

        panelGeneral.add(name).setBounds(40,110,150,20);;
        panelGeneral.add(location).setBounds(40,160,150,20);
        panelGeneral.add(date).setBounds(40,210,150,20);
        panelGeneral.add(artist).setBounds(40,260,150,20);
        panelGeneral.add(price).setBounds(40,310,150,20);
        panelGeneral.add(age).setBounds(40,360,220,20);
        panelGeneral.add(sortPrice).setBounds(320,160,150,20);
        panelGeneral.add(sortDate).setBounds(320,210,150,20);
        panelGeneral.add(noSort).setBounds(320,260,150,20);
        panelGeneral.add(searchButton).setBounds(90,500,300,75);
        panelGeneral.add(displayButton).setBounds(90,625,300,75);
        panelGeneral.add(searchingField).setBounds(35,400,400,30);

        return panelGeneral;
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: grouping JButtons to ButtonGroups respectively
    public void groupingButton(JRadioButton name,JRadioButton location,JRadioButton date,JRadioButton artist,
                                       JRadioButton price, JRadioButton age, JRadioButton sortByPrice,
                                       JRadioButton sortByDate, JRadioButton noSort) {
        ButtonGroup groupForTerms = new ButtonGroup();
        ButtonGroup groupForSort = new ButtonGroup();

        groupForTerms.add(name);
        groupForTerms.add(location);
        groupForTerms.add(date);
        groupForTerms.add(artist);
        groupForTerms.add(price);
        groupForTerms.add(age);

        groupForSort.add(sortByPrice);
        groupForSort.add(sortByDate);
        groupForSort.add(noSort);

    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return all the events in String
    public String displayingResult(Event event) {
        return    "Name: "         + event.getName()     + "     "
                + "Location: "     + event.getLocation() + "     "
                + "Date: "         + event.getDate()     + "     "
                + "Event Artist: " + event.getArtist()   + "     "
                + "Event Price: "  + event.getPrice()    + "     "
                + "Restriction: "  + event.getAgeRestriction();
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user input of selecting searching by term
    public void searchByTerm() {
        if (name.isSelected()) {
            eventWithSameName();
        } else if (location.isSelected()) {
            eventWithSameLocation();
        } else if (date.isSelected()) {
            eventWithSameDate();
        } else if (artist.isSelected()) {
            eventWithSameArtist();
        } else if (price.isSelected()) {
            eventBelowPrice();
        } else if (age.isSelected()) {
            eventUnderAge();
        }
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user input of searching by name
    private void eventWithSameName() {
        result = events.eventsWithSameName(searchingField.getText());
        processSorting();
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user input of searching by location
    private void eventWithSameLocation() {
        result = events.eventsWithSameLocation(searchingField.getText());
        processSorting();
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user input of searching by date
    private void eventWithSameDate() {
        result = events.eventsWithSameDate(Integer.parseInt(searchingField.getText()));
        processSorting();
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user input of searching by artist
    private void eventWithSameArtist() {
        result = events.eventsWithSameArtist(searchingField.getText());
        processSorting();
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user input of searching under certain price
    private void eventBelowPrice() {
        result = events.eventsUnderThePrice(Integer.parseInt(searchingField.getText()));
        processSorting();
    }


    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user input of searching under certain age
    private void eventUnderAge() {
        result = events.eventsUnderTheAge(Integer.parseInt(searchingField.getText()));
        processSorting();
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user sorting requesting for searching by term
    public void processSorting() {
        if (sortDate.isSelected()) {
            result.sortingByDate();
        } else if (sortPrice.isSelected()) {
            result.sortingByPrice();
        }
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: process user sorting requesting for displaying all
    public void processSortingForAll() {
        if (sortDate.isSelected()) {
            events.sortingByDate();
        } else if (sortPrice.isSelected()) {
            events.sortingByPrice();
        }
    }


    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: return true if successfully processing user input of posting an event, otherwise false
    public Boolean postAnEvent() {
        Event event;
        int originalSize = events.getEvents().size();
        event = new Event(nameField.getText(), locationField.getText(), Integer.parseInt(dateField.getText()),
                artistField.getText(), Integer.parseInt(priceField.getText()), Integer.parseInt(ageField.getText()));
        events.addEvent(event);

        return !(originalSize == events.getEvents().size());
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: return true if successfully process user input of removing an event, otherwise false
    public Boolean removeAnEvent() {
        int originalSize = events.getEvents().size();
        events.removeEvent(nameField.getText(), locationField.getText(), Integer.parseInt(dateField.getText()),
                artistField.getText(), Integer.parseInt(priceField.getText()), Integer.parseInt(ageField.getText()));

        return !(originalSize == events.getEvents().size());
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: processing all the clicked buttons
    public void buttonUsing() {
        displayButton.setActionCommand("display");
        displayButton.addActionListener(new DisplayAllAction());

        searchButton.setActionCommand("search");
        searchButton.addActionListener(new SearchAction());

        addButton.setActionCommand("add");
        addButton.addActionListener(new AddAction());

        removeButton.setActionCommand("remove");
        removeButton.addActionListener(new RemoveAction());

        saveButton.setActionCommand("save");
        saveButton.addActionListener(new SaveAction());

        reloadButton.setActionCommand("reload");
        reloadButton.addActionListener(new ReloadAction());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: display all the events and output the result in a new frame when displayButton is clicked
    public class DisplayAllAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("display")) {
                JFrame frame = new JFrame("Result");
                frame.setSize(750, 500);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);

                processSortingForAll();

                String[] listData = new String[events.getEvents().size()];
                int i = 0;
                for (Event event : events.getEvents()) {
                    listData[i] = displayingResult(event);
                    i++;
                }

                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setPreferredSize(new Dimension(200,100));
                ListModel listmodel = new DefaultComboBoxModel(listData);
                JList jlist = new JList<>();
                jlist.setModel(listmodel);
                scrollPane.setViewportView(jlist);
                frame.add(scrollPane);
            }
        }
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: search and sort the events and output the result in a new frame when displayButton is clicked
    public class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("search")) {
                JFrame frame = new JFrame("Result");
                frame.setSize(750, 500);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);

                searchByTerm();

                String[] listData = new String[result.getEvents().size()];
                int i = 0;
                for (Event event : result.getEvents()) {
                    listData[i] = displayingResult(event);
                    i++;
                }

                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setPreferredSize(new Dimension(200,100));
                ListModel listmodel = new DefaultComboBoxModel(listData);
                JList jlist = new JList<>();
                jlist.setModel(listmodel);
                scrollPane.setViewportView(jlist);
                frame.add(scrollPane);
            }
        }
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: add the event to events and output the result in a new frame when displayButton is clicked
    public class AddAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("add")) {
                JFrame frame = new JFrame("Result");
                frame.setSize(150, 100);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);

                if (postAnEvent()) {
                    JLabel label = new JLabel("Succeed!");
                    label.setFont(new Font("Verdana", Font.BOLD, 26));
                    frame.add(label).setBounds(500, 250, 100, 50);
                } else {
                    JLabel label = new JLabel("Failed!");
                    label.setFont(new Font("Verdana", Font.BOLD, 26));
                    frame.add(label).setBounds(500, 250, 100, 50);
                }

            }
        }
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: remove the event to events and output the result in a new frame when displayButton is clicked
    public class RemoveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("remove")) {
                JFrame frame = new JFrame("Result");
                frame.setSize(150, 100);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);

                if (removeAnEvent()) {
                    JLabel label = new JLabel("Succeed!");
                    label.setFont(new Font("Verdana", Font.BOLD, 26));
                    frame.add(label).setBounds(500, 250, 100, 50);
                } else {
                    JLabel label = new JLabel("Failed!");
                    label.setFont(new Font("Verdana", Font.BOLD, 26));
                    frame.add(label).setBounds(500, 250, 100, 50);
                }

            }
        }
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: save events to Json and output the result in a new frame when displayButton is clicked
    public class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("save")) {
                JFrame frame = new JFrame("Result");
                frame.setSize(150, 100);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
                JLabel label = new JLabel("Succeed");

                try {
                    jsonWriter.open();
                    jsonWriter.write(events);
                    jsonWriter.close();
                } catch (FileNotFoundException exception) {
                    label.setText("Failed!");
                }
                label.setFont(new Font("Verdana", Font.BOLD, 26));
                frame.add(label).setBounds(500, 250, 100, 50);
            }
        }
    }

    //REQUIRES:
    //MODIFIES:this
    //EFFECTS: reload events from Json and output the result in a new frame when displayButton is clicked
    public class ReloadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("reload")) {
                JFrame frame = new JFrame("Result");
                frame.setSize(150, 100);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
                JLabel label = new JLabel("Succeed");

                try {
                    events = jsonReader.read();
                }  catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
                label.setFont(new Font("Verdana", Font.BOLD, 26));
                frame.add(label).setBounds(500, 250, 100, 50);
            }
        }
    }


    public static void main(String[] args) {
        new GUI();
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
//                events.addEvent(event5);
//                events.addEvent(event1);
//                events.addEvent(event4);
//                events.addEvent(event2);
//                events.addEvent(event3);
//
