package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Adds a task to the ToDoApp.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the ToDoApp. "
            + "Parameters: NAME [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES]...\n"
            + "Example: " + COMMAND_WORD
            + " Buy Printer";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp";

    private final Task toAdd;
 
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String... inputs)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        String deadline;
        int priority;
        String notes;
        boolean tagging = false;
        boolean noting = false;
        
        for (String input : inputs) {
            if(input.startsWith("d/")){
                deadline = input.substring(2);
            }else if(input.startsWith("p/")){
                priority = Integer.parseInt(input.substring(2));
            }else if(input.startsWith("t/")){
                noting = false;
                tagSet.add(new Tag(input.substring(2)));
                tagging = true;
            }else if(input.startsWith("n/")){
                tagging = false;
                notes.concat(input.substring(2) + " ");
                noting = true;
            }else{
                if(tagging == true) {
                    tagSet.add(input);
                }else if(noting == true) {
                    notes.concat(input + " ");
                }
            }
        }
        
        notes.trim();
        
        this.toAdd = new Task(
                new Name(name),
                new UniqueTagList(tagSet)
        );
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