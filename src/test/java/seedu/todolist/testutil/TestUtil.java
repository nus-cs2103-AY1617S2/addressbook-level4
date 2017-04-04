package seedu.todolist.testutil;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;

import com.google.common.io.Files;

import guitests.guihandles.TodoCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import seedu.todolist.TestApp;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.FileUtil;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.commons.util.XmlUtil;
import seedu.todolist.logic.commands.SaveFileCommand;
import seedu.todolist.model.TodoList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.Name;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.storage.XmlSerializableTodoList;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static final String LS = System.lineSeparator();

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static final Todo[] SAMPLE_TODO_DATA = getSampleTodoData();

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

    private static Todo[] getSampleTodoData() {
        try {
            //CHECKSTYLE.OFF: LineLength
            return new Todo[]{
                new Todo(new Name("Walk the dog"), new UniqueTagList()),
                new Todo(new Name("Walk the cat"), new UniqueTagList()),
                new Todo(new Name("Do math homework"), new UniqueTagList()),
                new Todo(new Name("Do english homework"), new UniqueTagList()),
                new Todo(new Name("Wash dishes"), new UniqueTagList()),
                new Todo(new Name("Mow the lawn"), new UniqueTagList()),
                new Todo(new Name("Cook dinner"), new UniqueTagList()),
                new Todo(new Name("Do laundry"), new UniqueTagList()),
                new Todo(new Name("Wash car"), new UniqueTagList())
            };
            //CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }


    private static Tag[] getSampleTagData() {
        try {
            return new Tag[]{
                new Tag("relatives"),
                new Tag("friends")
            };
        } catch (IllegalValueException e) {
            assert false;
            return null;
            //not possible
        }
    }

    public static List<Todo> generateSampleTodoData() {
        return Arrays.asList(SAMPLE_TODO_DATA);
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
        createDataFileWithData(generateSampleStorageTodoList(), filePath);
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

    public static XmlSerializableTodoList generateSampleStorageTodoList() {
        return new XmlSerializableTodoList(new TodoList());
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
     * Removes a subset from the list of todos.
     * @param todos The list of todos
     * @param todosToRemove The subset of todos.
     * @return The modified todos after removal of the subset from todos.
     */
    public static TestTodo[] removeTodosFromList(final TestTodo[] todos, TestTodo... todosToRemove) {
        List<TestTodo> listOfTodos = asList(todos);
        listOfTodos.removeAll(asList(todosToRemove));
        return listOfTodos.toArray(new TestTodo[listOfTodos.size()]);
    }


    /**
     * Returns a copy of the list with the todo at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. index 1 if the first element is to be removed
     */
    public static TestTodo[] removeTodoFromList(final TestTodo[] list, int targetIndexInOneIndexedFormat) {
        return removeTodosFromList(list, list[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Replaces todos[i] with a todo.
     * @param todos The array of todos.
     * @param todo The replacement todo
     * @param index The index of the todo to be replaced.
     * @return
     */
    public static TestTodo[] replaceTodoFromList(TestTodo[] todos, TestTodo todo, int index) {
        todos[index] = todo;
        return todos;
    }

    /**
     * Appends todos to the array of todos.
     * @param todos A array of todos.
     * @param todosToAdd The todos that are to be appended behind the original array.
     * @return The modified array of todos.
     */
    public static TestTodo[] addTodosToList(final TestTodo[] todos, TestTodo... todosToAdd) {
        List<TestTodo> listOfTodos = asList(todos);
        listOfTodos.addAll(asList(todosToAdd));
        return listOfTodos.toArray(new TestTodo[listOfTodos.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }
    //@@author A0163786N
    /**
     * Completes the todo at targetIndexOneIndexedFormat
     * @param todos The list of todos
     * @param targetIndexOneIndexedFormat index of todo to be completed
     * @param completeTime
     * @return The modified todos after completing the specified todo.
     */
    public static TestTodo[] completeTodoInList(final TestTodo[] todos, int targetIndexOneIndexedFormat,
            String completeTime) {
        Date completeDate = null;
        try {
            completeDate = StringUtil.parseDate(completeTime, DATE_FORMAT);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "invalid complete time format";
        }
        int index = targetIndexOneIndexedFormat - 1; //array is zero indexed
        TestTodo todoToComplete = todos[index];
        todoToComplete.setCompleteTime(completeDate);
        return replaceTodoFromList(todos, todoToComplete, index);
    }
    //@@author
    //@@author A0163786N
    /**
     * Uncompletes the todo at targetIndexOneIndexedFormat
     * @param todos The list of todos
     * @param targetIndexOneIndexedFormat index of todo to be uncompleted
     * @return The modified todos after uncompleting the specified todo.
     */
    public static TestTodo[] uncompleteTodoInList(final TestTodo[] todos, int targetIndexOneIndexedFormat) {
        int index = targetIndexOneIndexedFormat - 1; //array is zero indexed
        TestTodo todoToUncomplete = todos[index];
        todoToUncomplete.setCompleteTime(null);
        return replaceTodoFromList(todos, todoToUncomplete, index);
    }
    //@@author
    public static boolean compareCardAndTodo(TodoCardHandle card, ReadOnlyTodo todo, boolean compareCompleteTime) {
        return card.isSameTodo(todo, compareCompleteTime);
    }

    public static Tag[] getTagList(String tags) {
        if ("".equals(tags)) {
            return new Tag[]{};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Tag(e.replaceFirst("Tag: ", ""));
            } catch (IllegalValueException e1) {
                //not possible
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

    //@@author A0163720M
    public static String getSaveFileCommand(String saveFile) {
        StringBuilder sb = new StringBuilder();
        sb.append(SaveFileCommand.COMMAND_WORD + " " + saveFile);
        return sb.toString();
    }
    //@@author
}
