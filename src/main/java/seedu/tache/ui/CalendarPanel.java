package seedu.tache.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.tache.MainApp;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.events.ui.CalendarNextRequestEvent;
import seedu.tache.commons.events.ui.CalendarPreviousRequestEvent;
import seedu.tache.commons.events.ui.TaskListTypeChangedEvent;
import seedu.tache.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.tache.commons.util.FxViewUtil;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private static final String FXML = "CalendarPanel.fxml";

    @FXML
    private WebView calendar;

    /**
     * @param placeholder The AnchorPane where the CalendarPanel must be inserted.
     */
    public CalendarPanel(AnchorPane placeholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(calendar, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(calendar);
        registerAsAnEventHandler(this);
        loadCalendar(taskList);
    }

    //@@author A0142255M
    /**
     * Executes the script when the calendar finishes loading.
     *
     * @param script    Script to be executed.
     */
    private void loadAndExecuteScript(String script) {
        assert script != null;
        assert !script.equals("");
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            engine.executeScript(script);
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    engine.executeScript(script);
                }
            });
        }
    }

    /**
     * Initializes the calendar, then adds all timed events.
     *
     * @param taskList    List of all tasks (including timed tasks).
     */
    private void loadCalendar(ObservableList<ReadOnlyTask> taskList) {
        assert taskList != null;
        String calendarURL = MainApp.class.getResource("/html/calendar.html").toExternalForm();
        WebEngine engine = calendar.getEngine();
        engine.load(calendarURL);
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            addAllEvents(taskList);
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    addAllEvents(taskList);
                }
            });
        }
        logger.fine("Calendar loaded.");
    }

    /**
     * Returns the String that can be executed by the calendar WebEngine to add an event into the calendar.
     *
     * @param task    Task to be added as a calendar event.
     * @return    Script for calendar to add event.
     */
    private String getLoadTaskExecuteScript(ReadOnlyTask task) {
        assert task != null;
        String title = task.getName().toString();
        String start = "0";
        String end = "0"; // invalid format of datetime
        Optional<DateTime> startDateTime = task.getStartDateTime();
        Optional<DateTime> endDateTime = task.getEndDateTime();
        if (task.getStartDateTime().isPresent()) {
            start = startDateTime.get().getDateTimeForFullCalendar();
        }
        if (task.getEndDateTime().isPresent()) {
            end = endDateTime.get().getDateTimeForFullCalendar();
        }
        String status = "uncompleted";
        if (task.getActiveStatus() == false) {
            status = "completed";
        } else if (task.getEndDateTime().isPresent()) {
            if (task.getEndDateTime().get().hasPassed()) {
                status = "overdue";
            }
        } else if (task.getStartDateTime().isPresent()) {
            if (task.getStartDateTime().get().hasPassed()) {
                status = "overdue";
            }
        }
        return "add_event('" + title + "', '" + start + "', '" + end + "', '" + status + "')";
    }

    /**
     * Updates the events in the calendar whenever the task manager is modified.
     *
     * @param event    TaskManagerChangedEvent which contains the updated task list.
     */
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        ObservableList<ReadOnlyTask> taskList = event.data.getTaskList();
        refreshCalendar(taskList);
        logger.fine("Calendar refreshed.");
    }

    /**
     * Changes the reference position of the calendar to the date associated with the selected task.
     * If the start date of the task is not present, the end date is used as reference instead.
     *
     * @param event    TaskPanelSelectionChangedEvent which contains the task selected.
     */
    @Subscribe
    public void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        Optional<DateTime> startDateTime = event.getNewSelection().getStartDateTime();
        Optional<DateTime> endDateTime = event.getNewSelection().getEndDateTime();
        if (startDateTime.isPresent()) {
            changeReferenceDate(startDateTime.get().getDateTimeForFullCalendar());
        } else if (endDateTime.isPresent()) {
            changeReferenceDate(endDateTime.get().getDateTimeForFullCalendar());
        }
    }

    /**
     * Changes the view of the calendar according to the task list type of the task panel.
     *
     * @param event    TaskListTypeChangedEvent which contains the updated task list type.
     */
    @Subscribe
    public void handleTaskListTypeChangedEvent(TaskListTypeChangedEvent event) {
        String taskListType = event.getTaskListType();
        if (taskListType.equals("Tasks Due Today") || taskListType.equals("Tasks Due This Week")) {
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String referenceDate = sdf.format(today);
            changeReferenceDate(referenceDate);
            if (taskListType.equals("Tasks Due Today")) {
                changeView("day");
            } else {
                changeView("week");
            }
        } else {
            changeView("month");
        }
    }

    /**
     * Moves the calendar back (by a day / month / week).
     */
    @Subscribe
    public void handleCalendarPreviousRequestEvent(CalendarPreviousRequestEvent event) {
        loadAndExecuteScript("prev()");
        logger.fine("Calendar moved back.");
    }

    /**
     * Moves the calendar forward (by a day / month / week).
     */
    @Subscribe
    public void handleCalendarNextRequestEvent(CalendarNextRequestEvent event) {
        loadAndExecuteScript("next()");
        logger.fine("Calendar moved forward.");
    }

    /**
     * Toggles the view of the calendar to day / month / year.
     *
     * @param view    A string indicating the view requested ("day", "month" or "year").
     */
    private void changeView(String view) {
        assert view != null;
        assert !view.equals("");
        loadAndExecuteScript("change_view('" + view + "')");
        logger.fine("Calendar view changed to: " + view);
    }

    /**
     * Moves the calendar to the referenceDate.
     */
    private void changeReferenceDate(String referenceDate) {
        assert referenceDate != null;
        assert !referenceDate.equals("");
        loadAndExecuteScript("change_reference_date('" + referenceDate + "')");
        logger.fine("Calendar reference date changed to: " + referenceDate);
    }

    private void addCurrentEvent(ReadOnlyTask task) {
        loadAndExecuteScript(getLoadTaskExecuteScript(task));
    }

    /**
     * Clears the calendar of all previous events, then adds all timed events from the latest taskList.
     *
     * @param taskList    Updated list of all tasks.
     */
    private void refreshCalendar(ObservableList<ReadOnlyTask> taskList) {
        assert taskList != null;
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            removeAllEvents();
            addAllEvents(taskList);
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    removeAllEvents();
                    addAllEvents(taskList);
                }
            });
        }
    }

    /**
     * Clears the calendar of all events.
     */
    private void removeAllEvents() {
        WebEngine engine = calendar.getEngine();
        engine.executeScript("remove_all()");
    }

    /**
     * Inputs all timed tasks into the calendar.
     * For deadline tasks (only have end but no start date/time), convert end date/time to start date/time.
     *
     * @param taskList    List which contains all tasks (including timed tasks).
     */
    public void addAllEvents(ObservableList<ReadOnlyTask> taskList) {
        assert taskList != null;
        for (ReadOnlyTask task : taskList) {
            if (task.getTimedStatus()) {
                if (!task.getStartDateTime().isPresent()) {
                    Task newTask = new Task(task);
                    newTask.setStartDateTime(newTask.getEndDateTime());
                    newTask.setEndDateTime(Optional.empty());
                    task = newTask;
                }
                if (task.getRecurState().isMasterRecurring()) {
                    continue;
                }
                addCurrentEvent(task);
            }
        }
    }
    //@@author

    /**
     * Frees resources allocated to the calendar.
     */
    public void freeResources() {
        calendar = null;
    }

}
