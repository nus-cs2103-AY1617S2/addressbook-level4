package t09b1.today.testutil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.loadui.testfx.GuiTest;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;
import org.testfx.api.FxToolkit;

import com.google.common.io.Files;

import guitests.guihandles.TaskCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import t09b1.today.TestApp;
import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.commons.util.FileUtil;
import t09b1.today.commons.util.XmlUtil;
import t09b1.today.logic.commands.AddCommand;
import t09b1.today.model.TaskManager;
import t09b1.today.model.tag.Tag;
import t09b1.today.model.task.ReadOnlyTask;
import t09b1.today.model.task.Task;
import t09b1.today.storage.XmlSerializableTaskManager;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static final String LS = System.lineSeparator();

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

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
        createDataFileWithData(generateSampleStorageTaskManager(), filePath);
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

    public static XmlSerializableTaskManager generateSampleStorageTaskManager() {
        return new XmlSerializableTaskManager(new TaskManager());
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
     * Removes a subset from the list of tasks.
     *
     * @param tasks
     *            The list of tasks
     * @param tasksToRemove
     *            The subset of tasks.
     * @return The modified tasks after removal of the subset from tasks.
     */
    public static Task[] removeTasksFromList(final Task[] tasks, Task... tasksToRemove) {
        List<Task> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(tasksToRemove));
        return listOfTasks.toArray(new Task[listOfTasks.size()]);
    }

    /**
     * Returns a copy of the list with the task at specified index removed.
     *
     * @param list
     *            original list to copy from
     * @param targetIndexInOneIndexedFormat
     *            e.g. index 1 if the first element is to be removed
     */
    public static Task[] removeTaskFromList(final Task[] list, int targetIndexInOneIndexedFormat) {
        return removeTasksFromList(list, list[targetIndexInOneIndexedFormat - 1]);
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
    public static Task[] replaceTaskFromList(Task[] tasks, Task task, int index) {
        tasks[index] = task;
        return tasks;
    }

    /**
     * Appends tasks to the array of tasks.
     *
     * @param tasks
     *            A array of tasks.
     * @param tasksToAdd
     *            The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static Task[] addTasksToList(final Task[] tasks, Task... tasksToAdd) {
        List<Task> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(tasksToAdd));
        return listOfTasks.toArray(new Task[listOfTasks.size()]);
    }

    /**
     * Appends task to the array of tasks at an index.
     *
     * @param tasks
     *            A array of tasks.
     * @param task
     *            The task to be added
     * @param index
     *            The index at which to insert the task
     * @return The modified array of tasks.
     */
    public static Task[] addTasksToList(final Task[] tasks, Task task, int index) {
        Task[] newTasks = new Task[tasks.length + 1];
        if (index <= 0) {
            index = 0;
        } else if (index > tasks.length) {
            index = tasks.length;
        }

        for (int i = 0; i < index; i++) {
            newTasks[i] = tasks[i];
        }
        newTasks[index] = task;
        for (int i = index + 1; i < newTasks.length; i++) {
            newTasks[i] = tasks[i - 1];
        }

        return newTasks;
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
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

    /**
     * Assigns relative indexes for test tasks The index is the same as the
     * actual index shown on the UI
     */
    public static void assignUiIndex(Task[] taskList) {
        // initialise displayed index
        int todayID = 1;
        int futureID = 1;
        int completedID = 1;
        for (int i = 0; i < taskList.length; i++) {
            Task tmpTask = taskList[i];
            // set task id to be displayed, the id here is 1-based
            if (tmpTask.isToday() && !tmpTask.isDone()) {
                tmpTask.setID("T" + todayID);
                todayID++;
            } else if (!tmpTask.isDone()) {
                tmpTask.setID("F" + futureID);
                futureID++;
            } else {
                tmpTask.setID("C" + completedID);
                completedID++;
            }
        }
    }

    /**
     * Assigns relative indexes for test tasks The index is the same as the
     * actual index shown on the UI
     */
    public static void assignUiIndex(List<Task> taskList) {
        // initialise displayed index
        int todayID = 1;
        int futureID = 1;
        int completedID = 1;
        Iterator<Task> iter = taskList.iterator();
        while (iter.hasNext()) {
            Task tmpTask = iter.next();
            // set task id to be displayed, the id here is 1-based
            if (tmpTask.isToday() && !tmpTask.isDone()) {
                tmpTask.setID("T" + todayID);
                todayID++;
            } else if (!tmpTask.isDone()) {
                tmpTask.setID("F" + futureID);
                futureID++;
            } else {
                tmpTask.setID("C" + completedID);
                completedID++;
            }
        }
    }

    // @author A0093999Y
    /**
     * Makes an Add command out of a Task
     */
    public static String makeAddCommandString(Task task) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM/yyyy");
        StringBuilder builder = new StringBuilder();
        builder.append(AddCommand.COMMAND_WORD);
        builder.append(" " + task.getName().toString());
        if (task.getStartingTime().isPresent() && task.getDeadline().isPresent()) {
            builder.append(" from");
            builder.append(" " + sdf.format(task.getStartingTime().get().getDate()));
            builder.append(" to");
            builder.append(" " + sdf.format(task.getDeadline().get().getDate()));
        } else if (task.getDeadline().isPresent()) {
            builder.append(" due");
            builder.append(" " + sdf.format(task.getDeadline().get().getDate()));
        }
        for (Tag tag : task.getTags()) {
            builder.append(" #" + tag.getTagName());
        }
        return builder.toString();

    }

    /**
     * Generates a Task object with given name. Other fields will have some
     * dummy values.
     */
    public static Task generateTaskWithName(String name) throws Exception {
        return new FloatingTask(new Name(name), new UniqueTagList(new Tag("tag")), false, false);
    }

    /**
     * Generates a Task object with given name and tag. Other fields will have
     * some dummy values.
     */
    public static Task generateTaskWithNameAndTags(String name, String... tagNames) throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        for (String tagName : tagNames) {
            tags.add(new Tag(tagName));
        }
        return new FloatingTask(new Name(name), new UniqueTagList(tags), false, false);
    }

    // @@author: A0144422R
    public static Task generateEventTaskWithNameTags(String name, int days1, int days2, String... tagNames)
            throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        for (String tagName : tagNames) {
            tags.add(new Tag(tagName));
        }
        Date start = DateUtils.truncate(DateUtils.addDays(new Date(), days1), Calendar.DAY_OF_MONTH);
        Date end = DateUtils.truncate(DateUtils.addDays(new Date(), days2), Calendar.DAY_OF_MONTH);
        return new EventTask(new Name(name), new UniqueTagList(tags), end, start, false, false);
    }

    // @@author: A0144422R
    public static Task generateDeadlineTaskWithNameTags(String name, int days, String... tagNames) throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        for (String tagName : tagNames) {
            tags.add(new Tag(tagName));
        }
        Date end = DateUtils.truncate(DateUtils.addDays(new Date(), days), Calendar.DAY_OF_MONTH);
        return new DeadlineTask(new Name(name), new UniqueTagList(tags), end, false, false);
    }
}
