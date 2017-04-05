package seedu.address.testutil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;

import com.google.common.io.Files;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import seedu.address.TestApp;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.ByTime;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Event;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.Task;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.storage.XmlSerializableWhatsLeft;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static final String LS = System.lineSeparator();

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static final Event[] SAMPLE_EVENT_DATA = getSampleEventData();

    public static final Task[] SAMPLE_TASK_DATA = getSampleTaskData();

    public static final Tag[] SAMPLE_TAG_DATA = getSampleTagData();

    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try {
            executable.run();
        } catch (Throwable actualException) {
            if (actualException.getClass().isAssignableFrom(expected)) {
                return;
            }
            String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                    actualException.getClass().getName());
            throw new AssertionFailedError(message);
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

    private static Event[] getSampleEventData() {
        try {
            // CHECKSTYLE.OFF: LineLength
            return new Event[] {
                new Event(new Description("CS2103 TUT 1"), new StartTime("0900"), new StartDate("200517"),
                            new EndTime("1000"), new EndDate("200517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2104 TUT 1"), new StartTime("0900"), new StartDate("210517"),
                            new EndTime("1000"), new EndDate("210517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2105 TUT 1"), new StartTime("0900"), new StartDate("220517"),
                            new EndTime("1000"), new EndDate("220517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2106 TUT 1"), new StartTime("0900"), new StartDate("230517"),
                            new EndTime("1000"), new EndDate("230517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2107 TUT 1"), new StartTime("0900"), new StartDate("240517"),
                            new EndTime("1000"), new EndDate("240517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2108 TUT 1"), new StartTime("0900"), new StartDate("250517"),
                            new EndTime("1000"), new EndDate("250517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2109 TUT 1"), new StartTime("0900"), new StartDate("260517"),
                            new EndTime("1000"), new EndDate("260517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2110 TUT 1"), new StartTime("0900"), new StartDate("270517"),
                            new EndTime("1000"), new EndDate("270517"), new Location("NUS"), new UniqueTagList()),
                new Event(new Description("CS2111 TUT 1"), new StartTime("0900"), new StartDate("280517"),
                            new EndTime("1000"), new EndDate("280517"), new Location("NUS"), new UniqueTagList()) };
            // CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }

    private static Task[] getSampleTaskData() {
        try {
            return new Task[] {
                new Task(new Description("Homework 1"), new Priority("high"), new ByTime("1000"),
                            new ByDate("200517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 2"), new Priority("high"), new ByTime("1000"),
                            new ByDate("210517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 3"), new Priority("high"), new ByTime("1000"),
                            new ByDate("220517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 4"), new Priority("high"), new ByTime("1000"),
                            new ByDate("230517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 5"), new Priority("high"), new ByTime("1000"),
                            new ByDate("240517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 6"), new Priority("high"), new ByTime("1000"),
                            new ByDate("250517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 7"), new Priority("high"), new ByTime("1000"),
                            new ByDate("260517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 8"), new Priority("high"), new ByTime("1000"),
                            new ByDate("270517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS),
                new Task(new Description("Homework 9"), new Priority("high"), new ByTime("1000"),
                            new ByDate("280517"), new Location("NUS"), new UniqueTagList(), Task.DEFAULT_TASK_STATUS) };
        } catch (IllegalValueException e) {
            assert false;
            return null;
        }
    }

    private static Tag[] getSampleTagData() {
        try {
            return new Tag[] { new Tag("presentation"), new Tag("webcast") };
        } catch (IllegalValueException e) {
            assert false;
            return null;
            // not possible
        }
    }

    public static List<Event> generateSampleEventData() {
        return Arrays.asList(SAMPLE_EVENT_DATA);
    }

    /**
     * Appends the file name to the sandbox folder path. Creates the sandbox
     * folder if it doesn't exist.
     *
     * @param fileName
     * @return
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    public static void createDataFileWithSampleData(String filePath) {
        createDataFileWithData(generateSampleStorageWhatsLeft(), filePath);
    }

    public static <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // @@author A0121668A
    /**
     * Takes in a list of events and returns the expected filtered and sorted
     * list of events in event panel
     *
     * @param events
     * @return expected filtered and sorted event list in event panel
     */
    public static ReadOnlyEvent[] filterExpectedEventList(Event[] events) {
        int numInUIView = 0;
        ArrayList<ReadOnlyEvent> eventslist = new ArrayList<ReadOnlyEvent>();
        for (ReadOnlyEvent a : events) {
            if (!a.isOver()) {
                numInUIView++;
                eventslist.add(a);
            }
        }
        eventslist.sort(ReadOnlyEvent.getComparator());
        ReadOnlyEvent[] expected = new ReadOnlyEvent[numInUIView];
        int counter = 0;
        for (ReadOnlyEvent each : eventslist) {
            expected[counter] = each;
            counter++;
        }
        return expected;
    }

    // @@author
    public static void main(String... s) {
        createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);
    }

    public static XmlSerializableWhatsLeft generateSampleStorageWhatsLeft() {
        return new XmlSerializableWhatsLeft(new WhatsLeft());
    }

    /**
     * Tweaks the {@code keyCodeCombination} to resolve the
     * {@code KeyCode.SHORTCUT} to their respective platform-specific keycodes
     */
    public static KeyCode[] scrub(KeyCodeCombination keyCodeCombination) {
        List<KeyCode> keys = new ArrayList<>();
        if (keyCodeCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.ALT);
        }
        if (keyCodeCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.SHIFT);
        }
        if (keyCodeCombination.getMeta() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.META);
        }
        if (keyCodeCombination.getControl() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.CONTROL);
        }
        keys.add(keyCodeCombination.getCode());
        return keys.toArray(new KeyCode[] {});
    }

    public static boolean isHeadlessEnvironment() {
        String headlessProperty = System.getProperty("testfx.headless");
        return headlessProperty != null && headlessProperty.equals("true");
    }

    public static void captureScreenShot(String fileName) {
        File file = GuiTest.captureScreenshot();
        try {
            Files.copy(file, new File(fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String descOnFail(Object... comparedObjects) {
        return "Comparison failed \n"
                + Arrays.asList(comparedObjects).stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public static void setFinalStatic(Field field, Object newValue)
            throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        // ~Modifier.FINAL is used to remove the final modifier from field so
        // that its value is no longer
        // final and can be changed
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static void initRuntime() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage();
    }

    public static void tearDownRuntime() throws Exception {
        FxToolkit.cleanupStages();
    }

    /**
     * Gets private method of a class Invoke the method using
     * method.invoke(objectInstance, params...)
     *
     * Caveat: only find method declared in the current Class, not inherited
     * from supertypes
     */
    public static Method getPrivateMethod(Class<?> objectClass, String methodName) throws NoSuchMethodException {
        Method method = objectClass.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method;
    }

    public static void renameFile(File file, String newFileName) {
        try {
            Files.copy(file, new File(newFileName));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Gets mid point of a node relative to the screen.
     *
     * @param node
     * @return
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    /**
     * Gets mid point of a node relative to its scene.
     *
     * @param node
     * @return
     */
    public static Point2D getSceneMidPoint(Node node) {
        double x = getScenePos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScenePos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    /**
     * Gets the bound of the node relative to the parent scene.
     *
     * @param node
     * @return
     */
    public static Bounds getScenePos(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    public static Bounds getScreenPos(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

    public static double getSceneMaxX(Scene scene) {
        return scene.getX() + scene.getWidth();
    }

    public static double getSceneMaxY(Scene scene) {
        return scene.getX() + scene.getHeight();
    }

    public static Object getLastElement(List<?> list) {
        return list.get(list.size() - 1);
    }

    /**
     * Removes a subset from the list of events.
     *
     * @param events
     *            The list of events
     * @param eventsToRemove
     *            The subset of events.
     * @return The modified events after removal of the subset from events.
     */
    public static TestEvent[] removeEventsFromList(final TestEvent[] events, TestEvent... eventsToRemove) {
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents.removeAll(asList(eventsToRemove));
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }

    /**
     * Removes a subset from the list of tasks.
     *
     * @param tasks
     *            The list of tasks
     * @param tasks
     *            The subset of tasks.
     * @return The modified tasks after removal of the subset from tasks.
     */
    public static TestTask[] removeTasksFromList(final TestTask[] tasks, TestTask... tasksToRemove) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(tasksToRemove));
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    /**
     * Returns a copy of the list with the event at specified index removed.
     *
     * @param list
     *            original list to copy from
     * @param targetIndexInOneIndexedFormat
     *            e.g. index 1 if the first element is to be removed
     */
    public static TestEvent[] removeEventFromList(final TestEvent[] list, int targetIndexInOneIndexedFormat) {
        return removeEventsFromList(list, list[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Returns a copy of the list with the task at specified index removed.
     *
     * @param list
     *            original list to copy from
     * @param targetIndexInOneIndexedFormat
     *            e.g. index 1 if the first element is to be removed
     */
    public static TestTask[] removeTaskFromList(final TestTask[] list, int targetIndexInOneIndexedFormat) {
        return removeTasksFromList(list, list[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Replaces events[i] with an event.
     *
     * @param events
     *            The array of events.
     * @param event
     *            The replacement event
     * @param index
     *            The index of the event to be replaced.
     * @return
     */
    public static TestEvent[] replaceEventFromList(TestEvent[] events, TestEvent event, int index) {
        events[index] = event;
        return events;
    }

    /**
     * Replaces tasks[i] with a task.
     *
     * @param tasks
     *            The array of tasks.
     * @param task
     *            The replacement task
     * @param index
     *            The index of the task to be replaced.
     * @return
     */
    public static TestTask[] replaceTaskFromList(TestTask[] tasks, TestTask task, int index) {
        tasks[index] = task;
        return tasks;
    }

    /**
     * Appends events to the array of events.
     *
     * @param events
     *            A array of events.
     * @param eventsToAdd
     *            The events that are to be appended behind the original array.
     * @return The modified array of events.
     */
    public static TestEvent[] addEventsToList(final TestEvent[] events, TestEvent... eventsToAdd) {
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents.addAll(asList(eventsToAdd));
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }

    /**
     * Appends tasks to the array of tasks.
     *
     * @param tasks
     *            A array of tasks.
     * @param tasks
     *            The activities that are to be appended behind the original
     *            array.
     * @return The modified array of tasks.
     */
    public static TestTask[] addTasksToList(final TestTask[] tasks, TestTask... tasksToAdd) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(tasksToAdd));
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndEvent(EventCardHandle card, ReadOnlyEvent event) {
        return card.isSameEvent(event);
    }

    public static boolean compareCardAndTask(TaskCardHandle card, ReadOnlyTask task) {
        return card.isSameTask(task);
    }

    public static Tag[] getTagList(String tags) {
        if ("".equals(tags)) {
            return new Tag[] {};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Tag(e.replaceFirst("Tag: ", ""));
            } catch (IllegalValueException e1) {
                // not possible
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

}
