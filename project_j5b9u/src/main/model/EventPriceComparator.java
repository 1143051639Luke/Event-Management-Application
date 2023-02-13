package model;

import java.util.Comparator;

public class EventPriceComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        return o1.getPrice() - o2.getPrice();
    }
}
