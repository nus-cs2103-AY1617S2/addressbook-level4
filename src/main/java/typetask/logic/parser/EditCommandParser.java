package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.logic.parser.CliSyntax.PREFIX_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_START_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.Command;
import typetask.logic.commands.EditCommand;
import typetask.logic.commands.EditCommand.EditTaskDescriptor;
import typetask.logic.commands.IncorrectCommand;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {
    //@@author A0139926R
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_START_DATE,
                        PREFIX_END_DATE);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_DATE).get()));
                Optional<String> emptyString = Optional.of("");
                editTaskDescriptor.setEndDate(ParserUtil.parseDate(parseDate));
                editTaskDescriptor.setDate(ParserUtil.parseDate(emptyString));
            }
            if (argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_TIME).get()));
                editTaskDescriptor.setEndDate(ParserUtil.parseDate(parseDate));
            }
            if (argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_END_DATE).get()));
                editTaskDescriptor.setEndDate(ParserUtil.parseDate(parseDate));
            }
            if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent()) {
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_START_DATE).get()));
                editTaskDescriptor.setDate(ParserUtil.parseDate(parseDate));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }
  //@@author A0139926R
    public String getDate(String date) {
        if (date.isEmpty()) {
            return "";
        }
        List<Date> dates = DateParser.parse(date);
        String nattyDate = dates.get(0).toString();
        String[] splitDate = nattyDate.split(" ");
        String finalizedDate = splitDate[0] + " " + splitDate[1] + " " + splitDate[2] +
                " " + splitDate[3];
        return finalizedDate;
    }

}
