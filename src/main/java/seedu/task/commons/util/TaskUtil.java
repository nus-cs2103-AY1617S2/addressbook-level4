package seedu.task.commons.util;

import java.util.Set;

import seedu.task.model.task.ReadOnlyTask;

//@@author A0142487Y
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
    }

    /**
     * Checks if the given task contains an exact full match of the searched keywords, in any of the fields
     *
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
     * Checks if the given task contains a full match of any of the given keywords. One successful full match in any of
     * the fields will return true.
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
//    public static boolean doesTaskContainPossibleDateAsString(){
//    //if one string is not valid date, then it is a keyword
//    // if one string is a valid date, then it might be a keyword or a date, or a part of a date
//        return true;
//    }
}
