//@@author A0148037E
package seedu.geekeep.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;

/**
 * Finds and lists all tasks in GeeKeep whose title contains any of the argument keywords. Keyword matching is case
 * sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks which titles contain any of "
            + "the specified keywords (case-sensitive)\n"
            + "and/or which date is within the specified time duration "
            + "and/or which tag list contains any of the specified tags.\n "
            + "Parameters: [KEYWORD...] [a/AFTER_DATETIME] [b/BEFORE_DATETIME] [t/TAGS...]\n"
            + "Example: " + COMMAND_WORD + " CS3230 Midterm a/04-04-17 0000 t/exam";
    public static final String MESSAGE_TIME_CONSTRAINTS = "The before date and time must be later than "
            + "the after date and time.";

    public static final String MESSAGE_SUCCESS = "\nGeeKeep is showing all the tasks which:\n";

    private final Set<String> keywords;
    private final UniqueTagList tags;
    private final DateTime earliestTime;
    private final DateTime latestTime;

    //Message with all the filter information added.
    private String datailedSuccessMsg = MESSAGE_SUCCESS;

    public FindCommand(Set<String> keywords, Optional<String> earliestTime,
            Optional<String> latestTime,
            Set<String> tags) throws IllegalValueException {
        this.keywords = keywords;

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.tags = new UniqueTagList(tagSet);

        if (earliestTime.isPresent()) {
            this.earliestTime = new DateTime(earliestTime.get());
        } else {
            this.earliestTime = DateTime.getMin();
        }

        if (latestTime.isPresent()) {
            this.latestTime = new DateTime(latestTime.get());
        } else {
            this.latestTime = DateTime.getMax();
        }

        if (this.earliestTime.compare(this.latestTime) > 0) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        addFilterMessage(keywords, earliestTime, latestTime, tags);
    }

    private void addFilterMessage(Set<String> keywords, Optional<String> earliestTime,
            Optional<String> latestTime,
            Set<String> tags) {
        if (!keywords.isEmpty()) {
            datailedSuccessMsg  += "Contains any of keywords in " + keywords + " in title\n";
        }

        if (earliestTime.isPresent()) {
            datailedSuccessMsg += "Happens after "
                              + earliestTime.get() + "\n";
        }

        if (latestTime.isPresent()) {
            datailedSuccessMsg += "Happens before "
                              + latestTime.get() + "\n";
        }

        if (!tags.isEmpty()) {
            datailedSuccessMsg += "Contains the tags " + tags + "\n";
        }
    }

    public String getDetailedSuccessMsg() {
        return this.datailedSuccessMsg;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, earliestTime, latestTime, tags);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size())
                                 + getDetailedSuccessMsg());
    }
}
