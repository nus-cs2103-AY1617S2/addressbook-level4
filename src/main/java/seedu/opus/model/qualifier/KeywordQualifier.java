package seedu.opus.model.qualifier;

import java.util.Set;

import seedu.opus.model.task.ReadOnlyTask;

/**
 * Compares and filters the name, note and tag attributes of a task in the task manager
 */
public class KeywordQualifier implements Qualifier {

    private NameQualifier nameQualifier;
    private NoteQualifier noteQualifier;
    private TagQualifier tagQualifier;

    public KeywordQualifier(Set<String> nameKeyWords) {
        this.nameQualifier = new NameQualifier(nameKeyWords);
        this.noteQualifier = new NoteQualifier(nameKeyWords);
        this.tagQualifier = new TagQualifier(nameKeyWords);
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return nameQualifier.run(task) | noteQualifier.run(task) | tagQualifier.run(task);
    }

}
