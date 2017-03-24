package seedu.address.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Name;
import seedu.address.model.todo.ReadOnlyTodo;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;

/**
 * Edits the details of an existing todo in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the todo identified "
            + "by the index number used in the last todo listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 Take Dog for a walk";

    public static final String MESSAGE_EDIT_TODO_SUCCESS = "Edited Todo: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TODO = "This todo already exists in the address book.";

    private final int filteredTodoListIndex;
    private final EditTodoDescriptor editTodoDescriptor;

    /**
     * @param filteredTodoListIndex the index of the todo in the filtered todo list to edit
     * @param editTodoDescriptor details to edit the todo with
     */
    public EditCommand(int filteredTodoListIndex, EditTodoDescriptor editTodoDescriptor) {
        assert filteredTodoListIndex > 0;
        assert editTodoDescriptor != null;

        // converts filteredTodoListIndex from one-based to zero-based.
        this.filteredTodoListIndex = filteredTodoListIndex - 1;

        this.editTodoDescriptor = new EditTodoDescriptor(editTodoDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTodo> lastShownList = model.getFilteredTodoList();

        if (filteredTodoListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        ReadOnlyTodo todoToEdit = lastShownList.get(filteredTodoListIndex);
        Todo editedTodo = createEditedTodo(todoToEdit, editTodoDescriptor);

        try {
            model.updateTodo(filteredTodoListIndex, editedTodo);
        } catch (UniqueTodoList.DuplicateTodoException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TODO_SUCCESS, todoToEdit));
    }

    /**
     * Creates and returns a {@code Todo} with the details of {@code todoToEdit}
     * edited with {@code editTodoDescriptor}.
     */
    private static Todo createEditedTodo(ReadOnlyTodo todoToEdit,
                                             EditTodoDescriptor editTodoDescriptor) {
        assert todoToEdit != null;

        Name updatedName = editTodoDescriptor.getName().orElseGet(todoToEdit::getName);
        UniqueTagList updatedTags = editTodoDescriptor.getTags().orElseGet(todoToEdit::getTags);

        if (editTodoDescriptor.getStartTime().isPresent() && editTodoDescriptor.getEndTime().isPresent()) {
            return new Todo(updatedName, editTodoDescriptor.getStartTime().get(),
                    editTodoDescriptor.getEndTime().get(), updatedTags);
        } else if (!editTodoDescriptor.getStartTime().isPresent() && editTodoDescriptor.getEndTime().isPresent()) {
            return new Todo(updatedName, editTodoDescriptor.getEndTime().get(), updatedTags);
        } else if (editTodoDescriptor.getStartTime().isPresent() && !editTodoDescriptor.getEndTime().isPresent()
                && editTodoDescriptor.getStartTime().get().equals("")) { // event to deadline
            return new Todo(updatedName, todoToEdit.getEndTime(), updatedTags);
        } else {
            return new Todo(updatedName, updatedTags);
        }
    }

    /**
     * Stores the details to edit the todo with. Each non-empty field value will replace the
     * corresponding field value of the todo.
     */
    public static class EditTodoDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Date> startTime = Optional.empty();
        private Optional<Date> endTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTodoDescriptor() {}

        public EditTodoDescriptor(EditTodoDescriptor toCopy) {
            this.name = toCopy.getName();
            this.tags = toCopy.getTags();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            if (!this.startTime.isPresent() && !this.endTime.isPresent()) {
                return CollectionUtil.isAnyPresent(this.name, this.tags);
            } else if (!this.startTime.isPresent() && this.endTime.isPresent()) {
                return CollectionUtil.isAnyPresent(this.name, this.endTime, this.tags);
            } else {
                return CollectionUtil.isAnyPresent(this.name, this.startTime, this.endTime, this.tags);
            }
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public Optional<Date> getStartTime() {
            if (this.startTime != null) {
                return this.startTime;
            } else {
                return null;
            }
        }
        public void setStartTime(Date startTime) {
            if (startTime == null) {
                this.startTime = Optional.empty();
            } else {
                this.startTime = Optional.of(startTime);
            }
        }

        public Optional<Date> getEndTime() {
            if (this.endTime != null) {
                return this.endTime;
            } else {
                return null;
            }
        }
        public void setEndTime(Date endTime) {
            if (endTime == null) {
                this.endTime = Optional.empty();
            } else {
                this.endTime = Optional.of(endTime);
            }
        }
    }
}
