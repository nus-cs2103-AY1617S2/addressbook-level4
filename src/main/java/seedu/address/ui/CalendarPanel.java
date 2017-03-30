package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.LocalDateTimeRange;
import seedu.address.commons.core.CalendarLayout;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.CalendarUnsyncException;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;

//@@author A0124377A

/**
 * The Calendar window controller
 * Responsible for loading the calendar
 * Updating the calendar view
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String CALENDAR_UNSYC_MESSAGE = "Calendar is not synced";
    private static final String CALENDAR_VIEW_ID = "calendar";
    private static final int DEFAULT_BEFORE = -1;
    private static final int DEFAULT_AFTER = 3;
    private static final double DEFAULT_WEEK_VIEW_DAYS = 4.0;
    private Agenda agenda;
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private final CalendarAdder calAdder;
    private static final String FXML = "nil";

    public CalendarPanel(AnchorPane calendarPlaceholder, ObservableList<ReadOnlyEvent> eventList,
            ObservableList<ReadOnlyTask> taskList) {
        agenda = new Agenda();
        calAdder = CalendarAdder.getInstance();
        addToPlaceHolder(calendarPlaceholder);
        configure(eventList, taskList);
        logger.info("Setting up Calendar panel...");
        setBoundary();
        setWeekView(DEFAULT_BEFORE, DEFAULT_AFTER);
        agenda.setAllowDragging(false);
        agenda.setDisplayedLocalDateTime(LocalDateTime.now());
        resetCallBack();
    }

   /**
     * Set up the week view by setting the default value for the sliders.
     * @param before
     * @param after
     */
    private void setWeekView(int before, int after) {
        AgendaWeekSkin skin = new AgendaWeekSkin(this.agenda);
        //        AgendaDaysFromDisplayedSkin skin = new AgendaDaysFromDisplayedSkin(this.agenda);
//        skin.setDaysBeforeFurthest(before);
//        skin.setDaysAfterFurthest(after);
//        Slider slider = (Slider) this.agenda.lookup("#daysAfterSlider");
//        slider.setValue(DEFAULT_WEEK_VIEW_DAYS);
        this.agenda.setSkin(skin);
    }

   /**
    * Reset callbacks which modify the calendar so that the calendar depends solely on the event list
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
                // Do nothing
                return null;
            }
        });

        agenda.setNewAppointmentCallback(new Callback<LocalDateTimeRange, Appointment>() {
            @Override
            public Appointment call(LocalDateTimeRange param) {
                // Not allowing adding new events by clicking.
                return null;
            }
        });

    }

    private void addToPlaceHolder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, true);
        agenda.setId(CALENDAR_VIEW_ID);
        placeHolderPane.getChildren().add(agenda);
    }

    private void setBoundary() {
        AnchorPane.setTopAnchor(agenda, 0.0);
        AnchorPane.setBottomAnchor(agenda, 0.0);
        AnchorPane.setLeftAnchor(agenda, 0.0);
        AnchorPane.setRightAnchor(agenda, 0.0);
    }

    /**
     * Set data connection of calendar and the lists
     * @param eventList
     * @param taskList
     */
    private void configure(List<ReadOnlyEvent> eventList, List<ReadOnlyTask> taskList) {
        setConnection(eventList, taskList);
    }

    private void setConnection(List<ReadOnlyEvent> eventList, List<ReadOnlyTask> taskList) {
        agenda.appointments().clear();
        agenda.selectedAppointments().clear();
        setConnectionEvent(eventList);
        setConnectionTask(taskList);
    }

    private void setConnectionEvent(List<ReadOnlyEvent> eventList) {
        eventList.forEach(event -> agenda.appointments().add(calAdder.convertFromEvent(event)));
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
     * Toggle the Calendar display mode
     * @param calendarViewMode
     */
    public void updateCalendarMode(CalendarLayout calendarViewMode) {
        switch(calendarViewMode) {
        case DAY:
            agenda.setSkin(new AgendaDaySkin(agenda));
            break;
        case WEEK:
            setWeekView(DEFAULT_BEFORE, DEFAULT_AFTER);
            break;
        default:
            setWeekView(DEFAULT_BEFORE, DEFAULT_AFTER);
        }
    }

   /**
     * Select an event in the calendar and show its details.
     * @param targetEvent
     * @throws exception if calendar is not sync with event list. Restart needed.
     */
    public void select(ReadOnlyEvent event) throws CalendarUnsyncException {
        // focus on the event
        LocalDateTime displayedDateTime = LocalDateTime.of(event.getStartDate().getValue(),
                event.getStartTime().getValue());
        updateCalendarShownPeriod(displayedDateTime);

        //highlight the event
        Appointment targetAppoint  = agenda.appointments()
                .stream()
                .filter((Predicate<? super Agenda.Appointment>) eventInCalendar
                    -> CalendarAdder.compareWithEvent(event, eventInCalendar))
                .findAny()
                .orElseThrow(()-> new CalendarUnsyncException(CALENDAR_UNSYC_MESSAGE));

        agenda.selectedAppointments().add(targetAppoint);
    }

    public void select(ReadOnlyTask task) throws CalendarUnsyncException {
        if (isCompleted(task) || isFloatingTask(task)) {
            return;
        }

        LocalDateTime displayedDateTime = LocalDateTime.of(task.getByDate().getValue(), task.getByTime().getValue());
        updateCalendarShownPeriod(displayedDateTime);

        Appointment targetAppoint = agenda.appointments().stream()
                .filter((Predicate<? super Agenda.Appointment>) taskInCalendar
                    -> CalendarAdder.compareWithTask(task, taskInCalendar))
                .findAny()
                .orElseThrow(() -> new CalendarUnsyncException(CALENDAR_UNSYC_MESSAGE));

        agenda.selectedAppointments().add(targetAppoint);
    }

    /**
     * Focus the calendar to a certain time frame
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
