package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Activity;
import seedu.address.model.person.Description;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.person.UniqueActivityList;
import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing activity in WhatsLeft.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the last activity listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[DESCRIPTION] [p/PRIORITY] [l/LOCATION ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/high e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in WhatsLeft.";

    private final int filteredActivityListIndex;
    private final EditActivityDescriptor editActivityDescriptor;

    /**
     * @param filteredActivityListIndex the index of the activity in the filtered activity list to edit
     * @param editActivityDescriptor details to edit the activity with
     */
    public EditCommand(int filteredActivityListIndex, EditActivityDescriptor editActivityDescriptor) {
        assert filteredActivityListIndex > 0;
        assert editActivityDescriptor != null;

        // converts filteredActivityListIndex from one-based to zero-based.
        this.filteredActivityListIndex = filteredActivityListIndex - 1;

        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyActivity> lastShownList = model.getFilteredActivityList();

        if (filteredActivityListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        ReadOnlyActivity activityToEdit = lastShownList.get(filteredActivityListIndex);
        Activity editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);

        try {
            model.updateActivity(filteredActivityListIndex, editedActivity);
        } catch (UniqueActivityList.DuplicateActivityException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, activityToEdit));
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static Activity createEditedActivity(ReadOnlyActivity activityToEdit,
                                             EditActivityDescriptor editActivityDescriptor) {
        assert activityToEdit != null;

        Description updatedDescription = editActivityDescriptor.getDescription().orElseGet(
            activityToEdit::getDescription);
        Priority updatedPriority = editActivityDescriptor.getPriority().orElseGet(activityToEdit::getPriority);
        Location updatedLocation = editActivityDescriptor.getLocation().orElseGet(activityToEdit::getLocation);
        UniqueTagList updatedTags = editActivityDescriptor.getTags().orElseGet(activityToEdit::getTags);

        return new Activity(updatedDescription, updatedPriority, updatedLocation, updatedTags);
    }

    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditActivityDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditActivityDescriptor() {}

        public EditActivityDescriptor(EditActivityDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.priority = toCopy.getPriority();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.priority, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
