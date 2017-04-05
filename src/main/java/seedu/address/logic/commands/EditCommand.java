package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Date;
import seedu.address.model.task.Email;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyPerson;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.UniquePersonList;

import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [s/STARTDATE] [d/END DATE] [e/EMAIL] [g/GROUP]...\n"
            + "Example: " + COMMAND_WORD + " 1 d/01.01 e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the todo list.";

    private final int filteredPersonListIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param filteredPersonListIndex
     *            the index of the person in the filtered person list to edit
     * @param editPersonDescriptor
     *            details to edit the person with
     */
    public EditCommand(int filteredPersonListIndex, EditPersonDescriptor editPersonDescriptor) {
        assert filteredPersonListIndex > 0;
        assert editPersonDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredPersonListIndex = filteredPersonListIndex - 1;

        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(filteredPersonListIndex);
        Task editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(filteredPersonListIndex, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of
     * {@code personToEdit} edited with {@code editPersonDescriptor}.
     */

    private static Task createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {

        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElseGet(personToEdit::getName);
        Date updatedDate = editPersonDescriptor.getDate().orElseGet(personToEdit::getDate);
        StartDate updatedStartDate = editPersonDescriptor.getStartDate().orElseGet(personToEdit::getStartDate);
        Email updatedEmail = editPersonDescriptor.getEmail().orElseGet(personToEdit::getEmail);
        Group updatedGroup = editPersonDescriptor.getGroup().orElseGet(personToEdit::getGroup);
        UniqueTagList updatedTags = personToEdit.getTags();


        return new Task(updatedName, updatedDate, updatedStartDate, updatedEmail, updatedGroup, updatedTags);

    }

    /**
     * Stores the details to edit the person with. Each non-empty field value
     * will replace the corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Date> date = Optional.empty();
        private Optional<StartDate> sdate = Optional.empty();
        private Optional<Email> email = Optional.empty();
        private Optional<Group> group = Optional.empty();
        //private Optional<UniqueTagList> tags = Optional.empty();

        public EditPersonDescriptor() {
        }

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.getName();
            this.date = toCopy.getDate();
            this.sdate = toCopy.getStartDate();
            this.email = toCopy.getEmail();
            this.group = toCopy.getGroup();
            //this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.date, this.email, this.group);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setDate(Optional<Date> date) {
            assert date != null;
            this.date = date;
        }

        public Optional<Date> getDate() {
            return date;
        }

        public void setStartDate(Optional<StartDate> sdate) {
            assert sdate != null;
            this.sdate = sdate;
        }

        public Optional<StartDate> getStartDate() {
            return sdate;
        }

        public void setEmail(Optional<Email> email) {
            assert email != null;
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return email;
        }

        public void setGroup(Optional<Group> group) {
            assert group != null;
            this.group = group;
        }

        public Optional<Group> getGroup() {
            return group;
        }
/*
        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
*/
    }
}
