package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndDate;
import seedu.task.model.task.Group;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the todo list. "
            + "Format: text with the last 'from', 'to', or 'in'"
            + "defining the text after as the start date, end date, or group, respectively\n"
            + "The preceeding text is the task name\n"
            + "Cannot have a start date without an end date. \n"
            + "Example: " + COMMAND_WORD
            + " study english from today to tommorrow in Studying";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_PASSEDDATE = "The end date of the new task has passed!";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list";

    public static final String MESSAGE_NONAME = "No name given!\n";
    public static final String MESSAGE_NOGROUP = "No group given!\n";
    public static final String MESSAGE_ILLEGAL_TIME_PARAMS = "Cannot have a start date without an end date!\n";
    public static final String MESSAGE_ILLEGAL_TIME_INTERVAL = "End date is before the start date!\n";

    private static final String STARTDATE_KEYWORD = "from";
    private static final String ENDDATE_KEYWORD = "to";
    private static final String GROUP_KEYWORD = "in";

    private static List<String> SPECIAL_KEYWORDS;

    {
        SPECIAL_KEYWORDS = new ArrayList<String>();
        SPECIAL_KEYWORDS.add(STARTDATE_KEYWORD);
        SPECIAL_KEYWORDS.add(ENDDATE_KEYWORD);
        SPECIAL_KEYWORDS.add(GROUP_KEYWORD);
    }

    private Task toAdd = null;

    //@@author A0163848R
    /**
     * Constructs a an AddCommand
     * @param keywords composing the task to add.
     */
    public AddCommand(List<String> keywords) throws IllegalValueException {

        Optional<Name> name = getName(keywords);
        Optional<Group> group = getGroup(keywords);
        Optional<StartDate> start = getStartDate(keywords);
        Optional<EndDate> end = getEndDate(keywords);

        if (!name.isPresent()) {
            throw new IllegalValueException(MESSAGE_NONAME);
        }

        if (!group.isPresent()) {
            throw new IllegalValueException(MESSAGE_NOGROUP);
        }

        if (start.isPresent() && !end.isPresent()) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_TIME_PARAMS);
        }

        if (start.isPresent() && end.isPresent()
                && end.get().getTime().compareTo(start.get().getTime()) < 0) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_TIME_INTERVAL);
        }

        try {
            toAdd = Task.factory(
                    name.get(),
                    group.get(),
                    start.isPresent() ? start.get() : null,
                    end.isPresent() ? end.get() : null,
                    UniqueTagList.build(Tag.TAG_INCOMPLETE));
        } catch (IllegalValueException e) {
            //TODO error
        }
    }
    //@@author

    //@@author A0163848R
    /**
     * Extracts name from keywords
     * @param keywords to search
     * @return name if found
     */
    private static Optional<Name> getName(List<String> keywords) {
        String inner = StringUtil.extract(keywords, 0, SPECIAL_KEYWORDS);
        if (!inner.isEmpty()) {
            try {
                return Optional.of(new Name(inner));
            } catch (IllegalValueException e) {
            }
        }
        return Optional.empty();
    }
    //@@author

    //@@author A0163848R-reused
    /**
     * Extracts group from keywords
     * @param keywords to search
     * @return group if found
     */
    private static Optional<Group> getGroup(List<String> keywords) {
        if (keywords.contains(GROUP_KEYWORD)) {
            String inner = StringUtil.extract(keywords, keywords.lastIndexOf(GROUP_KEYWORD) + 1, SPECIAL_KEYWORDS);
            if (!inner.isEmpty()) {
                try {
                    return Optional.of(new Group(inner));
                } catch (IllegalValueException e) {
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Extracts start date from keywords
     * @param keywords to search
     * @return start date if found
     */
    private static Optional<StartDate> getStartDate(List<String> keywords) {
        if (keywords.contains(STARTDATE_KEYWORD)) {
            String inner = StringUtil.extract(keywords, keywords.lastIndexOf(STARTDATE_KEYWORD) + 1, SPECIAL_KEYWORDS);
            if (!inner.isEmpty()) {
                try {
                    return Optional.of(new StartDate(inner));
                } catch (IllegalValueException e) {
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Extracts end date from keywords
     * @param keywords to search
     * @return end date if found
     */
    private static Optional<EndDate> getEndDate(List<String> keywords) {
        if (keywords.contains(ENDDATE_KEYWORD)) {
            String inner = StringUtil.extract(keywords, keywords.lastIndexOf(ENDDATE_KEYWORD) + 1, SPECIAL_KEYWORDS);
            if (!inner.isEmpty()) {
                try {
                    return Optional.of(new EndDate(inner));
                } catch (IllegalValueException e) {
                }
            }
        }
        return Optional.empty();
    }
    //@@author

    //@@author A0164466X
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            String message = MESSAGE_SUCCESS + (toAdd.hasPassed() ? "\n" + MESSAGE_PASSEDDATE : "");
            return new CommandResult(String.format(message, toAdd));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }
}
