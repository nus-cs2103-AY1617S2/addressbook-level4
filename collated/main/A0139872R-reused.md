# A0139872R-reused
###### \java\seedu\watodo\logic\commands\ListDeadlineCommand.java
``` java
/**
 * Lists all tasks with deadlines in the task manager to the user.
 */
public class ListDeadlineCommand extends ListCommand {

    public static final String ARGUMENT = "deadline";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with deadlines";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListDoneCommand.java
``` java
/**
 * Lists all tasks that are marked as completed in the task manager to the user.
 */
public class ListDoneCommand extends ListCommand {

    public static final String ARGUMENT = "done";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are marked as completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListEventCommand.java
``` java
/**
 * Lists all events in the task manager to the user.
 */
public class ListEventCommand extends ListCommand {

    public static final String ARGUMENT = "event";

    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListFloatCommand.java
``` java
/**
 * Lists all floating tasks in the task manager to the user.
 */
public class ListFloatCommand extends ListCommand {

    public static final String ARGUMENT = "float";

    public static final String MESSAGE_SUCCESS = "Listed all floating tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListTagCommand.java
``` java
/**
 * Lists all tasks with the entered tag in the task manager to the user.
 */
public class ListTagCommand extends ListCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with the entered tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "#TAG : Lists all tasks whose tags contain any of "
        + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " #budget";

    private final Set<String> keywords;

    public ListTagCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredByTagsTaskList(keywords);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListUndoneCommand.java
``` java
/**
 * Lists all tasks that are not yet completed in the task manager to the user.
 */
public class ListUndoneCommand extends ListCommand {

    public static final String ARGUMENT = "undone";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are not yet completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\parser\ListCommandParser.java
``` java
/**
 * Parses input arguments into various types of list commands for execution.
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns its respective ListCommand objects for execution.
     */
    public Command parse(String args) {
        if (args.contains("#")) {
            return new ListTagCommandParser().parse(args);
        }

        switch (args) {

        case ListAllCommand.ARGUMENT:
            return new ListAllCommand();

        case ListDeadlineCommand.ARGUMENT:
            return new ListDeadlineCommand();

        case ListDoneCommand.ARGUMENT:
            return new ListDoneCommand();

        case ListEventCommand.ARGUMENT:
            return new ListEventCommand();

        case ListFloatCommand.ARGUMENT:
            return new ListFloatCommand();

        case ListUndoneCommand.ARGUMENT:
            return new ListUndoneCommand();

        case ListCommand.ARGUMENT:
            return new ListCommand();

        default:
            return new ListDateCommandParser().parse(args);
        }
    }
}
```
###### \java\seedu\watodo\logic\parser\ListDateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ListDateCommand object
 */
public class ListDateCommandParser {

    private DateTimeParser dateTimeParser;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListDateCommand and returns a ListDateCommand object for execution.
     */
    public Command parse(String args) {
        try {
            dateTimeParser = new DateTimeParser();
            dateTimeParser.parse(args);
            return new ListDateCommand(dateTimeParser.getStartDate(), dateTimeParser.getEndDate());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
```
###### \java\seedu\watodo\model\ModelManager.java
``` java
    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;
        private String tags;

        TagQualifier(Set<String> tagKeyWords) {
            assert tagKeyWords != null;
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            tags = task
                .getTags()
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.joining(" "));
            String status = task.getStatus().toString();
            return tagKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(tags, keyword))
                    .findAny()
                    .isPresent() &&
                    status.equalsIgnoreCase(ListUndoneCommand.ARGUMENT);
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWords);
        }
    }

```
###### \java\seedu\watodo\model\util\SampleDataUtil.java
``` java
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Description("Undone floating task"),
                    new UniqueTagList("dance")),
                new Task(new Description("Undone floating task 2"),
                    new UniqueTagList("school", "homework")),
                new Task(new Description("Done floating task"),
                    new UniqueTagList("gamedesign"),
                    TaskStatus.DONE),
                new Task(new Description("Done floating task 2"),
                    new UniqueTagList("gamedesign"),
                    TaskStatus.DONE),
                new Task(new Description("Undone deadline task"),
                    new DateTime("16 march"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done deadline task"),
                    new DateTime("18 march"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone deadline task 2"),
                    new DateTime("20 march"),
                    new UniqueTagList("dance")),
                new Task(new Description("Done deadline task 2"),
                    new DateTime("22 march"),
                    new UniqueTagList("gamedesign"),
                    TaskStatus.DONE),
                new Task(new Description("Undone deadline task 3"),
                    new DateTime("24 march"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done deadline task 3"),
                    new DateTime("26 march"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone deadline task 4"),
                    new DateTime("28 march"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done deadline task 4"),
                    new DateTime("30 march"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone deadline task 5"),
                    new DateTime("1 april"),
                    new UniqueTagList("dance")),
                new Task(new Description("Done deadline task 5"),
                    new DateTime("3 april"),
                    new UniqueTagList("gamedesign"),
                    TaskStatus.DONE),
                new Task(new Description("Undone deadline task 6"),
                    new DateTime("5 april"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done deadline task 6"),
                    new DateTime("7 april"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone deadline task 7"),
                    new DateTime("9 april"),
                    new UniqueTagList("dance")),
                new Task(new Description("Done deadline task 7"),
                    new DateTime("11 april"),
                    new UniqueTagList("gamedesign"),
                    TaskStatus.DONE),

                new Task(new Description("Undone event"),
                    new DateTime("16 march"),
                    new DateTime("17 march"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done event"),
                    new DateTime("18 march"),
                    new DateTime("19 march"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone event 2"),
                    new DateTime("20 march"),
                    new DateTime("21 march"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done event 2"),
                    new DateTime("22 march"),
                    new DateTime("23 march"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone event 3"),
                    new DateTime("24 march"),
                    new DateTime("25 march"),
                    new UniqueTagList("dance")),
                new Task(new Description("Done event 3"),
                    new DateTime("26 march"),
                    new DateTime("27 march"),
                    new UniqueTagList("gamedesign"),
                    TaskStatus.DONE),
                new Task(new Description("Undone event 4"),
                    new DateTime("28 march"),
                    new DateTime("29 march"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done event 4"),
                    new DateTime("30 march"),
                    new DateTime("31 march"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone event 5"),
                    new DateTime("1 april"),
                    new DateTime("2 april"),
                    new UniqueTagList("dance")),
                new Task(new Description("Done event 5"),
                    new DateTime("3 april"),
                    new DateTime("4 april"),
                    new UniqueTagList("gamedesign"),
                    TaskStatus.DONE),
                new Task(new Description("Undone event 6"),
                    new DateTime("5 april"),
                    new DateTime("6 april"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done event 6"),
                    new DateTime("7 april"),
                    new DateTime("8 april"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("Undone event 7"),
                    new DateTime("9 april"),
                    new DateTime("10 april"),
                    new UniqueTagList("reading")),
                new Task(new Description("Done event 7"),
                    new DateTime("11 april"),
                    new DateTime("12 april"),
                    new UniqueTagList("school", "homework"),
                    TaskStatus.DONE),
                new Task(new Description("ST2334 Lecture"),
                    new DateTime("10am 27 march"),
                    new DateTime("12pm 27 march"),
                    new UniqueTagList("LT27"),
                    TaskStatus.DONE),
                new Task(new Description("CG2271 Lecture"),
                    new DateTime("4pm 27 march"),
                    new DateTime("6pm 27 march"),
                    new UniqueTagList("LT19"),
                    TaskStatus.DONE),
                new Task(new Description("EE2024 Lab"),
                    new DateTime("9am 28 march"),
                    new DateTime("12pm 28 march"),
                    new UniqueTagList("DigELab"),
                    TaskStatus.DONE),
                new Task(new Description("CG2271 Tutorial"),
                    new DateTime("12pm 28 march"),
                    new DateTime("1pm 28 march"),
                    new UniqueTagList("COM10217"),
                    TaskStatus.DONE),
                new Task(new Description("EE2024 Lecture"),
                    new DateTime("2pm 28 march"),
                    new DateTime("4pm 28 march"),
                    new UniqueTagList("LT6"),
                    TaskStatus.DONE),
                new Task(new Description("CG2023 Lecture"),
                    new DateTime("4pm 28 march"),
                    new DateTime("6pm 28 march"),
                    new UniqueTagList("E30601"),
                    TaskStatus.DONE),
                new Task(new Description("ST2334 Tutorial"),
                    new DateTime("10am 29 march"),
                    new DateTime("12pm 29 march"),
                    new UniqueTagList("S160437"),
                    TaskStatus.DONE),
                new Task(new Description("CG2271 Lab"),
                    new DateTime("12pm 29 march"),
                    new DateTime("2pm 29 march"),
                    new UniqueTagList("COM10114"),
                    TaskStatus.DONE),
                new Task(new Description("ST2334 Lecture"),
                    new DateTime("10am 30 march"),
                    new DateTime("12pm 30 march"),
                    new UniqueTagList("LT27"),
                    TaskStatus.DONE),
                new Task(new Description("CG2023 Lecture"),
                    new DateTime("2pm 30 march"),
                    new DateTime("4pm 30 march"),
                    new UniqueTagList("E30601"),
                    TaskStatus.DONE),
                new Task(new Description("CS2103 Tutorial"),
                    new DateTime("4pm 30 march"),
                    new DateTime("5pm 30 march"),
                    new UniqueTagList("COM1B103")),
                new Task(new Description("EE2024"),
                    new DateTime("5pm 30 march"),
                    new DateTime("6pm 30 march"),
                    new UniqueTagList("LT6")),
                new Task(new Description("CG2023 Lab"),
                    new DateTime("9am 31 march"),
                    new DateTime("12pm 31 march"),
                    new UniqueTagList("SignalLab")),
                new Task(new Description("EE2024 Tutorial"),
                    new DateTime("2pm 31 march"),
                    new DateTime("3pm 31 march"),
                    new UniqueTagList("E30612")),
                new Task(new Description("CS2103 Lecture"),
                    new DateTime("4pm 31 march"),
                    new DateTime("6pm 31 march"),
                    new UniqueTagList("i3Aud")),
                new Task(new Description("CS2103 Online Quiz"),
                    new DateTime("11pm 3 april"),
                    new UniqueTagList("school", "homework")),
                new Task(new Description("ST2334 Online Quiz"),
                    new DateTime("11pm 2 april"),
                    new UniqueTagList("school", "homework")),
                new Task(new Description("CG2023 Report"),
                    new DateTime("11pm 2 april"),
                    new UniqueTagList("school", "homework")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleWatodo() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
```
