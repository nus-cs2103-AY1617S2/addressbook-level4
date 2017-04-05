package guitests.guihandles;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.ReadOnlyTodo;

/**
 * Provides a handle to a todo card in the todo list panel.
 */
public class TodoCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String STARTTIME_FIELD_ID = "#start";
    private static final String ENDTIME_FIELD_ID = "#end";
    private static final String COMPLETETIME_FIELD_ID = "#complete";

    private Node node;

    public TodoCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }
    //@@author A0165043M
    private String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    private String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }
    //@@author
    //@@author A0163786N
    private String getCompleteTime() {
        return getTextFromLabel(COMPLETETIME_FIELD_ID);
    }
    //@@author
    //@@author A0163786N
    public boolean isSameTodo(ReadOnlyTodo todo, boolean checkCompleteTime) {
        if (checkCompleteTime) {
            DateFormat completeCommandDateFormat = new SimpleDateFormat(DATE_FORMAT);
            if (todo.getCompleteTime() != null && !getCompleteTime().equals("Completed at "
                + completeCommandDateFormat.format(todo.getCompleteTime()))) {
                return false;
            }
        }
        return isSameTodo(todo);
    }
    //@@author
    //@@author A0163786N
    public boolean isSameTodo(ReadOnlyTodo todo) {
        DateFormat addCommandDateFormat = new SimpleDateFormat(DATE_FORMAT);

        if (todo.getEndTime() != null
                && !getEndTime().equals("End: " + addCommandDateFormat.format(todo.getEndTime()))) {
            return false;
        }

        if (todo.getStartTime() != null
                && !getStartTime().equals("Start: " + addCommandDateFormat.format(todo.getStartTime()))) {
            return false;
        }
        if (todo.getCompleteTime() != null && getCompleteTime().equals("Not Complete")) {
            return false;
        }
        return getFullName().equals(todo.getName().fullName) &&
                equalLists(getTags(), getTags(todo.getTags()));
    }
    //@@author
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TodoCardHandle) {
            TodoCardHandle handle = (TodoCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && equalLists(getTags(), handle.getTags());
        }
        return super.equals(obj);
    }
    //@@author A0165043M
    private boolean equalLists (List<String> str1, List<String> str2) {
        Collections.sort(str1);
        Collections.sort(str2);
        return str1.equals(str2);
    }
    //@@author
    @Override
    public String toString() {
        return getFullName();
    }
}
