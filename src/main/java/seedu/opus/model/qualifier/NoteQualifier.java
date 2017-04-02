package seedu.opus.model.qualifier;

import java.util.Set;

import seedu.opus.commons.util.StringUtil;
import seedu.opus.model.task.ReadOnlyTask;

/**
 * Compares and filters the note attribute of a task in the task manager
 */
public class NoteQualifier implements Qualifier {
    private Set<String> noteKeyWords;

    public NoteQualifier(Set<String> noteKeyWords) {
        this.noteKeyWords = noteKeyWords;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        String note = task.getNote().isPresent() ? task.getNote().get().value : "";
        return noteKeyWords.stream()
                .filter(keyword -> StringUtil.containsWordIgnoreCase(note, keyword))
                .findAny()
                .isPresent();
    }

}
