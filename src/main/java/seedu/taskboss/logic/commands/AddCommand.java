package seedu.taskboss.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.exceptions.DefaultCategoryException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 * Adds a task to the TaskBoss.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD_SHORT = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Adds a task to TaskBoss. "
            + "Parameters: n/NAME [sd/START_DATE] [ed/END_DATE] "
            + "[i/INFORMATION] [c/CATEGORY]...\n"
            + "Example: " + COMMAND_WORD
            + " n/Submit report sd/today 5pm ed/next friday 11.59pm i/inform partner c/Work c/Project\n"
            + "Example: " + COMMAND_WORD_SHORT
            + " n/Watch movie sd/feb 19 c/Fun";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskBoss";
    public static final String ERROR_INVALID_DATES = "Your end date is earlier than start date.";
    public static final String DEFAULT = "AllTasks";
    public static final String ERROR_CANNOT_ADD_DONE_CATEGORY = "Cannot add Done category";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws InvalidDatesException
     * @throws DefaultCategoryException
     */
    public AddCommand(String name, String startDateTime, String endDateTime,
            String information, Set<String> categories) throws IllegalValueException, InvalidDatesException, DefaultCategoryException {
        final Set<Category> categorySet = new HashSet<>();

        //@@author A0144904H
        //default Category "All Tasks" assigned to all tasks automatically
        categorySet.add(new Category(DEFAULT));
        if (categories.contains("Done")) {
            throw new DefaultCategoryException(ERROR_CANNOT_ADD_DONE_CATEGORY);
        }

        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
        //@@author A0144904H
        String priorityLevel = PriorityLevel.PRIORITY_NO;
        DateTime startDateTimeObj = new DateTime(startDateTime);
        DateTime endDateTimeObj = new DateTime(endDateTime);

        if (startDateTimeObj.getDate() != null && endDateTimeObj.getDate() != null &&
                startDateTimeObj.getDate().after(endDateTimeObj.getDate())) {
            throw new InvalidDatesException(ERROR_INVALID_DATES);
        }

        //@@author A0144904H
        String filteredName;
        if (name.contains("!")) {
            filteredName = name.replaceAll("!", "");
            priorityLevel = PriorityLevel.PRIORITY_HIGH;
        } else {
            filteredName = name;
        }

        this.toAdd = new Task(
                new Name(filteredName),
                new PriorityLevel(priorityLevel),
                startDateTimeObj,
                endDateTimeObj,
                new Information(information),
                new UniqueCategoryList(categorySet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        assert model != null;
        try {
            model.addTask(toAdd);
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            int targetIndex = lastShownList.indexOf(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
