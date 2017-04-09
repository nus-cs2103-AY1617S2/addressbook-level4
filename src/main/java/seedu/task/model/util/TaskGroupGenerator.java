package seedu.task.model.util;

import java.util.Random;

import seedu.task.commons.util.CollectionUtil;

//@@author A0163848R
/**
 * Random generator for Task Group strings
 */
public class TaskGroupGenerator {

    private static final String[] GROUPS = {
        "Vacation",
        "School",
        "Family",
        "Plans",
        "Hobbies",
        "ES",
    };

    /**
     * @param Random to use
     * @return Randomly-chosen group
     */
    public static String getGroup(Random r) {
        return CollectionUtil.getRandom(GROUPS, r);
    }

}
