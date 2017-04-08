package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import project.taskcrusher.TestApp;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class UserInboxPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TASK_LIST_VIEW_ID = "#taskListView";
    private static final String EVENT_LIST_VIEW_ID = "#eventListView";

    public UserInboxPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        ListView<ReadOnlyTask> taskList = getTaskListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public List<ReadOnlyEvent> getSelectedEvents() {
        ListView<ReadOnlyEvent> eventList = getEventListView();
        return eventList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getTaskListView() {
        return getNode(TASK_LIST_VIEW_ID);
    }

    public ListView<ReadOnlyEvent> getEventListView() {
        return getNode(EVENT_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of tasks in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks);
    }

    /**
     * Returns true if the list is showing the event details correctly and in correct order.
     * @param tasks A list of events in the correct order.
     */
    public boolean isListMatching(ReadOnlyEvent... events) {
        return this.isListMatching(0, events);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of person in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getTaskListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getTaskListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getTaskListView().scrollTo(scrollTo));
            guiRobot.sleep(400);
            if (!TestUtil.compareTaskCardAndTask(getTaskCardHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the list is showing the event details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param events A list of events in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyEvent... events) throws IllegalArgumentException {
        if (events.length + startPosition != getEventListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getEventListView().getItems().size() - 1) + " events");
        }
        assertTrue(this.containsInOrder(startPosition, events));
        for (int i = 0; i < events.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getEventListView().scrollTo(scrollTo));
            guiRobot.sleep(400);
            if (!TestUtil.compareEventCardAndEvent(getEventCardHandle(startPosition + i), events[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getTaskListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().name.equals(tasks[i].getName().name)) {
                System.out.println("in list is " + tasksInList.get(startPosition + i).getName().name);
                System.out.println("Wanted is " + tasks[i].getName().name);
                System.out.println(i + " dont match");
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the {@code events} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyEvent... events) {
        List<ReadOnlyEvent> eventsInList = getEventListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + events.length > eventsInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < events.length; i++) {
            if (!eventsInList.get(startPosition + i).getName().name.equals(events[i].getName().name)) {
                return false;
            }
        }

        return true;
    }

    public TaskCardHandle navigateToTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getTaskListView().getItems().stream()
                                                    .filter(t -> t.getName().name.equals(name))
                                                    .findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTask(task.get());
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public TaskCardHandle navigateToTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);

        guiRobot.interact(() -> {
            getTaskListView().scrollTo(index);
            guiRobot.sleep(150);
            getTaskListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(task);
    }

    /**
     * Navigates the listview to display and select the event.
     */
    public EventCardHandle navigateToEvent(ReadOnlyEvent event) {
        int index = getEventIndex(event);

        guiRobot.interact(() -> {
            getEventListView().scrollTo(index);
            guiRobot.sleep(150);
            getEventListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getEventCardHandle(event);
    }

    public EventCardHandle navigateToEvent(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyEvent> event = getEventListView().getItems().stream()
                                                    .filter(t -> t.getName().name.equals(name))
                                                    .findAny();
        if (!event.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToEvent(event.get());
    }


    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask) {
        List<ReadOnlyTask> tasksInList = getTaskListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getName().equals(targetTask.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getEventIndex(ReadOnlyEvent targetEvent) {
        List<ReadOnlyEvent> eventsInList = getEventListView().getItems();
        for (int i = 0; i < eventsInList.size(); i++) {
            if (eventsInList.get(i).getName().equals(targetEvent.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyTask getPerson(int index) {
        return getTaskListView().getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(new Task(getTaskListView().getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(person))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            System.out.println("Returning null");
            return null;
        }
    }

    public EventCardHandle getEventCardHandle(int index) {
        return getEventCardHandle(new Event(getEventListView().getItems().get(index)));
    }

    public EventCardHandle getEventCardHandle(ReadOnlyEvent event) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> eventCardNode = nodes.stream()
                .filter(n -> new EventCardHandle(guiRobot, primaryStage, n).isSameEvent(event))
                .findFirst();
        if (eventCardNode.isPresent()) {
            return new EventCardHandle(guiRobot, primaryStage, eventCardNode.get());
        } else {
            System.out.println("Returning null");
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        return getTaskListView().getItems().size();
    }

    public int getNumberOfEvents() {
        return getEventListView().getItems().size();
    }
}
