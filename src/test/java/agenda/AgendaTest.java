package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class AgendaTest {
    Agenda agenda;

    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);

    // January 5, 2021
    LocalDate jan_5_2021 = LocalDate.of(2021, 1, 5);

    // November 1st, 2020, 22:30
    LocalDateTime nov_1__2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);

    // 120 minutes
    Duration min_120 = Duration.ofMinutes(120);

    // A simple event
    // November 1st, 2020, 22:30, 120 minutes
    Event simple = new Event("Simple event", nov_1__2020_22_30, min_120);

    // A Weekly Repetitive event ending at a given date
    RepetitiveEvent fixedTermination = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, jan_5_2021);

    // A Weekly Repetitive event ending after a give number of occurrrences
    RepetitiveEvent fixedRepetitions = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, 10);

    // A daily repetitive event, never ending
    // November 1st, 2020, 22:30, 120 minutes
    RepetitiveEvent neverEnding = new RepetitiveEvent("Never Ending", nov_1__2020_22_30, min_120, ChronoUnit.DAYS);

    @BeforeEach
    public void setUp() {
        agenda = new Agenda();
        agenda.addEvent(simple);
        agenda.addEvent(fixedTermination);
        agenda.addEvent(fixedRepetitions);
        agenda.addEvent(neverEnding);
    }

    @Test
    public void testMultipleEventsInDay() {
        assertEquals(4, agenda.eventsInDay(nov_1_2020).size(), "Il y a 4 événements ce jour là");
        assertTrue(agenda.eventsInDay(nov_1_2020).contains(neverEnding));
    }

    @Test
    public void testFindByTitle() {
        // null
        String titre = "null";
        List<Event> listeTitre = new ArrayList<Event>();
        assertEquals(listeTitre, agenda.findByTitle(titre), "il y a 0 evenement avec ce nom");

        // bcp
        String titre2 = "Dinner Pro";
        LocalDateTime nov_13__2020_11_41 = LocalDateTime.of(2020, 11, 13, 11, 41);
        Event ev = new Event("Dinner Pro", nov_13__2020_11_41, min_120);
        agenda.addEvent(ev);
        listeTitre.add(ev);
        assertEquals(listeTitre, agenda.findByTitle(titre2), "il y a 2 évenement ce nom");
    }

    @Test
    public void testIsFreeFor() {
        LocalDateTime nov_1__2020_23_30 = LocalDateTime.of(2020, 11, 1, 23, 30);
        LocalDateTime nov_1__2020_22_00 = LocalDateTime.of(2020, 11, 1, 22, 00);
        Duration min_240 = Duration.ofMinutes(240);


        Event ev = new Event("avant", nov_1__2020_23_30, min_120);
        assertEquals(false, agenda.isFreeFor(ev), "début avant, fin pendant");

        Event ev1 = new Event("début en même temps", nov_1__2020_22_30, min_120);
        assertEquals(false, agenda.isFreeFor(ev1), "début en même temps");

        Event ev2 = new Event("pendant et après", nov_1__2020_23_30, min_120);
        assertEquals(false, agenda.isFreeFor(ev2), "début pendant, fin après");

        Event ev3 = new Event("pas ensemble", nov_1__2020_22_00, min_240);
        assertEquals(false, agenda.isFreeFor(ev3), "début après, fin après");

    }

}