package project.taskcrusher.testutil;

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
import project.taskcrusher.TestApp;
import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.commons.util.FileUtil;
import project.taskcrusher.commons.util.XmlUtil;
import project.taskcrusher.model.UserInbox;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.storage.XmlSerializableUserInbox;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static final String LS = System.lineSeparator();

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static final Task[] SAMPLE_PERSON_DATA = getSamplePersonData();

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

    private static Task[] getSamplePersonData() {
        try {
            // CHECKSTYLE.OFF: LineLength
            return new Task[] {
                new Task(new Name("sample task1"), new Deadline("11-2-2020"), new Priority("1"),
                            new Description("4th street"), new UniqueTagList()),
                new Task(new Name("sample task2"), new Deadline("14-2-2021"), new Priority("2"),
                            new Description("5th street"), new UniqueTagList()), };
            // CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }

    private static Tag[] getSampleTagData() {
        try {
            return new Tag[] { new Tag("relatives"), new Tag("friends") };
        } catch (IllegalValueException e) {
            assert false;
            return null;
            // not possible
        }
    }

    public static List<Task> generateSamplePersonData() {
        return Arrays.asList(SAMPLE_PERSON_DATA);
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
        createDataFileWithData(generateSampleStorageAddressBook(), filePath);
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

    public static void main(String... s) {
        createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);
    }

    public static XmlSerializableUserInbox generateSampleStorageAddressBook() {
        return new XmlSerializableUserInbox(new UserInbox());
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
     * Removes a subset from the list of persons.
     *
     * @param persons
     *            The list of persons
     * @param personsToRemove
     *            The subset of persons.
     * @return The modified persons after removal of the subset from persons.
     */
    public static TestTaskCard[] removeTasksFromList(final TestTaskCard[] persons, TestTaskCard... personsToRemove) {
        List<TestTaskCard> listOfPersons = asList(persons);
        listOfPersons.removeAll(asList(personsToRemove));
        return listOfPersons.toArray(new TestTaskCard[listOfPersons.size()]);
    }

    /**
     * Returns a copy of the list with the person at specified index removed.
     *
     * @param list
     *            original list to copy from
     * @param targetIndexInOneIndexedFormat
     *            e.g. index 1 if the first element is to be removed
     */
    public static TestTaskCard[] removeTaskFromList(final TestTaskCard[] list, int targetIndexInOneIndexedFormat) {
        return removeTasksFromList(list, list[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Removes a subset from the list of persons.
     *
     * @param events
     *            The list of persons
     * @param eventsToRemove
     *            The subset of persons.
     * @return The modified persons after removal of the subset from persons.
     */
    public static TestEventCard[] removeEventsFromList(final TestEventCard[] events,
            TestEventCard... eventsToRemove) {
        List<TestEventCard> listOfEvents = asList(events);
        listOfEvents.removeAll(asList(eventsToRemove));
        return listOfEvents.toArray(new TestEventCard[listOfEvents.size()]);
    }

    /**
     * Returns a copy of the list with the person at specified index removed.
     *
     * @param list
     *            original list to copy from
     * @param targetIndexInOneIndexedFormat
     *            e.g. index 1 if the first element is to be removed
     */
    public static TestEventCard[] removeEventFromList(final TestEventCard[] list, int targetIndexInOneIndexedFormat) {
        return removeEventsFromList(list, list[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Replaces persons[i] with a person.
     *
     * @param persons
     *            The array of persons.
     * @param person
     *            The replacement person
     * @param index
     *            The index of the person to be replaced.
     * @return
     */
    public static TestTaskCard[] replacePersonFromList(TestTaskCard[] persons, TestTaskCard person, int index) {
        persons[index] = person;
        return persons;
    }

    /**
     * Appends persons to the array of persons.
     *
     * @param tasks
     *            A array of persons.
     * @param tasksToAdd
     *            The persons that are to be appended behind the original array.
     * @return The modified array of persons.
     */
    public static TestTaskCard[] addTasksToList(final TestTaskCard[] tasks, TestTaskCard... tasksToAdd) {
        List<TestTaskCard> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(tasksToAdd));
        listOfTasks.sort(null);
        return listOfTasks.toArray(new TestTaskCard[listOfTasks.size()]);
    }

    public static TestEventCard[] addEventsToList(final TestEventCard[] events, TestEventCard... eventsToAdd) {
        List<TestEventCard> listOfEvents = asList(events);
        listOfEvents.addAll(asList(eventsToAdd));
        listOfEvents.sort(null);
        return listOfEvents.toArray(new TestEventCard[listOfEvents.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareTaskCardAndTask(TaskCardHandle card, ReadOnlyTask task) {
        if (card == null) {
            assert false;
        }
        return card.isSameTask(task);
    }

    public static boolean compareEventCardAndEvent(EventCardHandle card, ReadOnlyEvent event) {
        if (card == null) {
            assert false;
        }
        return card.isSameEvent(event);
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
