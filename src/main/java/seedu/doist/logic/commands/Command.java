package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import seedu.doist.commons.core.Messages;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.Model;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

    private static HashMap<String, ArrayList<String>> commandAliases;

    public static void setDefaultCommandWords() {
        commandAliases = new HashMap<String, ArrayList<String>>();
        commandAliases.put("add",  new ArrayList<>(Arrays.asList("do")));
        commandAliases.put("clear",  new ArrayList<>());
        commandAliases.put("delete",  new ArrayList<>(Arrays.asList("del")));
        commandAliases.put("edit",  new ArrayList<>(Arrays.asList("update")));
        commandAliases.put("exit",  new ArrayList<>());
        commandAliases.put("find",  new ArrayList<>());
        commandAliases.put("finish",  new ArrayList<>(Arrays.asList("fin")));
        commandAliases.put("help",  new ArrayList<>());
        commandAliases.put("list",  new ArrayList<>(Arrays.asList("ls")));
        commandAliases.put("select",  new ArrayList<>());
        commandAliases.put("sort",  new ArrayList<>(Arrays.asList("sort_by")));
        commandAliases.put("unfinish",  new ArrayList<>(Arrays.asList("unfin")));
        commandAliases.put("alias",  new ArrayList<>());
        commandAliases.put("reset_alias",  new ArrayList<>());
        commandAliases.put("view_alias",  new ArrayList<>(Arrays.asList("list_alias", "ls_alias")));
    }

    public static Set<String> getDefaultCommandWordSet() {
        if (commandAliases == null) {
            setDefaultCommandWords();
        }
        return commandAliases.keySet();
    }

    public static ArrayList<String> getAliasList(String defaultCommandWord) {
        if (commandAliases == null) {
            setDefaultCommandWords();
        }
        return commandAliases.get(defaultCommandWord);
    }

    public static void setAlias(String alias, String commandWord) {
        assert(commandAliases.get(commandWord) != null);
        if (commandAliases == null) {
            setDefaultCommandWords();
        }
        for (String word : commandAliases.keySet()) {
            commandAliases.get(word).remove(alias);
        }
        ArrayList<String> aliases = commandAliases.get(commandWord);
        aliases.add(alias);
        commandAliases.replace(commandWord, aliases);
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Constructs a feedback message to summarise an operation that sorted a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListSortedSummary(SortCommand.SortType sortType) {
        return String.format(Messages.MESSAGE_TASKS_SORTED_OVERVIEW, sortType.toString());
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    public ArrayList<ReadOnlyTask> getMultipleTasksFromIndices(int[] targetIndices) throws CommandException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ArrayList<ReadOnlyTask> relatedTasks = new ArrayList<ReadOnlyTask>();

        for (int targetIndex : targetIndices) {
            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            ReadOnlyTask relatedTask = lastShownList.get(targetIndex - 1);
            relatedTasks.add(relatedTask);
        }
        return relatedTasks;
    }
}



