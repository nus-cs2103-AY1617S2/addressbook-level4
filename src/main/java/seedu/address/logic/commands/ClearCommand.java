package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.controlsfx.control.PropertySheet.Mode;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.YTomorrow;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyPerson;
import seedu.address.model.task.UniquePersonList.DuplicatePersonException;

//@@author A0163848R
/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    
    public static final String KEYWORD_ALL = "all";
    public static final String KEYWORD_COMPLETE = Tag.TAG_COMPLETE;
    public static final String KEYWORD_PASSED = "passed";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all tasks, "
            + "or only completed tasts if the " + KEYWORD_COMPLETE + " keyword is specified.\n"
            + "Parameters: [keyword]\n"
            + "Keywords : "
            + KEYWORD_ALL
            +", "
            + KEYWORD_COMPLETE
            + ", "
            + KEYWORD_PASSED
            + "\n"
            + "Example: " + COMMAND_WORD + " completed";
    
    public static final String MESSAGE_SUCCESS_ALL = "All tasks have been cleared!";
    public static final String MESSAGE_SUCCESS_COMPLETE = "All competed tasks have been cleared!";
    public static final String MESSAGE_SUCCESS_PASSSED = "All passed tasks have been cleared!";
    public static final String MESSAGE_FAILURE = "Clear command not given any keywords!\n" + MESSAGE_USAGE;
    
    private Set<String> keywords;
    
    public ClearCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        if (keywords.contains(KEYWORD_ALL)) {
            
            model.resetData(new YTomorrow());
            
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
        
        if (keywords.contains(KEYWORD_COMPLETE)) {
            
            List<ReadOnlyPerson> filtered = new ArrayList<ReadOnlyPerson>();
            for (ReadOnlyPerson task : model.getAddressBook().getPersonList()) {
                try {
                    if (!task.getTags().contains(new Tag(Tag.TAG_COMPLETE))) {
                        filtered.add(task);
                    }
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
            }
            YTomorrow filteredYTomorrow = new YTomorrow();
            try {
                filteredYTomorrow.setPersons(filtered);
            } catch (DuplicatePersonException e) {
                e.printStackTrace();
            }
            model.resetData(filteredYTomorrow);
            
            return new CommandResult(MESSAGE_SUCCESS_COMPLETE);
        }
        
        if (keywords.contains(KEYWORD_PASSED)) {
            
            List<ReadOnlyPerson> filtered = new ArrayList<ReadOnlyPerson>();
            for (ReadOnlyPerson task : model.getAddressBook().getPersonList()) {
                if (!task.hasPassed()) {
                    filtered.add(task);
                }
            }
            YTomorrow filteredYTomorrow = new YTomorrow();
            try {
                filteredYTomorrow.setPersons(filtered);
            } catch (DuplicatePersonException e) {
                e.printStackTrace();
            }
            model.resetData(filteredYTomorrow);
            
            return new CommandResult(MESSAGE_SUCCESS_PASSSED);
        }
        
        return new CommandResult(MESSAGE_FAILURE);
    }
}
