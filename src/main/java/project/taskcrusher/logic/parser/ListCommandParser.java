package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DATE;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.ListCommand;
import project.taskcrusher.model.task.Deadline;

//@@author A0163962X
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListCommandParser {

    private final boolean showActiveOnly = true;
    private final boolean showCompleteOnly = true;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListCommand and returns a ListCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE);
        argsTokenizer.tokenize(args);

        String flag = ParserUtil.setValue(argsTokenizer.getPreamble(), ListCommand.NO_FLAG);
        String date = ParserUtil.setValue(argsTokenizer, PREFIX_DATE, Deadline.NO_DEADLINE);

        try {
            switch (flag) {
            case ListCommand.NO_FLAG:
                return new ListCommand(date, showActiveOnly, !showCompleteOnly);
            case ListCommand.ALL_FLAG:
                return new ListCommand(date, !showActiveOnly, !showCompleteOnly);
            case ListCommand.COMPLETE_FLAG:
                return new ListCommand(date, !showActiveOnly, showCompleteOnly);
            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

}
