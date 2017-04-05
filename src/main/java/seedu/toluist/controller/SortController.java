//@@author A0162011A
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Sort Controller is responsible for changing the order of the displayed tasks
 */
public class SortController extends Controller {
    private static final String RESULT_MESSAGE_ALREADY_SORTED = "List is already sorted by %s!";
    private static final String RESULT_MESSAGE_SUCCESS = "List is now sorted by %s.";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*sort.*";
    private static final String COMMAND_WORD = "sort";
    private static final String WORD_BY = "by";

    private static final String[] KEYWORD_CATEGORIES = { "priority", "overdue", "enddate",
        "startdate", "description", "default" };
    private static final String PARAMETER_CATEGORY = "category";

    private static final Logger logger = LogsCenter.getLogger(SortController.class);

    private static final String HELP_DETAILS = "Changes the sorting order of the tasks.";
    private static final String HELP_FORMAT = "sort CATEGORY(priority/overdue/enddate/startdate/description/default)";
    private static final String[] HELP_COMMENTS = { "The default sorting order is: "
                                                    + "overdue, priority, end date, start date, description",
                                                    "Using this command will push the chosen category"
                                                    + " to the top of the sorting order." };
    private static final String[] HELP_EXAMPLES = { "`sort priority`\nReorders the task list by description.",
                                                    "`sort overdue`\nReorders the task list by description.",
                                                    "`sort enddate`\nReorders the task list by description.",
                                                    "`sort startdate`\nReorders the task list by description.",
                                                    "`sort description`\nReorders the task list by description.",
                                                    "`sort default`\nResets the task list to the default ordering." };

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass().toString() + " will handle command");

        String keyword = tokens.get(PARAMETER_CATEGORY);
        if (keyword.equals(StringUtil.EMPTY_STRING)) {
            UiStore.getInstance().setCommandResult(new CommandResult("Invalid format"));
            return;
        }
        if (!isKeywordInCategoryList(keyword)) {
            UiStore.getInstance().setCommandResult(new CommandResult("Invalid parameter"));
            return;
        }

        if (Task.sortBy(keyword.toLowerCase())) { //returns true if successful
            UiStore.getInstance().setCommandResult(new CommandResult(
                    String.format(RESULT_MESSAGE_SUCCESS, keyword)));
        } else {
            UiStore.getInstance().setCommandResult(new CommandResult(
                    String.format(RESULT_MESSAGE_ALREADY_SORTED, keyword)));
        }


        uiStore.setTasks(TodoList.getInstance().getTasks());
    }

    private boolean isKeywordInCategoryList(String keyword) {
        for (String category : KEYWORD_CATEGORIES) {
            if (keyword.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();
        String replacedCommand = Pattern.compile(WORD_BY, Pattern.CASE_INSENSITIVE).matcher(command)
                .replaceFirst(StringUtil.EMPTY_STRING);
        replacedCommand = Pattern.compile(COMMAND_WORD, Pattern.CASE_INSENSITIVE).matcher(replacedCommand)
                .replaceFirst(StringUtil.EMPTY_STRING).trim();

        tokens.put(PARAMETER_CATEGORY, replacedCommand);
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
