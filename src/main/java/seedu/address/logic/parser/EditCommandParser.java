package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Start;
import seedu.address.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    //@@author A0114395E
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START, PREFIX_DEADLINE, PREFIX_PRIORITY, PREFIX_TAG, PREFIX_NOTES);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
            // Check which prefixes are present
            boolean startPresent = args.contains(PREFIX_START.prefix);
            boolean deadlinePresent = args.contains(PREFIX_DEADLINE.prefix);
            boolean priorityPresent = args.contains(PREFIX_PRIORITY.prefix);
            boolean notesPresent = args.contains(PREFIX_NOTES.prefix);
            
            // We only set new values if user had input something.
            Optional<Name> nameStr = ParserUtil.parseName(preambleFields.get(1));
            if (nameStr.isPresent() && nameStr.get().fullName.length() > 0) editTaskDescriptor.setName(nameStr);
            else System.out.println("no name ");
            String startStr = argsTokenizer.getValue(PREFIX_START).orElse("");
            if (startStr.length() > 0) editTaskDescriptor.setStart(Optional.of(new Start(startStr)));
            else System.out.println("no start");
            String deadlineStr = argsTokenizer.getValue(PREFIX_DEADLINE).orElse("");
            if (deadlineStr.length() > 0) editTaskDescriptor.setDeadline(Optional.of(new Deadline(deadlineStr)));
            else System.out.println("no dead");
            int priorityInt = Integer.parseInt(argsTokenizer.getValue(PREFIX_PRIORITY).orElse("0"));
            if (priorityInt > 0) editTaskDescriptor.setPriority(Optional.of(new Priority(priorityInt)));
            else System.out.println("no p");
            String notesStr = argsTokenizer.getValue(PREFIX_NOTES).orElse("");
            if (notesStr.length() > 0) editTaskDescriptor.setNotes(Optional.of(new Notes(notesStr)));
            else System.out.println("no n");
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    //@@author A0114395E
    /*
     * Helper method to parse a get the inverse arguments of an Edit command
     * @param String, UnmodifiableObservableList<ReadOnlyTask>
     * @returns String of arguments for the inverse of the input edit command
     */
    public Command parseInverse(String arguments,
            UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
        argsTokenizer.tokenize(arguments);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);
        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (index.isPresent()) {
            ReadOnlyTask taskToEdit = lastShownList.get(index.get() - 1);
            final StringBuilder editBuilder = new StringBuilder();
            editBuilder.append(" ");
            editBuilder.append(index.get().toString());
            editBuilder.append(" ");
            editBuilder.append(ParserUtil.getTaskArgs(taskToEdit));
            return parse(editBuilder.toString());
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
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
