# A0143076J-unused
###### \AddCommand.java
``` java
//not used because re-implemented the add function to give flexible ordering of the task description
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK [by/ DATETIME] [from/ START_DATETIME] [to/ END_DATE_TIME] [#TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " read Lord of the rings by/ next thurs #personal";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    public enum TaskType { FLOAT, DEADLINE, EVENT, INVALID }
    private Task toAdd;

    /**
     * Creates an AddCommand using raw values
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, boolean hasDeadline, Optional<String> deadline, boolean hasStartDate,
            Optional<String> startDate, boolean hasEndDate, Optional<String> endDate, Set<String> tags)
        throws IllegalValueException {

        assert description != null;

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        TaskType taskType = checkTaskType(hasDeadline, hasStartDate, hasEndDate);

        switch (taskType) {
        case FLOAT:
            this.toAdd = new FloatingTask(new Description(description), new UniqueTagList(tagSet));
            break;
        case DEADLINE:
            this.toAdd = new DeadlineTask(new Description(description), new DateTime(deadline.get()),
                    new UniqueTagList(tagSet));
            break;
        case EVENT:
            this.toAdd = new EventTask(new Description(description), new DateTime(startDate.get()),
                    new DateTime(endDate.get()), new UniqueTagList(tagSet));
            break;
        case INVALID:
        default:
            throw new IllegalValueException("Too many/few DATETIME arguments!");
        }
    }

    /**
     * Checks the type of task(floating, deadline or event) to be added
     * based on the DATETIME parameters entered by the user.
     * @throws IllegalValueException if both deadline and startDate are entered,
     * or if only one of startDate or endDate is entered
     */
    private static TaskType checkTaskType(boolean hasDeadline, boolean hasStartDate, boolean hasEndDate)
            throws IllegalValueException {
        if (!hasDeadline && !hasStartDate && !hasEndDate) {
            return TaskType.FLOAT;
        }
        if (hasDeadline && !hasStartDate && !hasEndDate) {
            return TaskType.DEADLINE;
        }
        if (!hasDeadline && hasStartDate && hasEndDate) {
            return TaskType.EVENT;
        }
        return TaskType.INVALID;
    }


    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
```
###### \AddCommandParser.java
``` java
//not used because decided to try to instead implement flexible ordering of the task description. 
//Using this code makes it necessary for the task description to immediately follow the add command word.
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_DEADLINE).isPresent(), argsTokenizer.getValue(PREFIX_DEADLINE),
                    argsTokenizer.getValue(PREFIX_START_DATETIME).isPresent(), argsTokenizer.getValue(PREFIX_START_DATETIME),
                    argsTokenizer.getValue(PREFIX_END_DATETIME).isPresent(), argsTokenizer.getValue(PREFIX_END_DATETIME),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
```
###### \DeadlineTask.java
``` java
//not used because realized that it was not very useful to split the task into 3 classes based on tasktype.
//initially thought it would give better cohesion but after doing, realized only added a lot of repeated code.
/** Represents a task that has to be done by a specific deadline in the task manager.
 * * Guarantees: details are present and not null, field values are validated.
 */
public class DeadlineTask extends Task implements ReadOnlyTask {

    private DateTime deadline;

    public DeadlineTask(Description description, DateTime dateTime, UniqueTagList tags) {
        super(description, tags);
        this.deadline = dateTime;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }
    @Override
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
        this.setStatus(replacement.getStatus());
        //TODO the deadline in
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other)
                        && this.getDeadline().equals(((DeadlineTask) other).getDeadline()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, status, deadline, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" by: ").append(getDeadline().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
```
###### \EventTask.java
``` java
//not used because realized that it was not very useful to split the task into 3 classes based on tasktype.
//initially thought it would give better cohesion but after doing, realized only added a lot of repeated code.
/** Represents an event with a start and end time in the task manager.
 *  Guarantees: details are present and not null, field values are validated.
 */
public class EventTask extends Task implements ReadOnlyTask {

    private DateTime startDateTime;
    private DateTime endDateTime;
    public static final String MESSAGE_EVENT_TASK_CONSTRAINT = "End date/time must be later than start date/time!";
    /**
     * Every field must be present and not null. End time must be later than start time.
     * @throws IllegalValueException
    */
    public EventTask(Description description, DateTime startDate, DateTime endDate, UniqueTagList tags) throws IllegalValueException {
        super(description, tags);
        if (!endDate.isLater(startDate)) {
            throw new IllegalValueException(MESSAGE_EVENT_TASK_CONSTRAINT);
        }
        this.setStartDateTime(startDate);
        this.setEndDateTime(endDate);
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
        this.setStatus(replacement.getStatus());
        //TODO the datetimes in
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other)
                        && this.getStartDateTime().equals(((EventTask) other).getStartDateTime())
                        && this.getEndDateTime().equals(((EventTask) other).getEndDateTime()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, status, startDateTime, endDateTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" from: ").append(getStartDateTime().toString())
                .append(" to: ").append(getEndDateTime().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
```
