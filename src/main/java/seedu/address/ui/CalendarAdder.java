package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplBase;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;


//@@author A0124377A
public class CalendarAdder extends AppointmentImplBase implements Appointment {
    private static final String EVENT_GROUP = "group0";
    private static final long DEFAULT_DURATION = 1;
    private static final String TASK_GROUP = "group1";
    private static Map<String, AppointmentGroup> groupMap;
    private static CalendarAdder instance;

    private CalendarAdder() {
        setGroups();
    }

    private static void setGroups() {
        groupMap = new HashMap<>();
        for (AppointmentGroup group : new Agenda().appointmentGroups()) {
            groupMap.put(group.getDescription(), group);
        }
    }

    public Appointment convertFromEvent(ReadOnlyEvent event) {
        Appointment item = new AppointmentImplLocal();
        item.setSummary(event.getDescription().toString());
        LocalDate startDate = event.getStartDate().getValue();
        LocalTime startTime = event.getStartTime().getValue();
        LocalDate endDate = event.getEndDate().getValue();
        LocalTime endTime = event.getEndTime().getValue();
        item.setStartLocalDateTime(LocalDateTime.of(startDate, startTime));
        item.setEndLocalDateTime(LocalDateTime.of(endDate, endTime));
        item.setLocation(event.getLocation().toString());
        item.setAppointmentGroup(groupMap.get(EVENT_GROUP));

        return item;
    }

    public Appointment convertFromTask(ReadOnlyTask task) {
        Appointment item = new AppointmentImplLocal();
        item.setSummary(task.getDescription().toString());
        LocalDate byDate = task.getByDate().getValue();
        LocalTime byTime = task.getByTime().getValue();
        if (byDate != null && byTime != null) {
            item.setEndLocalDateTime(LocalDateTime.of(byDate, byTime));
            item.setStartLocalDateTime(item.getEndLocalDateTime().minusHours(DEFAULT_DURATION));
        }
        item.setLocation(task.getLocation().toString());
        item.setAppointmentGroup(groupMap.get(TASK_GROUP));
        return item;
    }

    public static CalendarAdder getInstance() {
        if (instance == null) {
            instance = new CalendarAdder();
        }
        return instance;
    }

   /**
     * Compare the equality of a task with an item in the calendar.
     * @param targetTask
     * @param taskInCalendar
     * @return
     */
    public static boolean compareWithTask(ReadOnlyTask task, Appointment taskInCalendar) {
        assert task.getByDate() != null;
        return taskInCalendar.getSummary().equals(task.getDescription().toString())
                && taskInCalendar.getEndLocalDateTime().equals(LocalDateTime.of(
                        task.getByDate().getValue(), task.getByTime().getValue()));
    }

    public static boolean compareWithEvent(ReadOnlyEvent event, Appointment eventInCalendar) {
        return eventInCalendar.getSummary().equals(event.getDescription().toString())
                && eventInCalendar.getStartLocalDateTime().equals(
                        LocalDateTime.of(event.getStartDate().getValue(), event.getStartTime().getValue()))
                && eventInCalendar.getEndLocalDateTime().equals(
                        LocalDateTime.of(event.getEndDate().getValue(), event.getEndTime().getValue()));
    }

    public boolean isTask(Appointment appointment) {
        AppointmentGroup group =  appointment.getAppointmentGroup();
        return group.getStyleClass().equals(TASK_GROUP);
    }

    public boolean isEvent(Appointment appointment) {
        return !isTask(appointment);
    }
}
