//@@author A0139925U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.DELIMITER_EDIT_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.DELIMITER_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.KEYWORD_EDIT_MULTI_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.KEYWORD_EDIT_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.KEYWORD_EDIT_PARAMETER_VALUE;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_DATE;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_TIME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_NAME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_DATE;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_TIME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Name;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    public static final String MESSAGE_INVALID_PARAMETER = "Invalid parameter given. Valid parameters" +
                                                   " include name, start_date, start_time, end_date, end_time and tags";

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        if (args.contains(DELIMITER_PARAMETER)) {
            return parseStructuredArguments(args);
        } else {
            return parseNaturalLanguageArguments(args);
        }
    }

    /**
     * Parses the given {@code String} of arguments that is of a natural language format in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    private Command parseNaturalLanguageArguments(String args) {
        String argsInProcess = args;
        //Manual expression matching due to regex matching criteria being too broad
        Optional<Integer> index = ParserUtil.parseIndex(argsInProcess.trim().split(" ")[0]);
        if (index.isPresent()) {
            //Process index
            int indexOfIndex = argsInProcess.indexOf(new String("" +  index.get()));
            argsInProcess = argsInProcess.substring(indexOfIndex + new String("" +  index.get()).length());

            return processNaturalMultiParameterEdit(index, argsInProcess);

        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments that is of a structured format in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    private Command parseStructuredArguments(String args) {
        String[] preambleFields = args.split(DELIMITER_PARAMETER);
        Optional<Integer> index = ParserUtil.parseIndex(preambleFields[0]);

        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        for (int i = 1; i < preambleFields.length; i++) {
            try {
                String updateParameter = preambleFields[i].substring(0, preambleFields[i].replaceAll("^\\s+", "")
                                         .indexOf(DELIMITER_EDIT_PARAMETER) + 1).trim();
                String updateValue = preambleFields[i].substring(preambleFields[i].replaceAll("^\\s+", "")
                                     .indexOf(DELIMITER_EDIT_PARAMETER) + 1).trim();

                if (ParserUtil.isFoundIn(updateParameter, PARAMETER_NAME)) {
                    editTaskDescriptor.setName(Optional.of(new Name(updateValue)));
                } else if (ParserUtil.isFoundIn(updateParameter, PARAMETER_START_DATE)) {
                    editTaskDescriptor.setStartDate(Optional.of(updateValue));
                } else if (ParserUtil.isFoundIn(updateParameter, PARAMETER_END_DATE)) {
                    editTaskDescriptor.setEndDate(Optional.of(updateValue));
                } else if (ParserUtil.isFoundIn(updateParameter, PARAMETER_START_TIME)) {
                    editTaskDescriptor.setStartTime(Optional.of(updateValue));
                } else if (ParserUtil.isFoundIn(updateParameter, PARAMETER_END_TIME)) {
                    editTaskDescriptor.setEndTime(Optional.of(updateValue));
                } else if (ParserUtil.isFoundIn(updateParameter, PARAMETER_TAG)) {
                    editTaskDescriptor.setTags(parseTagsForEdit(Arrays.asList(updateValue
                                                                              .split(DELIMITER_EDIT_PARAMETER))));
                } else {
                    throw new IllegalValueException(MESSAGE_INVALID_PARAMETER);
                }
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Process the given {@code String} of arguments that contains only multiple parameters of interest
     * that is of a natural language format and returns an EditCommand object for execution.
     */
    private Command processNaturalMultiParameterEdit (Optional<Integer> index, String argsInProcess) {
        String[] argsInProcessElements = argsInProcess.trim().split(DELIMITER_EDIT_PARAMETER);
        ArrayList<String> updateParameterList = new ArrayList<String>();
        ArrayList<String> updateValueList = new ArrayList<String>();

        if (!argsInProcessElements[0].equals(KEYWORD_EDIT_PARAMETER)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }

        boolean processingParameter = false;
        String currentParameter = "";
        String currentValue = "";
        for (int i = 1; i < argsInProcessElements.length; i++) {
            if (ParserUtil.isValidParameter(argsInProcessElements[i]) && !processingParameter) {
                if (argsInProcessElements[i + 1] != null
                        && argsInProcessElements[i + 1].equals(KEYWORD_EDIT_PARAMETER_VALUE)) {
                    processingParameter = true;
                    currentParameter = argsInProcessElements[i];
                    i++;
                    continue;
                }
            }
            if (processingParameter) {
                if (argsInProcessElements[i].equals(KEYWORD_EDIT_MULTI_PARAMETER)
                        && (i + 1 < argsInProcessElements.length)
                        && ParserUtil.isValidParameter(argsInProcessElements[i + 1])
                        && (i + 2 < argsInProcessElements.length)
                        && argsInProcessElements[i + 2].equals(KEYWORD_EDIT_PARAMETER_VALUE)) {
                    processingParameter = false;
                    updateParameterList.add(currentParameter);
                    updateValueList.add(currentValue.trim());
                    currentValue = "";
                } else if (i + 1 == argsInProcessElements.length) {
                    currentValue += " " + argsInProcessElements[i];
                    processingParameter = false;
                    updateParameterList.add(currentParameter);
                    updateValueList.add(currentValue.trim());
                    currentValue = "";
                } else {
                    currentValue += " " + argsInProcessElements[i];
                }
            }
        }

        String structuredArgument = index.get() + DELIMITER_PARAMETER;
        for (int i = 0; i < updateParameterList.size(); i++) {
            structuredArgument += updateParameterList.get(i) + DELIMITER_EDIT_PARAMETER
                                + updateValueList.get(i) + DELIMITER_EDIT_PARAMETER + DELIMITER_PARAMETER;
        }
        return parseStructuredArguments(structuredArgument);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
