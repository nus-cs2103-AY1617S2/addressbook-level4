//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public abstract class TagController extends Controller {

    private static final String PARAMETER_INDEX = "index";
    private static final String PARAMETER_KEYWORDS = "keywords";

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = StringUtil.SINGLE_SPACE;
    private static final int SECTION_INDEX = 0;
    private static final int SECTION_KEYWORDS = 1;

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        if (isInvalidFormat(tokens)) {
            showInvalidFormatMessage();
            return;
        }
        if (isIndexOutOfBounds(tokens)) {
            throw new InvalidCommandException(Messages.MESSAGE_INVALID_TASK_INDEX);
        }

        ArrayList<String> successfulList = new ArrayList<String>();
        ArrayList<String> failedList = new ArrayList<String>();
        UiStore uiStore = UiStore.getInstance();
        Task task = getTaskFromIndex(Integer.parseInt(tokens.get(PARAMETER_INDEX)) - 1);
        modifyTagsForTask(successfulList, failedList,
                task, tokens.get(PARAMETER_KEYWORDS));

        updateList(task);

        uiStore.setCommandResult(formatDisplay(successfulList.toArray(new String[successfulList.size()]),
                                failedList.toArray(new String[failedList.size()]),
                                successfulList.size()));
    }

    private Task getTaskFromIndex(int index) {
        return UiStore.getInstance().getShownTasks().get(index);
    }

    protected abstract void showInvalidFormatMessage() throws InvalidCommandException;

    protected boolean isIndexOutOfBounds(Map<String, String> tokens) {
        int index = Integer.parseInt(tokens.get(PARAMETER_INDEX)) - 1;
        if (index < 0 || index >= UiStore.getInstance().getShownTasks().size()) {
            return true;
        }
        return false;
    }

    protected boolean isInvalidFormat(Map<String, String> tokens) {
        String index = tokens.get(PARAMETER_INDEX);
        if (index.equals("") || tokens.get(PARAMETER_KEYWORDS).equals("")) {
            return true;
        }
        if (!StringUtils.isNumeric(index)) {
            return true;
        }

        return false;
    }

    protected void updateList(Task task) {
        TodoList todoList = TodoList.getInstance();
        UiStore uiStore = UiStore.getInstance();
        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks(), task);
        }
    }

    protected abstract void modifyTagsForTask(ArrayList<String> successfulList,
            ArrayList<String> failedList, Task task, String keywords);

    protected abstract CommandResult formatDisplay(String[] successfulList, String[] failedList, int successCount);

    public Map<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        String[] listOfParameters = extractCommandWords(command);
        try {
            tokens.put(PARAMETER_INDEX, listOfParameters[SECTION_INDEX]);
            tokens.put(PARAMETER_KEYWORDS, listOfParameters[SECTION_KEYWORDS]);
        } catch (Exception e) {
            tokens.put(PARAMETER_INDEX, "");
            tokens.put(PARAMETER_KEYWORDS, "");
        }

        return tokens;
    }

    protected String[] extractCommandWords(String command) {
        return command.split(COMMAND_SPLITTER_REGEX, NUMBER_OF_SPLITS_FOR_COMMAND_PARSE);
    }
}
