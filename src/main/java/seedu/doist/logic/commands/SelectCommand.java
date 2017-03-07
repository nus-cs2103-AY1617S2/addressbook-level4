package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.core.Messages;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.commons.events.ui.JumpToListRequestEvent;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * Selects a person identified using it's last displayed index from the address
 * book.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("select"));
    public static final String DEFAULT_COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = info().getUsageTextForCommandWords()
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + DEFAULT_COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex));

    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
