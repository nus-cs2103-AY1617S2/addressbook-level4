package seedu.todolist.logic.commands;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.Name;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.model.todo.UniqueTodoList;

/**
 * Adds a todo to the todo list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a todo to the todo list. "
            + "Parameters: TODO [t/TAG] \n"
            + "OR: TODO s/STARTTIME e/ENDTIME [t/TAG] \n"
            + "Example: " + COMMAND_WORD
            + " Take dog for walk s/1:00PM 11/11/17 e/2:00PM 11/11/17 t/todoal";

    public static final String MESSAGE_SUCCESS = "New todo added: %1$s";
    public static final String MESSAGE_ADD_RECUR_SUCCESS = "New todos added from %1$s\n to %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This todo already exists in the todo list";
    public static final String MESSAGE_INVALID_STARTTIME = "Invalid start time entered";
    public static final String MESSAGE_INVALID_ENDTIME = "Invalid end time entered";
    public static final String MESSAGE_INVALID_TIME = "Invalid time entered";
    public static final String MESSAGE_INVALID_RECURRING_DATE = "Invalid recurring date entered";

    private final Todo toAdd;
    private ArrayList<Todo> toAddRecur;

    //@@author A0163720M ,A0165043M-unused
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    /**
     * Creates an AddCommand using raw values to create a todo with start time and end time (event)
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo, Optional<String> startTime,
            Optional<String> endTime, Set<String> tags) throws IllegalValueException {
        try {
            // Parse through the set of tags
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }

            // Check for existence of each of the fields
            Name name = (todo != null) ? new Name(todo) : null;
            Date start;
            if (startTime.isPresent()) {
                start =  (!startTime.get().isEmpty()) ?
                        StringUtil.parseDate(startTime.get() , DATE_FORMAT) : getTodayMidnight();
            } else {
                start = null;
            }

            Date end;
            if (endTime.isPresent()) {
                end =  (!endTime.get().isEmpty()) ?
                        StringUtil.parseDate(endTime.get() , DATE_FORMAT) : getTomorrowMidnight();
            } else {
                end = null;
            }

            UniqueTagList tagList = new UniqueTagList(tagSet);

            // Todo(name, start_time, end_time, complete_time, taglist)
            this.toAdd = new Todo(name, start, end, null, tagList);
            this.toAddRecur = null;
        } catch (IllegalValueException e) {
            throw e;
        }
    }
    //@@author

    //@@author A0163720M, A0165043M-unused
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    /**
     * Creates an AddCommand using raw values to create a todo with just the end time (deadline)
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo, Optional<String> endTime, Set<String> tags) throws IllegalValueException {
        // Cannot throw an exception since there's only one line in the constructor
        // and the first line must be the call to the constructor, not try{}
        this(todo, Optional.empty(), endTime, tags);
    }

    public AddCommand(String todo, Date startTime, Date endTime, Set<String> tags, Date startDate,
            Date monthAndYear)
            throws IllegalValueException {
        this.toAdd = null;
        try {
            Date dateCounter = startDate;
            Date endMonthAndYear = addMonth(monthAndYear);
            this.toAddRecur = new ArrayList<Todo>();
            DateFormat dateFormat = new SimpleDateFormat("dd MM yy");
            if (dateCounter.after(endMonthAndYear)) {
                throw new IllegalValueException(AddCommand.MESSAGE_INVALID_RECURRING_DATE);
            }
            while (dateCounter.before(endMonthAndYear)) {
                Name name = (todo != null) ? new Name(todo + " " + dateFormat.format(dateCounter)) : null;
                Set<Tag> tagSet = new HashSet<>();
                for (String tagName : tags) {
                    tagSet.add(new Tag(tagName));
                }
                UniqueTagList tagList = new UniqueTagList(tagSet);
                this.toAddRecur.add(new Todo(name, combineDateTime(dateCounter, startTime),
                        combineDateTime(dateCounter, endTime), tagList));
                dateCounter = addWeek(dateCounter);
            }
        } catch (IllegalValueException e) {
            throw e;
        }  catch (ParseException e) {
            try {
                throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }
    //@@author

    //@@author A0163720M, A0165043M-unused
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    /**
     * Creates an AddCommand using raw values to create a floating task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo, Set<String> tags) throws IllegalValueException {
        // Cannot throw an exception since there's only one line in the constructor and the first line cannot be try{}
        this(todo, Optional.empty(), Optional.empty(), tags);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        if (toAdd == null) {
            try {
                for (int counter = 0; counter < toAddRecur.size(); counter++) {
                    model.addTodo(toAddRecur.get(counter));
                }
            } catch (UniqueTodoList.DuplicateTodoException e) {
                throw new CommandException(MESSAGE_DUPLICATE_TODO);
            }
            return new CommandResult(String.format(MESSAGE_ADD_RECUR_SUCCESS, toAddRecur.get(0),
                toAddRecur.get(toAddRecur.size() - 1))); //need more info.
        } else {
            try {
                model.addTodo(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            } catch (UniqueTodoList.DuplicateTodoException e) {
                throw new CommandException(MESSAGE_DUPLICATE_TODO);
            }
        }
    }
    //@@author
    public Date getTomorrowMidnight() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        DateFormat dateTimeFormat = new SimpleDateFormat("h:mma dd/MM/yy");
        try {
            dt = dateTimeFormat.parse("12:00am" + " " + dateFormat.format(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public Date getTodayMidnight() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        DateFormat dateTimeFormat = new SimpleDateFormat("h:mma dd/MM/yy");
        try {
            dt = dateTimeFormat.parse("12:00am" + " " + dateFormat.format(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
    private Date addMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);  // number of days to add
        return c.getTime();  // dt is now the new date
    }
    private Date addWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);  // number of days to add
        return c.getTime();  // dt is now the new date
    }
    //@@author A0165043M-unused
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    private Date combineDateTime(Date date, Date time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma");
        SimpleDateFormat returnFormat = new SimpleDateFormat("h:mma dd/MM/yy");
        try {
            return returnFormat.parse(timeFormat.format(time) + " " + dateFormat.format(date));
        } catch (ParseException e) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
        }
    }
    //@@author
}
