package agenda;

import java.time.LocalDate;
import java.util.*;

/**
 * Description : An agenda that stores events
 *
 */
public class Agenda {
    private ArrayList<Event> thoseEvents;

    public Agenda(){
        this.thoseEvents = new ArrayList<>();
    }
    public void addEvent(Event e) {
        thoseEvents.add(e);

    }


    /**
     * Computes the events that occur on a given day
     *
     * @param day the day to i test
     * @return a list of events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        List<Event> thoseEventsDay = new ArrayList<Event>();
        for (int i = 0; i < thoseEvents.size(); i ++){
            if (thoseEvents.get(i).isInDay(day)){
                thoseEventsDay.add(thoseEvents.get(i));
            }
        }
        //
        return thoseEventsDay;
    }

    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     */
    public List<Event> findByTitle(String title) {
        ArrayList<Event> titleList = new ArrayList<Event>();
        for (Event e : thoseEvents){
            if(e.getTitle().equals(title)){
                titleList.add(e);
            }
        }
        return titleList;
    }

    /**
     * Déterminer s’il y a de la place dans l'agenda pour un événement
     */
    public boolean isFreeFor(Event e) {
        Boolean t =true;
        for (Event ev : thoseEvents){
            if (ev.getStart().isBefore(e.getStart())){
                if (ev.getStart().plus(ev.getDuration()).isAfter(e.getStart()) ){
                    t=false;
                }
            } else if (ev.getStart().equals(e.getStart())){
                t=false;
            }
            if (ev.getStart().isAfter(e.getStart())){
                if (e.getStart().plus(e.getDuration()).isAfter(ev.getStart()) ){
                    t=false;
                }}
        }
        return t;
    }


}