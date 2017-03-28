package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Information;
import seedu.task.model.task.PriorityLevel;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Task Manager. "
            + "Parameters: TASK_NAME [d/DEADLINE] [p/PRIORITY_LEVEL] [i/ANY_INFO] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + "Buy Milk d/17-Mar-2017 p/4 i/For home t/chore";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This Task already exists in the Task Manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String deadline, String priorityLevel, String info, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        //@@author A0139161J
        //NLP, Natty implementation
        if (!deadline.equals("")) {
            Parser parser = new Parser();
            List <DateGroup> groups = parser.parse(deadline);
            List dates = null;
            int line;
            int column;
            String matchingValue;
            String syntaxTree;
            Map parseMap;
            boolean isRecurring;
            Date recursUntil;

            for (DateGroup group: groups) {
                dates = group.getDates();
                line = group.getLine();
                column = group.getPosition();
                matchingValue = group.getText();
                syntaxTree = group.getSyntaxTree().toStringTree();
                parseMap = group.getParseLocations();
                isRecurring = group.isRecurring();
                recursUntil = group.getRecursUntil();
            }

            if (dates != null) {
                deadline = dates.get(0).toString();
            }
            StringTokenizer st = new StringTokenizer(deadline);
            List<String> listDeadline = new ArrayList<String>();
            while (st.hasMoreTokens()) {
                listDeadline.add(st.nextToken());
            }
            StringBuilder deadlineString = new StringBuilder();
            deadlineString.append(listDeadline.get(2) + "-" + listDeadline.get(1)
                + "-" + listDeadline.get(5)); // Extracting the dates.toString() format to DD-MMM-YYYY
            deadline = deadlineString.toString();
        }
        this.toAdd = new Task(
                new TaskName(taskName),
                new Deadline(deadline),
                new PriorityLevel(priorityLevel),
                new Information(info),
                new UniqueTagList(tagSet)
        );
        toAdd.setParserInfo("add");
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            GlobalStack gStack = GlobalStack.getInstance();
            gStack.getUndoStack().push(toAdd);
            /**Debugging purpose
             * gStack.printStack();
             */
            //@@author
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }
}
