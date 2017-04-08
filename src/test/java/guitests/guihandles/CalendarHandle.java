package guitests.guihandles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.stage.Stage;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import seedu.whatsleft.TestApp;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.testutil.TestUtil;
import seedu.whatsleft.ui.CalendarAdder;

//@@author A0124377A
/**
 * Handler for CalendarTest
 */
public class CalendarHandle extends GuiHandle {

    private static final String PANE_ID = "#calendar";
    private final CalendarAdder calAdder;

    public CalendarHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
        calAdder = CalendarAdder.initializeCalendar();
    }

    public List<Appointment> getAppoinments() {
        Agenda agenda = getAgenda();
        return agenda.appointments();
    }

    public Agenda getAgenda() {
        return (Agenda) getNode(PANE_ID);
    }

    public boolean isCalendarTasksMatching(ReadOnlyTask... tasks) {
        return this.isCalendarTaskMatching(0, tasks);
    }

    private boolean isCalendarTaskMatching(int startPosition, ReadOnlyTask[] tasks) {
        if (tasks.length + startPosition != getAppoinmentsTask().size()) {
            throw new IllegalArgumentException("Calendar size mismatched\n" + "Expected"
                    + (getAppoinmentsTask().size() - 1) + "events\n"
                    + "But was : " + tasks.length);
        }

        return (this.containsAllTask(startPosition, tasks));
    }

    public boolean isCalendarEventsMatching(ReadOnlyEvent... events) {
        return this.isCalendarEventMatching(0, events);
    }

    private boolean isCalendarEventMatching(int startPosition, ReadOnlyEvent[] events) {
        if (events.length + startPosition != getAppoinmentsEvent().size()) {
            throw new IllegalArgumentException ("Calendar size mismatched\n" + "Expected"
                    + (getAppoinmentsEvent().size() - 1) + "events\n"
                    + "But was : " + events.length);
        }

        return (this.containsAll(startPosition, events));
    }

    private List<Appointment> getAppoinmentsTask() {
        Agenda agenda = getAgenda();
        return agenda.appointments().stream()
        .filter((Predicate<? super Agenda.Appointment>) appointment -> calAdder.isTask(appointment))
        .collect(Collectors.toList());
    }

    private List<Appointment> getAppoinmentsEvent() {
        Agenda agenda = getAgenda();
        return agenda.appointments().stream()
        .filter((Predicate<? super Agenda.Appointment>) appointment -> calAdder.isEvent(appointment))
        .collect(Collectors.toList());
    }


    private boolean containsAll(int startPosition, ReadOnlyEvent[] events) {
        List<Appointment> eventsInCal = getAppoinments();

        //check on the length
        if (eventsInCal.size() < startPosition + events.length) {
            return false;
        }

        //check each event in the list
        for (int i = 0; i < events.length; i++) {
            if (!isSameEvent(eventsInCal.get(i), events[i])) {
                throw new IllegalArgumentException("was: " + eventsInCal.get(i).toString()
                        + " expected: " + events[i].toString());
            }
        }
        return true;
    }

    private boolean containsAllTask(int startPosition, ReadOnlyTask[] tasks) {
        List<Appointment> tasksInCal = getAppoinmentsTask();

        if (tasksInCal.size() < startPosition + tasks.length) {
            return false;
        }

        //check each event in the list
        for (int i = 0; i < tasks.length; i++) {
            if (!isSameTask(tasksInCal.get(i), tasks[i])) {
                throw new IllegalArgumentException("was: " + tasksInCal.get(i).toString()
                        + " expected: " + tasks[i].toString());
            }
        }
        return true;
    }

    public boolean isSameTask(Appointment appointment, ReadOnlyTask task) {
        System.out.println(appointment.getSummary());
        System.out.println(task.getDescriptionToShow());
        return appointment.getSummary().equals(task.getDescriptionToShow())
                && appointment.getEndLocalDateTime()
                .equals(LocalDateTime.of(task.getByDate().getValue(), task.getByTime().getValue()));
    }

    public boolean isSameEvent(Appointment appointment, ReadOnlyEvent event) {

        if (appointment.getSummary().equals(event.getDescriptionToShow())
                && appointment.getStartLocalDateTime()
                .equals(LocalDateTime.of(event.getStartDate().getValue(), event.getStartTime().getValue()))
                && appointment.getEndLocalDateTime()
                .equals(LocalDateTime.of(event.getEndDate().getValue(), event.getEndTime().getValue()))) {
            System.out.println(appointment.getSummary());
            System.out.println(event.getDescriptionToShow());
            return true;
        }
        return false;
    }

    /**
     * Generate a stub agenda in week view
     * @return
     * @throws Exception
     */
    public Agenda getAgendaOfWeek() throws Exception {
        TestUtil.initRuntime();
        Agenda agenda = new Agenda();
        agenda.setSkin(new AgendaWeekSkin(agenda));
        TestUtil.tearDownRuntime();

        return agenda;
    }

    /**
     * Generate a stub agenda of specific time.
     * @param time
     * @return
     * @throws Exception
     */
    public Agenda getAgendaOfDateTime(LocalDateTime time) throws Exception {
        TestUtil.initRuntime();
        Agenda agenda = new Agenda();
        agenda.setDisplayedLocalDateTime(time);
        TestUtil.tearDownRuntime();
        return agenda;
    }
}
