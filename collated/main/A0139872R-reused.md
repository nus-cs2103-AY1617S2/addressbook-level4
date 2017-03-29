# A0139872R-reused
###### \java\seedu\watodo\logic\commands\ListCommand.java
``` java
/**
 * Lists all overdue tasks and upcoming tasks due the next day in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all overdue tasks and tasks due tomorrow";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists tasks that fit the specified keyword as a filter to the user. "
            + "Parameters: KEYWORD\n" + "Example: " + COMMAND_WORD + " all";

    public static final int DEADLINE = 1;

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByDatesTaskList(DEADLINE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListDayCommand.java
``` java
/**
 * Lists all tasks scheduled on the current day in the task manager to the user.
 */
public class ListDayCommand extends ListCommand {

    public static final String COMMAND_WORD = "day";

    public static final String MESSAGE_SUCCESS = "Listed today's tasks";

    public static final int DEADLINE = 0;

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByDatesTaskList(DEADLINE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListDeadlineCommand.java
``` java
/**
 * Lists all tasks with deadlines in the task manager to the user.
 */
public class ListDeadlineCommand extends ListCommand {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with deadlines";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
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

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are marked as completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
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

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
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

    public static final String COMMAND_WORD = "float";

    public static final String MESSAGE_SUCCESS = "Listed all floating tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListMonthCommand.java
``` java
/**
 * Lists all tasks scheduled on the current month in the task manager to the user.
 */
public class ListMonthCommand extends ListCommand {

    public static final String COMMAND_WORD = "month";

    public static final String MESSAGE_SUCCESS = "Listed this month's tasks";

    public static final int DEADLINE = 1;

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByMonthsTaskList(DEADLINE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
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

    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are not yet completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\watodo\logic\commands\ListWeekCommand.java
``` java
/**
 * Lists all tasks scheduled on the current week in the task manager to the user.
 */
public class ListWeekCommand extends ListCommand {

    public static final String COMMAND_WORD = "week";

    public static final String MESSAGE_SUCCESS = "Listed this week's tasks";

    public static final int DEADLINE = 7;

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByDatesTaskList(DEADLINE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
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
            return tagKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(tags, keyword))
                    .findAny()
                    .isPresent() &&
                    task.getStatus().toString().equalsIgnoreCase(ListUndoneCommand.COMMAND_WORD);
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWords);
        }
    }

```
