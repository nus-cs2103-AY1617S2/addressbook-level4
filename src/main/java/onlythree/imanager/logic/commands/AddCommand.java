package onlythree.imanager.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.StartEndDateTime;
import onlythree.imanager.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME [by DEADLINE] [from START_DATE_TIME to END_DATE_TIME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " meeting from tmr 9am to tmr 11am t/beta";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    //@@author A0140023E
    /**
     * Creates an AddCommand using raw values except {@code deadline} and {@code startEndDateTime}
     * that must be pre-initialized.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String nameArgs, Optional<Deadline> deadline,
            Optional<StartEndDateTime> startEndDateTime, Set<String> tags) throws IllegalValueException {
        toAdd = new Task(new Name(nameArgs), deadline, startEndDateTime, initTagList(tags));
    }

    /**
     * Returns initialized tags as a {@link UniqueTagList}
     * @throws IllegalValueException if there is a tag name that is invalid in the given tags set
     */
    private UniqueTagList initTagList(Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }


    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        model.addTask(toAdd);
        //@@author A0148052L
        model.pushCommand(COMMAND_WORD);
        model.pushStatus(model.getTaskList());
        //@@author
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
