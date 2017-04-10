package seedu.task.commons.util;

import java.util.Set;

import seedu.task.model.task.ReadOnlyTask;

//@@author A0142487Y
public class TaskUtil {

    /**
     * Returns true if the given {@code task} contains the searched {@code keyword} (case insensitive), in any field,
     * either a fullword or a substring match is found.
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
    }

    /**
     * Returns true if the given {@code task} contains the searched {@code keywords} (case insensitive), in any field,
     * all in fullword matches. Return false otherwise.
     * @param task
     * @param keywords
     * @return
     */
    public static boolean doesTaskContainExactKeywords(ReadOnlyTask task, Set<String> keywords) {
        assert task != null;
        assert keywords != null;
        return (StringUtil.containsExactWordsIgnoreCase(task.getName().fullName, keywords)
                || StringUtil.containsExactWordsIgnoreCase(task.getRemark().toString(), keywords)
                || StringUtil.containsExactWordsIgnoreCase(task.getLocation().toString(), keywords)
                || StringUtil.containsExactWordsIgnoreCase(task.getTags().toString(), keywords));
    }

    /**
     * Returns true if the given {@code task} contains the searched {@code keywords} (case insensitive), in any field,
     * any fullword match. Returns false otherwise.
     *
     * @param task
     * @param keywords
     * @return
     */
    public static boolean doesTaskContainExactKeyword(ReadOnlyTask task, Set<String> keywords) {
        assert task != null;
        assert keywords != null;
        for (String keyword : keywords) {
            if (StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)
                    || StringUtil.containsWordIgnoreCase(task.getLocation().toString(), keyword)
                    || StringUtil.containsWordIgnoreCase(task.getRemark().toString(), keyword)
                    || StringUtil.containsWordIgnoreCase(task.getTags().toString(), keyword)) {
                return true;
            }
        }
        return false;
    }
}
