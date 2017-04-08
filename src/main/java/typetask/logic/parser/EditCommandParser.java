package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.logic.parser.CliSyntax.PREFIX_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static typetask.logic.parser.CliSyntax.PREFIX_START_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import typetask.commons.core.Messages;
import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.Command;
import typetask.logic.commands.EditCommand;
import typetask.logic.commands.EditCommand.EditTaskDescriptor;
import typetask.logic.commands.IncorrectCommand;

//@@author A0139926R
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    private static final int day = 0;
    private static final int month = 1;
    private static final int dayDate = 2;
    private static final int time = 3;
    private static final int year = 5;
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_PRIORITY);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        List<Date> endDateList;
        List<Date> startDateList;
        List<Date> timeList;
        List<Date> dateList;
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            //Checks for valid deadline. If valid, store new deadline for editing
            if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
                dateList = DateParser.parse(argsTokenizer.getValue(PREFIX_DATE).get());
                if (!DateParser.checkValidDateFormat(dateList)) {
                    return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
                }
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_DATE).get()));
                Optional<String> emptyString = Optional.of("");
                editTaskDescriptor.setEndDate(ParserUtil.parseDate(parseDate));
                editTaskDescriptor.setDate(ParserUtil.parseDate(emptyString));
            }
            //Checks for valid deadline. If valid, store new deadline for editing
            if (argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
                timeList = DateParser.parse(argsTokenizer.getValue(PREFIX_TIME).get());
                if (!DateParser.checkValidDateFormat(timeList)) {
                    return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
                }
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_TIME).get()));
                Optional<String> emptyString = Optional.of("");
                editTaskDescriptor.setEndDate(ParserUtil.parseDate(parseDate));
                editTaskDescriptor.setDate(ParserUtil.parseDate(emptyString));
            }
            //Checks for valid start date. If valid, store new start date for editing
            if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent()) {
                startDateList = DateParser.parse(argsTokenizer.getValue(PREFIX_START_DATE).get());
                if (!DateParser.checkValidDateFormat(startDateList)) {
                    return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE);
                }
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_START_DATE).get()));
                editTaskDescriptor.setDate(ParserUtil.parseDate(parseDate));
            }
            //Checks for valid end date. If valid, store new end date for editing
            if (argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
                endDateList = DateParser.parse(argsTokenizer.getValue(PREFIX_END_DATE).get());
                if (!DateParser.checkValidDateFormat(endDateList)) {
                    return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE);
                }
                Optional<String> parseDate = Optional.of(getDate(argsTokenizer.getValue(PREFIX_END_DATE).get()));
                editTaskDescriptor.setEndDate(ParserUtil.parseDate(parseDate));
            }
            //Checks for valid schedule
            if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() &&
                    argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
                startDateList = DateParser.parse(argsTokenizer.getValue(PREFIX_START_DATE).get());
                endDateList = DateParser.parse(argsTokenizer.getValue(PREFIX_END_DATE).get());
                if (!DateParser.checkValidSchedule(startDateList, endDateList)) {
                    return new IncorrectCommand(Messages.MESSAGE_INVALID_START_AND_END_DATE);
                }
            }
            if (argsTokenizer.getValue(PREFIX_PRIORITY).isPresent()) {
                editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
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
        assert date != "";
        List<Date> dates = DateParser.parse(date);
        String nattyDate = dates.get(0).toString();
        String[] splitDate = nattyDate.split(" ");
        String finalizedDate = splitDate[day] + " " + splitDate[month] + " " + splitDate[dayDate] +
                " " + splitDate[year] + " " + splitDate[time];
        return finalizedDate;
    }

}
