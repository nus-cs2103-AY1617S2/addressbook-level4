package seedu.opus.model.qualifier;

import java.util.Set;

import seedu.opus.commons.util.StringUtil;
import seedu.opus.model.task.ReadOnlyTask;

//@@author A0126345J
/**
 * Compares and filters the name attribute of a task in the task manager
 */
public class NameQualifier implements Qualifier {
    private Set<String> nameKeyWords;

    public NameQualifier(Set<String> nameKeyWords) {
        this.nameKeyWords = nameKeyWords;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return nameKeyWords.stream()
                .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                .findAny()
                .isPresent();
    }

}
//@@author
