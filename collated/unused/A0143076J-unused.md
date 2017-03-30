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
###### \DateTimeParser.java
``` java
//meant to allow user to add events in the format from/friday 1pm to/ 4pm where the endDate is captured ass
//friday 4pm rather than current one of today 4pm, and thus throwing an exception warning that end date cannot
//be earlier than startDate. However, decided to do away with this as the format becomes rather messy and
//also code feels messier due to the way natty was implemented. ie. this addition enhancement not very value-adding.
/**
 * Parses out the startDate and endDate, and determines the corresponding
 * taskType of the given task
 */
public class DateTimeParser {

    public enum TaskType {
        FLOAT, DEADLINE, EVENT
    };

    private TaskType type;

    private String dateInText;
    private Date startDate;
    private Date endDate;

    public static final String MESSAGE_INVALID_NUM_DATETIME = "Too many/few dateTime arguments!";

    /**
     * Constructs a DateTimeParser object with both default start and end
     * date-times null
     */
    public DateTimeParser() {
        startDate = null;
        endDate = null;
    }

    /**
     * Determines if the combination of dateTime prefixes are valid, and if so,
     * extracts out the startDate and endDate if they exist
     */
    public void parse(String args) throws IllegalValueException {
        ArgumentTokenizer datesTokenizer = new ArgumentTokenizer(PREFIX_BY, PREFIX_ON, PREFIX_FROM);
        datesTokenizer.tokenize(args);

        String byPostfix = datesTokenizer.getUniqueValue(PREFIX_BY).orElse(null);
        String onPostfix = datesTokenizer.getUniqueValue(PREFIX_ON).orElse(null);
        String fromPostfix = datesTokenizer.getUniqueValue(PREFIX_FROM).orElse(null);
        checkIfPrefixFormatCorrect(byPostfix, onPostfix, fromPostfix);

        extractDates(byPostfix, onPostfix, fromPostfix);
        type = matchTaskType(byPostfix, onPostfix, fromPostfix);
        assert type != null;
    }

    /**
     * Returns the arg with the dateTime prefixes and dates removed
     */
    public String trimArgsOfDates(String arg) {

        if (type.equals(TaskType.DEADLINE)) {
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_BY.getPrefix(), dateInText), " ");
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), dateInText), " ");
        }
        if (type.equals(TaskType.EVENT)) {
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_FROM.getPrefix(), dateInText), " ");
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), dateInText), " ");
        }
        return arg.trim();
    }

    /**
     * Checks if the dateTime prefixes entered by the user can in the valid
     * format. There can only be 0 or exactly 1 of any of the prefixes
     *
     * @throws IllegalValueException if formats of the prefixes are not valid
     */
    private void checkIfPrefixFormatCorrect(String byPostfix, String onPostfix, String fromPostfix)
            throws IllegalValueException {

        boolean hasBy = byPostfix != null;
        boolean hasOn = onPostfix != null;
        boolean hasFrom = fromPostfix != null;

        if (!((!hasBy && !hasOn && !hasFrom) || (hasBy && !hasOn && !hasFrom) || (!hasBy && hasOn && !hasFrom)
                || (!hasBy && !hasOn && hasFrom))) {
            throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
        }
    }

    /**
     * Reads and validates the dates following the dateTime prefix and stores it
     * as startDate or endDate accordingly
     *
     * @throws IllegalValueException if the dates format are invalid, or if too
     *             many date/time are given
     */
    private void extractDates(String byPostfix, String onPostfix, String fromPostfix) throws IllegalValueException {

        List<String> postfixArgs = new ArrayList<String>();
        Collections.addAll(postfixArgs, byPostfix, onPostfix, fromPostfix);
        Parser parser = new Parser(); // refers to the Parser class in natty

        for (String arg : postfixArgs) {
            if (arg == null) {
                continue;
            }

            List<DateGroup> dateGroups = parser.parse(arg.trim());

            if (dateGroups.size() == 0 || dateGroups.get(0).getPosition() != 1) {
                throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
            }
            this.dateInText = dateGroups.get(0).getText().trim();

            List<Date> dates = dateGroups.get(0).getDates();

            if (dates.size() > 2) {
                throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
            }
            if (dates.size() == 1) {
                endDate = dates.get(0);
            }
            if (dates.size() == 2) {
                startDate = dates.get(0);
                endDate = dates.get(1);
            }
        }

    }

    /**
     * Determines whether the task added by the user is floating, deadline or
     * event, based on whether it has start and end date/times. Checks that the
     * correct prefix is used to input the corresponding task type.
     *
     * @throws IllegalValueException if the taskType does not match the prefix
     *             used
     */
    private TaskType matchTaskType(String byPostfix, String onPostfix, String fromPostfix)
            throws IllegalValueException {
        if (this.getStartDate() == null && this.getEndDate() == null) {
            assert byPostfix == null && onPostfix == null && fromPostfix == null;
            return TaskType.FLOAT;
        }
        if (this.getStartDate() == null && this.getEndDate() != null) {
            if (fromPostfix != null) { // Exception handling: can only add a deadline with by/ and on/ prefixes
                throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
            }
            return TaskType.DEADLINE;
        }
        if (this.getStartDate() != null && this.getEndDate() != null) {
            if (byPostfix != null) { // Exception handling: can only add an event with from/ or on/ prefixes
                throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
            }
            return TaskType.EVENT;
        }
        return null;
    }

    public TaskType getTaskType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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
