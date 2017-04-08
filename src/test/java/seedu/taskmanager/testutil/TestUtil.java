package seedu.taskmanager.testutil;

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

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import seedu.taskmanager.TestApp;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.FileUtil;
import seedu.taskmanager.commons.util.XmlUtil;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.storage.XmlSerializableTaskManager;

// @@author A0141102H
/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static final String LS = System.lineSeparator();

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static final Task[] SAMPLE_TASK_DATA = getSampleTaskData();

    public static final Category[] SAMPLE_CATEGORY_DATA = getSampleCategoryData();

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

    private static Task[] getSampleTaskData() {
        try {
            // CHECKSTYLE.OFF: LineLength
            return new Task[] {
                new Task(new TaskName("Eat breakfast with mom"), new StartDate("03/03/17"), new StartTime("1000"),
                        new EndDate("03/03/17"), new EndTime("1100"), Boolean.FALSE,
                        new UniqueCategoryList("just", "friends")),
                new Task(new TaskName("Start on the CS2103 project"), new StartDate("03/03/17"),
                        new StartTime("1400"), new EndDate("03/04/17"), new EndTime("1800"), Boolean.TRUE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Eat dinner with my only 2 friends"), new StartDate("09/03/17"),
                        new StartTime("1800"), new EndDate("09/03/17"), new EndTime("2000"), Boolean.TRUE,
                        new UniqueCategoryList()),
                new Task(new TaskName("Try harder for CS2103"), new StartDate("05/04/17"), new StartTime("1500"),
                        new EndDate("05/05/17"), new EndTime("1600"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Try even harder for CS2103 project"), new StartDate("04/04/17"),
                        new StartTime("1400"), new EndDate("05/04/17"), new EndTime("1500"), Boolean.FALSE,
                        new UniqueCategoryList("lepak")),
                new Task(new TaskName("Eat lunch at techno"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("04/03/17"), new EndTime("1400"),
                        Boolean.FALSE, new UniqueCategoryList("no", "friends")),
                new Task(new TaskName("Run 2.4km in 10 mins"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                        Boolean.FALSE, new UniqueCategoryList("lepak")),
                new Task(new TaskName("Time to relax a little"), new StartDate("06/05/17"), new StartTime("1400"),
                        new EndDate("06/05/17"), new EndTime("1800"), Boolean.FALSE,
                        new UniqueCategoryList("lepak")),
                new Task(new TaskName("Chiong all day everyday"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                        Boolean.FALSE, new UniqueCategoryList("work")),
                new Task(new TaskName("Get it done"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("06/05/17"), new EndTime("1700"), Boolean.TRUE,
                        new UniqueCategoryList("work")) };
            // CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }

    private static Category[] getSampleCategoryData() {
        try {
            return new Category[] { new Category("relatives"), new Category("friends") };
        } catch (IllegalValueException e) {
            assert false;
            return null;
            // not possible
        }
    }

    public static List<Task> generateSampleTaskData() {
        return Arrays.asList(SAMPLE_TASK_DATA);
    }

    /**
     * Appends the file TaskName to the sandbox folder path. Creates the sandbox
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
    public static TestTask[] removeTasksFromList(final TestTask[] tasks, TestTask... tasksToRemove) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(tasksToRemove));
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
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
     * Appends tasks to the array of tasks.
     *
     * @param tasks
     *            A array of tasks.
     * @param tasksToAdd
     *            The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestTask[] addTasksToList(final TestTask[] tasks, TestTask... tasksToAdd) {
        List<TestTask> listOfTasks = asList(tasks);
        for (int index = 0; index < tasksToAdd.length; index++) {
            int addIndex = findSortedPositionToAdd(tasksToAdd[index], listOfTasks);
            listOfTasks.add(addIndex, tasksToAdd[index]);
        }
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndTask(EventTaskCardHandle card, ReadOnlyTask task) {
        return card.isSameTask(task);
    }

    public static boolean compareCardAndTask(DeadlineTaskCardHandle card, ReadOnlyTask task) {
        return card.isSameTask(task);
    }

    public static boolean compareCardAndTask(FloatingTaskCardHandle card, ReadOnlyTask task) {
        return card.isSameTask(task);
    }

    public static Category[] getCategoryList(String categories) {
        if ("".equals(categories)) {
            return new Category[] {};
        }

        final String[] split = categories.split(", ");

        final List<Category> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Category(e.replaceFirst("Category: ", ""));
            } catch (IllegalValueException e1) {
                // not possible
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Category[split.length]);
    }
    
    // @@author A0142418L
    /**
     * Compares the starting date and time of 2 event tasks.
     *
     * @return true if 1st event task is earlier than the 2nd event task based
     *         on the startDate and startTime
     * @return false, if otherwise.
     */
    private static boolean isAddEventEarlierAddListIndex(TestTask toAdd, TestTask task) {
        if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2).compareTo(
                task.getStartDate().value.substring(task.getStartDate().value.length() - 2)) < 0) {
            return true;
        } else {
            if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2).compareTo(
                    task.getStartDate().value.substring(task.getStartDate().value.length() - 2)) == 0) {
                if (toAdd.getStartDate().value
                        .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                        .compareTo(task.getStartDate().value.substring(
                                task.getStartDate().value.length()
                                        - 5,
                                task.getStartDate().value.length() - 3)) < 0) {
                    return true;
                } else {
                    if (toAdd.getStartDate().value
                            .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                            .compareTo(task.getStartDate().value.substring(
                                    task.getStartDate().value.length()
                                            - 5,
                                    task.getStartDate().value.length() - 3)) == 0) {
                        if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6)
                                .compareTo(task.getStartDate().value.substring(0,
                                        task.getStartDate().value.length() - 6)) < 0) {
                            return true;
                        } else {
                            if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6)
                                    .compareTo(task.getStartDate().value.substring(0,
                                            task.getStartDate().value.length() - 6)) == 0) {
                                if (toAdd.getStartTime().value.compareTo(task.getStartTime().value) < 0) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Compares the due date of 2 deadline tasks.
     *
     * @return true if 1st deadline task is earlier than the 2nd deadline task
     *         based on the endDate and endTime
     * @return false, if otherwise.
     */
    private static boolean isAddDeadlineEarlierAddListIndex(TestTask toAdd, TestTask task) {
        if (toAdd.getEndDate().value.substring(toAdd.getEndDate().value.length() - 2).compareTo(
                task.getEndDate().value.substring(task.getEndDate().value.length() - 2)) < 0) {
            return true;
        } else {
            if (toAdd.getEndDate().value.substring(toAdd.getEndDate().value.length() - 2).compareTo(
                    task.getEndDate().value.substring(task.getEndDate().value.length() - 2)) == 0) {
                if (toAdd.getEndDate().value
                        .substring(toAdd.getEndDate().value.length() - 5, toAdd.getEndDate().value.length() - 3)
                        .compareTo(
                                task.getEndDate().value.substring(task.getEndDate().value.length() - 5,
                                        task.getEndDate().value.length() - 3)) < 0) {
                    return true;
                } else {
                    if (toAdd.getEndDate().value
                            .substring(toAdd.getEndDate().value.length() - 5, toAdd.getEndDate().value.length() - 3)
                            .compareTo(task.getEndDate().value.substring(
                                    task.getEndDate().value.length()
                                            - 5,
                                    task.getEndDate().value.length() - 3)) == 0) {
                        if (toAdd.getEndDate().value.substring(0, toAdd.getEndDate().value.length() - 6)
                                .compareTo(task.getEndDate().value.substring(0,
                                        task.getEndDate().value.length() - 6)) < 0) {
                            return true;
                        } else {
                            if (toAdd.getEndDate().value.substring(0, toAdd.getEndDate().value.length() - 6)
                                    .compareTo(task.getEndDate().value.substring(0,
                                            task.getEndDate().value.length() - 6)) == 0) {
                                if (toAdd.getEndTime().value.compareTo(task.getEndTime().value) < 0) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Finds the sorted position to add new task to the existing list of task.
     * List of tasks is sorted firstly based on type of task and then by chronological order of the task
     *
     * Event tasks sorted by startDate startTime.
     * Deadline tasks sorted by endDate endTime.
     * Floating tasks are just added to the bottom of the list as there is no time element within a floating task.
     *
     * @return The sorted position index to add the new task in the sorted list
     *         of tasks.
     */
    private static int findSortedPositionToAdd(TestTask toAdd, List<TestTask> taskList) {
        int addIndex = 0;
        if (!taskList.isEmpty()) {
            if (toAdd.isEventTask()) {
                while (taskList.get(addIndex).isEventTask()) {
                    if (isAddEventEarlierAddListIndex(toAdd, taskList.get(addIndex))) {
                        break;
                    }
                    addIndex++;
                    if (addIndex == taskList.size()) {
                        break;
                    }
                }
            }

            if (toAdd.isDeadlineTask()) {
                while (taskList.get(addIndex).isEventTask()) {
                    addIndex++;
                    if (addIndex == taskList.size()) {
                        break;
                    }
                }
                while ((addIndex != taskList.size()) && taskList.get(addIndex).isDeadlineTask()) {
                    if (isAddDeadlineEarlierAddListIndex(toAdd, taskList.get(addIndex))) {
                        break;
                    }
                    addIndex++;
                    if (addIndex == taskList.size()) {
                        break;
                    }
                }
            }

            if (toAdd.isFloatingTask()) {
                addIndex = taskList.size();
            }
        }
        return addIndex;
    }

}
