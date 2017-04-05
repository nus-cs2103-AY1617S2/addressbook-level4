package seedu.whatsleft.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.LocalDateTimeRange;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.exceptions.CalendarUnsyncException;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;

//@@author A0124377A

public class CalendarPanel extends UiPart<Region> {
    private static final String CALENDAR_UNSYC_MESSAGE = "Calendar is not synced";
    private static final String CALENDAR_ID = "calendar";
    private Agenda agenda;
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private final CalendarAdder calAdder;

    public CalendarPanel(AnchorPane calendarPlaceholder, ObservableList<ReadOnlyEvent> eventList,
            ObservableList<ReadOnlyTask> taskList) {
        agenda = new Agenda();
        calAdder = CalendarAdder.initializeCalendar();
        addToPlaceHolder(calendarPlaceholder);
        setConnection(eventList, taskList);
        logger.info("Setting up Calendar panel...");
        setBoundary();
        setWeekView();
        agenda.setAllowDragging(false);
        agenda.setDisplayedLocalDateTime(LocalDateTime.now());
        resetCallBack();
    }

   /**
     * Set up the default week view
     * @param before
     * @param after
     */
    private void setWeekView() {
        AgendaWeekSkin skin = new AgendaWeekSkin(this.agenda);
        this.agenda.setSkin(skin);
    }

   /**
    * Reset callbacks which to automatically update
    */
    private void resetCallBack() {
        agenda.setActionCallback(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment param) {
                logger.info(param.getSummary() + " is selected. ");
                return null;
            }
        });

        agenda.setEditAppointmentCallback(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment param) {
                return null;
            }
        });

        // Disallow adding activity just by clicking.
        agenda.setNewAppointmentCallback(new Callback<LocalDateTimeRange, Appointment>() {
            @Override
            public Appointment call(LocalDateTimeRange param) {
                return null;
            }
        });

    }

    private void addToPlaceHolder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, true);
        agenda.setId(CALENDAR_ID);
        placeHolderPane.getChildren().add(agenda);
    }

    private void setBoundary() {
        AnchorPane.setTopAnchor(agenda, 0.0);
        AnchorPane.setBottomAnchor(agenda, 0.0);
        AnchorPane.setLeftAnchor(agenda, 0.0);
        AnchorPane.setRightAnchor(agenda, 0.0);
    }

    /**
     * @param eventList
     * @param taskList
     */
    private void setConnection(List<ReadOnlyEvent> eventList, List<ReadOnlyTask> taskList) {
        agenda.appointments().clear();
        agenda.selectedAppointments().clear();
        setConnectionEvent(eventList);
        setConnectionTask(taskList);
    }

    private void setConnectionEvent(List<ReadOnlyEvent> eventList) {
        eventList.stream()
             .filter(event -> LocalDateTime.of(event.getEndDate().getValue(),
                     event.getEndTime().getValue()).isAfter(LocalDateTime.now()))
             .collect(Collectors.toList())
             .forEach(event -> agenda.appointments().add(calAdder.convertFromEvent(event)));
    }

    private void setConnectionTask(List<ReadOnlyTask> taskList) {
        taskList.stream()
            .filter(task -> task.getByDate().value != null && !task.getStatus() && task.getByTime().value != null)
            .collect(Collectors.toList())
            .forEach(task -> agenda.appointments().add(calAdder.convertFromTask(task)));
    }

   /**
     * Refresh data shown when eventlist in model modified
     * @param eventList
     */
    public void refresh(List<ReadOnlyEvent> eventList, List<ReadOnlyTask> taskList) {
        logger.info("Refreshing calendar...");
        setConnection(eventList, taskList);
    }

   /**
     * Select an event in the calendar and show its details. Had help for this function
     * @param targetEvent
     * @throws exception if calendar is not sync with event list
     */
    public void selection(ReadOnlyEvent event) throws CalendarUnsyncException {
        LocalDateTime displayedDateTime = LocalDateTime.of(event.getStartDate().getValue(),
                event.getStartTime().getValue());
        updateCalendarShownPeriod(displayedDateTime);

        Appointment targetAppoint  = agenda.appointments()
                .stream()
                .filter((Predicate<? super Agenda.Appointment>) eventInCalendar
                    -> CalendarAdder.compareWithEvent(event, eventInCalendar))
                .findAny()
                .orElseThrow(()-> new CalendarUnsyncException(CALENDAR_UNSYC_MESSAGE));
        agenda.selectedAppointments().clear();
        agenda.selectedAppointments().add(targetAppoint);
    }
    /**
     * Select a task in the calendar and show its details.
     * @param targetTask
     * @throws exception if calendar is not sync with task list
     */
    public void selection(ReadOnlyTask task) throws CalendarUnsyncException {
        if (isFloatingTask(task) || isCompleted(task)) {
            return;
        }

        LocalDateTime displayedDateTime = LocalDateTime.of(task.getByDate().getValue(), task.getByTime().getValue());
        updateCalendarShownPeriod(displayedDateTime);

        Appointment targetAppoint = agenda.appointments().stream()
                .filter((Predicate<? super Agenda.Appointment>) taskInCalendar
                    -> CalendarAdder.compareWithTask(task, taskInCalendar))
                .findAny()
                .orElseThrow(() -> new CalendarUnsyncException(CALENDAR_UNSYC_MESSAGE));
        agenda.selectedAppointments().clear();
        agenda.selectedAppointments().add(targetAppoint);
    }

    //Update the calendar to show new view with user input weeks ahead
    public void viewWeeksAhead(LocalDateTime t, int weeksAhead) {
        updateCalendarShownPeriod(t.plusWeeks(weeksAhead));
    }

    /**
     * Focus the calendar on selected time frame
     * @param t
     */
    public void updateCalendarShownPeriod(LocalDateTime t) {
        agenda.setDisplayedLocalDateTime(t);
    }


    private boolean isFloatingTask(ReadOnlyTask task) {
        return task.getByDate() == null;
    }

    private boolean isCompleted(ReadOnlyTask task) {
        return task.getStatus();
    }


}
