package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.parser.ParserUtil;
import seedu.doist.model.tag.UniqueTagList;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("list", "ls"));
    public static final String DEFAULT_COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = info().getUsageTextForCommandWords()
            + ": List tasks satisfying the requirements specified by the parameters\n"
            + "Parameters: TYPE [\\from START_TIME] [\\to END_TIME] [\\as PRIORITY] [\\under TAG...]\n"
            + "Example: " + DEFAULT_COMMAND_WORD + "pending \\under school ";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    private UniqueTagList tagList = new UniqueTagList();

    public ListCommand(String preamble, Map<String, List<String>> parameters) throws IllegalValueException {
        List<String> tagsParameterStringList = parameters.get("\\under");
        if (tagsParameterStringList != null && !tagsParameterStringList.isEmpty()) {
            tagList = ParserUtil.parseTagsFromString(tagsParameterStringList.get(0));
        }
    }

    @Override
    public CommandResult execute() {
        if (tagList.isEmpty()) {
            model.updateFilteredListToShowAll();
        } else {
            model.updateFilteredTaskList(tagList);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
