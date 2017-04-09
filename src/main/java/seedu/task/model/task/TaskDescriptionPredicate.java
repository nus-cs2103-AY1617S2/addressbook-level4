package seedu.task.model.task;

import java.util.function.Predicate;

public class TaskDescriptionPredicate implements Predicate<Object> {
    public static final String PREDICATE_WORD = "desc";
    // @@author A0163845X

    private String desc;

    public TaskDescriptionPredicate(String desc) {
        this.desc = desc;
    }

    public static boolean patternStringMatch(String p, String t) {
        int i = 0;
        int j = 0;
        while (i <= t.length() - p.length()) {
            if (p.substring(j, j + 1).equalsIgnoreCase(t.substring(i + j, i + j + 1))) {
                j++;
                if (j >= p.length()) {
                    return true;
                }
            } else {
                j = 0;
                i++;
            }
        }
        return false;
    }

    @Override
    public boolean test(Object t) {
        Task task = (Task) t;
        if (task.getTaskDescription().length() < desc.length()) {
            return false;
        }
        return patternStringMatch(desc, task.getTaskDescription());
    }

}
