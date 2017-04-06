package project.taskcrusher.logic.commands;

import java.util.List;
import java.util.Optional;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [EVENT_NAME]"
            + " [d/START_DATE to END_DATE] [l/LOCATION] [//DESCRIPTION] [t/TAG]...\n" + "Example: " + COMMAND_WORD
            + " 1 l/new world //description";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the active list.";

    private final int filteredEventListIndex;
    private final EditEventDescriptor editEventDescriptor;
    public boolean force = false;

    /**
     * @param filteredEventListIndex
     *            the index of the event in the filtered event list to edit
     * @param editEventDescriptor
     *            details to edit the event with
     */
    public EditEventCommand(int filteredEventListIndex, EditEventDescriptor editEventDescriptor) {
        assert filteredEventListIndex > 0;
        assert editEventDescriptor != null;

        // converts filteredEventListIndex from one-based to zero-based.
        this.filteredEventListIndex = filteredEventListIndex - 1;

        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (filteredEventListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToEdit = lastShownList.get(filteredEventListIndex);
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        List<? extends ReadOnlyEvent> preexistingEvents = model.getUserInbox().getEventList();
        if (!force && editEventDescriptor.getTimeslots().isPresent()
                && editedEvent.hasOverlappingEvent(preexistingEvents)) { // allow
            // for
            // force
            // editing
            throw new CommandException(AddCommand.MESSAGE_EVENT_CLASHES);
        }

        try {
            model.updateEvent(filteredEventListIndex, editedEvent);
        } catch (UniqueEventList.DuplicateEventException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
        model.updateFilteredEventListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, eventToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of
     * {@code personToEdit} edited with {@code editPersonDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        // After these statements, each field should NOT be nullz
        Name updatedName = editEventDescriptor.getName().orElseGet(eventToEdit::getName);
        Location updatedLocation = editEventDescriptor.getLocation().orElseGet(eventToEdit::getLocation);
        Priority updatedPriority = editEventDescriptor.getPriority().orElseGet(eventToEdit::getPriority);
        List<Timeslot> updatedTimeslots = editEventDescriptor.getTimeslots().orElseGet(eventToEdit::getTimeslots);
        Description updatedDescription = editEventDescriptor.getDescription().orElseGet(eventToEdit::getDescription);
        UniqueTagList updatedTags = editEventDescriptor.getTags().orElseGet(eventToEdit::getTags);

        return new Event(updatedName, updatedTimeslots, updatedPriority, updatedLocation, updatedDescription,
                updatedTags);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value
     * will replace the corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<List<Timeslot>> timeslots = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditEventDescriptor() {
        }

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.name = toCopy.getName();
            this.priority = toCopy.getPriority();
            this.location = toCopy.getLocation();
            this.timeslots = toCopy.getTimeslots();
            this.description = toCopy.getDescription();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.location, this.priority, this.timeslots,
                    this.description, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
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

        public void setTimeslots(Optional<List<Timeslot>> timeslots) {
            assert timeslots != null;
            this.timeslots = timeslots;
        }

        public Optional<List<Timeslot>> getTimeslots() {
            return timeslots;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
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
