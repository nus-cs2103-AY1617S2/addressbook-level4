package seedu.taskboss.testutil;

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

import guitests.guihandles.CategoryCardHandle;
import guitests.guihandles.TaskCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import seedu.taskboss.TestApp;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.commons.util.FileUtil;
import seedu.taskboss.commons.util.XmlUtil;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.storage.XmlSerializableTaskBoss;

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
            //CHECKSTYLE.OFF: LineLength
            return new Task[]{
                new Task(new Name("Ali Muster"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("4th street"),
                        new Recurrence(Frequency.DAILY), new UniqueCategoryList()),
                new Task(new Name("Boris Mueller"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("81th street"),
                        new Recurrence(Frequency.WEEKLY), new UniqueCategoryList()),
                new Task(new Name("Carl Kurz"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("wall street"),
                        new Recurrence(Frequency.MONTHLY), new UniqueCategoryList()),
                new Task(new Name("Daniel Meier"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("10th street"),
                        new Recurrence(Frequency.YEARLY), new UniqueCategoryList()),
                new Task(new Name("Elle Meyer"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("michegan ave"),
                        new Recurrence(Frequency.NONE), new UniqueCategoryList()),
                new Task(new Name("Fiona Kunz"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("little tokyo"),
                        new Recurrence(Frequency.DAILY), new UniqueCategoryList()),
                new Task(new Name("George Best"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("4th street"),
                        new Recurrence(Frequency.WEEKLY), new UniqueCategoryList()),
                new Task(new Name("Hoon Meier"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 28, 2017"), new Information("little india"),
                        new Recurrence(Frequency.MONTHLY), new UniqueCategoryList()),
                new Task(new Name("Ida Mueller"), new PriorityLevel("Yes"), new DateTime("Feb 19, 2017"),
                        new DateTime("Feb 20, 2017"), new Information("chicago ave"),
                        new Recurrence(Frequency.YEARLY), new UniqueCategoryList()),
                new Task(new Name("Johnny Depp"), new PriorityLevel("Yes"), new DateTime("next sat 5pm"),
                        new DateTime("tomorrow"), new Information("Silicon Valley"),
                        new Recurrence(Frequency.NONE), new UniqueCategoryList()),
                new Task(new Name("Kelvin Koo"), new PriorityLevel("Yes"), new DateTime("today 10am"),
                        new DateTime("next wed"), new Information("clementi ave 2"),
                        new Recurrence(Frequency.DAILY), new UniqueCategoryList())

            };
            //CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }


    private static Category[] getSampleCategoryData() {
        try {
            return new Category[]{
                new Category("relatives"),
                new Category("friends")
            };
        } catch (IllegalValueException e) {
            assert false;
            return null;
            //not possible
        }
    }

    public static List<Task> generateSampleTaskData() {
        return Arrays.asList(SAMPLE_TASK_DATA);
    }

    /**
     * Appends the file name to the sandbox folder path.
     * Creates the sandbox folder if it doesn't exist.
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
        createDataFileWithData(generateSampleStorageTaskBoss(), filePath);
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

    public static XmlSerializableTaskBoss generateSampleStorageTaskBoss() {
        return new XmlSerializableTaskBoss(new TaskBoss());
    }

    /**
     * Tweaks the {@code keyCodeCombination} to resolve the {@code KeyCode.SHORTCUT} to their
     * respective platform-specific keycodes
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
        return keys.toArray(new KeyCode[]{});
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
                + Arrays.asList(comparedObjects).stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public static void setFinalStatic(Field field, Object newValue) throws NoSuchFieldException,
                                                                           IllegalAccessException {
        field.setAccessible(true);
        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        // ~Modifier.FINAL is used to remove the final modifier from field so that its value is no longer
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
     * Gets private method of a class
     * Invoke the method using method.invoke(objectInstance, params...)
     *
     * Caveat: only find method declared in the current Class, not inherited from supertypes
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
     * @param tasks The list of tasks
     * @param tasksToRemove The subset of tasks.
     * @return The modified tasks after removal of the subset from tasks.
     */
    public static TestTask[] removeTasksFromList(final TestTask[] tasks, TestTask... tasksToRemove) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(tasksToRemove));
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }


    /**
     * Returns a copy of the list with the task at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. index 1 if the first element is to be removed
     */
    public static TestTask[] removeTaskFromList(final TestTask[] list, int targetIndexInOneIndexedFormat) {
        return removeTasksFromList(list, list[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Replaces tasks[i] with a task.
     * @param tasks The array of tasks.
     * @param task The replacement task
     * @param index The index of the task to be replaced.
     * @return
     */
    public static TestTask[] replaceTaskFromList(TestTask[] tasks, TestTask task, int index) {
        tasks[index] = task;
        return tasks;
    }

    /**
     * Appends tasks to the array of tasks.
     * @param tasks A array of tasks.
     * @param tasksToAdd The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestTask[] addTasksToList(final TestTask[] tasks, TestTask... tasksToAdd) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(tasksToAdd));
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    /**
     * Appends categories to the array of categories.
     * @param categories An array of categories.
     * @param categoriesToAdd The categories that are to be appended behind the original array.
     * @return The modified array of categories.
     */
    public static TestCategory[] addCategoriesToList(final TestCategory[] categories,
            TestCategory... categoriesToAdd) {
        List<TestCategory> listOfCategories = asList(categories);
        listOfCategories.addAll(asList(categoriesToAdd));
        return listOfCategories.toArray(new TestCategory[listOfCategories.size()]);
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

    public static boolean compareCardAndCategory(CategoryCardHandle card, TestCategory category) {
        return card.isSameCategory(category);
    }

    public static Category[] getCategoryList(String categories) {
        if ("".equals(categories)) {
            return new Category[]{};
        }

        final String[] split = categories.split(", ");

        final List<Category> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Category(e.replaceFirst("Category: ", ""));
            } catch (IllegalValueException e1) {
                //not possible
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Category[split.length]);
    }

}
