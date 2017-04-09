package seedu.taskmanager.logic.commands;

//import static seedu.taskmanager.logic.parser.Parser.BASIC_COMMAND_FORMAT;
//
//import java.util.Optional;
//import java.util.regex.Matcher;
//
//import seedu.taskmanager.commons.core.EventsCenter;
//import seedu.taskmanager.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
//import seedu.taskmanager.logic.parser.EditCommandParser;
//import seedu.taskmanager.logic.parser.ParserUtil;
import seedu.taskmanager.model.HistoryManager;

// @@author A0140032E
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successful: %1$s";
    public static final String MESSAGE_NO_MORE_UNDO = "No more actions available to undo";

    @Override
    public CommandResult execute() throws CommandException {
        HistoryManager historyManager = HistoryManager.getInstance();
        String commandText;
        try {
            commandText = historyManager.undo();
            //highlightChanges(commandText);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException(MESSAGE_NO_MORE_UNDO);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandText));
    }

    // @@author A0131278H
    /**
     * Highlights the changes caused by undo command according to
     * {@code commandText}
     */
    /*private void highlightChanges(String commandText) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandText.trim());
        System.out.println(commandText);
        // assert matcher.matches();
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // commands not covered here do not need a highlighting behavior
        switch (commandWord) {
        case DeleteCommand.COMMAND_WORD:
            highlightByIndex(arguments);
            break;
        case DoneCommand.COMMAND_WORD:
            highlightByIndex(arguments);
            break;
        case UndoneCommand.COMMAND_WORD:
            highlightByIndex(arguments);
            break;
        case EditCommand.COMMAND_WORD:
            EditCommand editCommand = (EditCommand) new EditCommandParser().parse(arguments);
            highlightByIndex(Integer.toString(editCommand.getFilteredSelectedTaskListIndex() + 1));
            break;
        }
    }

    /**
     * Highlights the task by index according to {@code indexString} parsed in
     */
    /*private void highlightByIndex(String indexString) {
        Optional<Integer> index = ParserUtil.parseIndex(indexString);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index.get() - 1));
    }*/
    // @@author
}
// @@author
