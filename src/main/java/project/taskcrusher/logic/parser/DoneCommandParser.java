package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.DeleteCommand;
import project.taskcrusher.logic.commands.DoneCommand;
import project.taskcrusher.logic.commands.EditCommand;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.EditCommand.EditTaskDescriptor;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
    	
    	args = args + " d/ completed";
    	ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_DEADLINE, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setDeadline(ParserUtil.parseDeadline(argsTokenizer.getValue(PREFIX_DEADLINE)));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);    }

}
