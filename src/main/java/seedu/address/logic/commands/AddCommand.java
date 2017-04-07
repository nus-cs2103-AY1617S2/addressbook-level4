package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the todo list. "
            + "Parameters: NAME [s/START DATE] [d/DEADLINE] g/GROUP \n "
            + "Start date and deadline are not necessary. \n"
            + "Cannot have a start date without an end date. \n"
            + "Example: " + COMMAND_WORD
            + " study english s/today d/tomorrow g/learning";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_PASSED = "The end date of the new task has passed!";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list";

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
    public AddCommand(List<String> keywords) {
        Optional<Name> name = getName(keywords);
        Optional<StartDate> start = getStartDate(keywords);
        Optional<EndDate> end = getEndDate(keywords);
        Optional<Group> group = getGroup(keywords);
        
        if (!name.isPresent()
                || !group.isPresent()
                || (start.isPresent() && !end.isPresent())) {
            //TODO syntax error
        }
        
        try {
            toAdd = Task.factory(
                    name.get(),
                    group.get(),
                    start.isPresent() ? start.get() : null,
                    end.isPresent() ? end.get() : null
                    );
        } catch (IllegalValueException e) {
            //TODO error
        }
        
    }
    
    private static Optional<Name> getName(List<String> keywords) {
        try {
            return Optional.of(new Name(concat(keywords.subList(
                    0,
                    lastIndexOfAny(keywords, SPECIAL_KEYWORDS)))));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    private static Optional<StartDate> getStartDate(List<String> keywords) {
        try {
            return Optional.of(new StartDate(concat(keywords.subList(
                    keywords.lastIndexOf(STARTDATE_KEYWORD) + 1,
                    lastIndexOfAny(keywords, except(SPECIAL_KEYWORDS, STARTDATE_KEYWORD))))));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
   
    private static Optional<EndDate> getEndDate(List<String> keywords) {
        try {
            return Optional.of(new EndDate(concat(keywords.subList(
                    keywords.lastIndexOf(ENDDATE_KEYWORD) + 1,
                    lastIndexOfAny(keywords, except(SPECIAL_KEYWORDS, ENDDATE_KEYWORD))))));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    private static Optional<Group> getGroup(List<String> keywords) {
        try {
            return Optional.of(new Group(concat(keywords.subList(
                    keywords.lastIndexOf(GROUP_KEYWORD) + 1,
                    lastIndexOfAny(keywords, except(SPECIAL_KEYWORDS, GROUP_KEYWORD))))));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    private static String concat(List<String> strs) {
        return concat(strs, " ");
    }
    
    private static String concat(List<String> strs, String between) {
        String cat = "";
        for (int i = 0; i < strs.size(); i++) {
            if (i == 0) {
                cat = strs.get(i);
                continue;
            }
            
            cat += between + strs.get(i);
        }
        return cat;
    }
    
    private static <T> int lastIndexOfAny(List<T> list, List<T> any) {
        int i = -1;
        for (T a : any) {
            int t = list.lastIndexOf(a);
            if (t < t) {
                i = t;
            }
        }
        return i;
    }
    
    private static <T> List<T> except(List<T> origin, T except) {
        List<T> filtered = new ArrayList<T>();
        for (T el : origin) {
            if (!el.equals(except)) {
                filtered.add(el);
            }
        }
        return filtered;
    }
    //@@author

    /**
     * Creates an AddCommand using raw values. This case is only have the
     * deadline
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    //@@author A0164032U
    /*
    public AddCommand(String name, String end, String group) throws IllegalValueException {
        this.toAdd = new DeadlineTask(
                new Name(name),
                new EndDate(end),
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }
    */

    /*
     * Constructor: floating task without starting date and end date
     */
    //@@author A0164032U
    /*
    public AddCommand(String name, String group) throws IllegalValueException {
        this.toAdd = new FloatingTask(
                new Name(name),
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }
    
    //@@author A0164032U
    public AddCommand(String name, String start, String end, String group) throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                new StartDate(start),
                new EndDate(end),
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }
    */
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addPerson(toAdd);
            
            String message = MESSAGE_SUCCESS + (toAdd.hasPassed() ? "\n" + MESSAGE_PASSED : "");
            return new CommandResult(String.format(message, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }
}
