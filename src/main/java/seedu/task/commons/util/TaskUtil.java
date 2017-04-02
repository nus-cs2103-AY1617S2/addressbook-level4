package seedu.task.commons.util;

import seedu.task.model.task.ReadOnlyTask;

public class TaskUtil {

    /**
     * Checks if the given task contains the searched keyword (case insensitive), in any field, both a fullword and a
     * substring
     * 
     * @param task
     * @param keyword
     * @return
     */
    public static boolean doesTaskContainKeyword(ReadOnlyTask task, String keyword) {
        assert task != null;
        assert keyword != null;

        return (StringUtil.containsSubstringIgnoreCase(task.getName().fullName, keyword)
                || StringUtil.containsSubstringIgnoreCase(task.getRemark().toString(), keyword)
                || StringUtil.containsSubstringIgnoreCase(task.getLocation().toString(), keyword)
                || StringUtil.containsSubstringIgnoreCase(task.getTags().toString(), keyword));
        // (StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)
        // || StringUtil.containsWordIgnoreCase(task.getRemark().toString(), keyword)
        // || StringUtil.containsWordIgnoreCase(task.getLocation().toString(), keyword)
    }

}
