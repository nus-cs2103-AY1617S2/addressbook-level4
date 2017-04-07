package seedu.address.model.util;

import java.util.Random;

import seedu.address.commons.util.CollectionUtil;

//@@author A0163848R
public class TaskGroupGenerator {
    
    private static final String[] GROUPS = {
            "Vacation",
            "School",
            "Family",
            "Plans",
            "Hobbies",
            "ES",
    };
    
    public static String getGroup(Random r) {
        return CollectionUtil.getRandom(GROUPS, r);
    }
    
}
