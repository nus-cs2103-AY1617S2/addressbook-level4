package seedu.address.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

//@@author A0140042A
/**
 * Edits a label in all tasks that exists in AddressBook
 */
public class EditLabelCommand extends Command {

    public static final String COMMAND_WORD = "editlabel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a label to another label \n"
            + "Existing label will be overwritten by the new label.\n" + "Parameters: LABEL_TO_EDIT NEW_LABEL"
            + "Example: " + COMMAND_WORD + " school schoolwork";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Labels changed from %1$s to %2$s";
    public static final String MESSAGE_LABEL_NOT_EXIST = "Specified label does not exist in any task saved";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_LABEL_INVALID = "Label name is invalid";

    private Label labelToChange;
    private Label newLabel;

    public EditLabelCommand(String labelToChange, String newLabel) throws IllegalValueException {
        this.labelToChange = new Label(labelToChange);
        this.newLabel = new Label(newLabel);
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredListToShowAll();
        List<ReadOnlyPerson> allTaskList = model.getFilteredPersonList();
        boolean labelExist = replaceLabelInTasks(allTaskList);

        if (!labelExist) {
            throw new CommandException(MESSAGE_LABEL_NOT_EXIST);
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, labelToChange, newLabel));
    }

    /**
     * Replaces a specific label in all tasks
     * @param allTaskList
     * @return true if the specified label exists
     */
    private boolean replaceLabelInTasks(List<ReadOnlyPerson> allTaskList) throws CommandException {
        boolean labelExist = false;
        for (int i = 0; i < allTaskList.size(); i++) {
            Person person = new Person(allTaskList.get(i));
            UniqueLabelList labels = person.getLabels();
            if (labels.contains(labelToChange)) {
                Set<Label> labelSet = labels.toSet();
                labelSet.remove(labelToChange);
                labelSet.add(newLabel);
                person.setLabels(new UniqueLabelList(labelSet));

                labelExist = true;

                try {
                    model.updatePerson(i, person);
                } catch (UniquePersonList.DuplicatePersonException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                }
            }
        }
        return labelExist;
    }

}
