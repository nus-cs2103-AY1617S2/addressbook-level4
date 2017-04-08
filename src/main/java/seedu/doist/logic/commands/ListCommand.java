package seedu.doist.logic.commands;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.Map;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.parser.CliSyntax;
import seedu.doist.logic.parser.ParserUtil;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;

/**
 * Lists all tasks in the todo list to the user.
 */
public class ListCommand extends Command {

    //@@author A0147980U
    public enum TaskType {
        PENDING,
        FINISHED,
        OVERDUE,
        ALL
    }
    //@@author A0140887W
    public static final String DEFAULT_COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ": List tasks as specified by the parameters\n"
            + "TYPE could be pending, overdue or finished. If no TYPE is specified, all tasks will be listed.\n"
            + "Parameters: [TYPE] [\\from START_TIME] [\\to END_TIME] [\\as PRIORITY] [\\under TAG...]\n"
            + "Example: " + DEFAULT_COMMAND_WORD + " pending \\under school ";
    //public static final String MESSAGE_INVALID_PREAMBLE = "Invalid list type! Type should be pending,"
    //                                                        + " overdue or finished";
    public static final String MESSAGE_SUCCESS = "Listed %1$s tasks";
    public static final String MESSAGE_PENDING = String.format(MESSAGE_SUCCESS, "pending");
    public static final String MESSAGE_FINISHED = String.format(MESSAGE_SUCCESS, "finished");
    public static final String MESSAGE_OVERDUE = String.format(MESSAGE_SUCCESS, "overdue");
    public static final String MESSAGE_ALL = String.format(MESSAGE_SUCCESS, "all");

    //@@author A0147980U
    private UniqueTagList tagList = new UniqueTagList();
    private TaskType type = null;

    public ListCommand(String preamble, Map<String, List<String>> parameters) throws IllegalValueException {
        if (!preamble.trim().isEmpty()) {
            String processedPreamble = processListPreamble(preamble);
            // pending, overdue or finished
            try {
                type = TaskType.valueOf(processedPreamble);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                              MESSAGE_USAGE));
            }
        } else {
            listDefault();
        }
        List<String> tagsParameterStringList = parameters.get(CliSyntax.PREFIX_UNDER.toString());
        if (tagsParameterStringList != null && !tagsParameterStringList.isEmpty()) {
            tagList = ParserUtil.parseTagsFromString(tagsParameterStringList.get(0));
        }
    }

    //@@author A0140887W
    /** Trims trailing whitespace, removes leading whitespace, replaces in-between whitespaces with one underscore
     * and converts to uppercase letters. This is so that it can be converted to the TaskType enum.
     *
     * @param preamble the pre-processed preamble of the list command
     * @return the processed preamble
     * */
    private String processListPreamble(String preamble) {
        // remove all trailing spaces, new line characters etc
        String processedPreamble = preamble.trim();

        // remove all leading spaces, new line characters etc
        processedPreamble = processedPreamble.replaceAll("^\\s+", "");

        // replace in-between spaces, new line characters etc with _
        processedPreamble = processedPreamble.replaceAll("\\s+", "_");

        // change to uppercase
        processedPreamble = processedPreamble.toUpperCase();
        return processedPreamble;
    }

    /** Default list type if there is no preamble */
    private void listDefault() {
        type = TaskType.ALL;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.updateFilteredTaskList(type, tagList);
        model.sortTasksByDefault();
        String message = "";
        if (type != null) {
            if (type.equals(TaskType.PENDING)) {
                message = MESSAGE_PENDING;
            } else if (type.equals(TaskType.FINISHED)) {
                message = MESSAGE_FINISHED;
            } else if (type.equals(TaskType.ALL)) {
                message = MESSAGE_ALL;
            } else if (type.equals(TaskType.OVERDUE)) {
                message = MESSAGE_OVERDUE;
            } else {
                message = "";
            }
        } else {
            assert false : "type should not be null!";
        }
        CommandResult commandResult = tagList.isEmpty() ?
                                      new CommandResult(message) :
                                      new CommandResult(getSuccessMessageListUnder(message, tagList));
        return commandResult;
    }

    public static String getSuccessMessageListUnder(String messageSuccess, UniqueTagList tagList) {
        String message = messageSuccess + " under: ";
        for (Tag tag : tagList) {
            message += tag.tagName + " ";
        }
        message = message.trim();
        return message;
    }
}
