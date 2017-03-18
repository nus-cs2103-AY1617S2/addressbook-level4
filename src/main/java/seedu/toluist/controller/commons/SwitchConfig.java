package seedu.toluist.controller.commons;

import seedu.toluist.model.TaskSwitchPredicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Store the mapping for each tab & keyword
 */
public class SwitchConfig {
    private ArrayList<TaskSwitchPredicate> predicateArrayList = new ArrayList<>();
    private HashMap<String, TaskSwitchPredicate> keywordPredicateMapping = new HashMap<>();

    public static SwitchConfig getDefaultSwitchConfig() {
        SwitchConfig switchConfig = new SwitchConfig();
        switchConfig.setPredicate(TaskSwitchPredicate.INCOMPLETE_SWITCH_PREDICATE, "i");
        switchConfig.setPredicate(TaskSwitchPredicate.TODAY_SWITCH_PREDICATE, "t");
        switchConfig.setPredicate(TaskSwitchPredicate.WITHIN_7_DAYS_SWITCH_PREDICATE, "w");
        switchConfig.setPredicate(TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE, "c");
        switchConfig.setPredicate(TaskSwitchPredicate.ALL_SWITCH_PREDICATE, "a");
        return switchConfig;
    }

    /**
     * Add predicate to config with matching keywords
     * @param predicate the predicate
     * @param keywords varargs of keywords
     */
    public void setPredicate(TaskSwitchPredicate predicate, String... keywords) {
        if (!predicateArrayList.contains(predicate)) {
            predicateArrayList.add(predicate);
        }

        for (String keyword : keywords) {
            keywordPredicateMapping.put(keyword, predicate);
        }
    }

    /**
     * Return matching predicate for a keyword
     * @param keyword
     * @return a present optional of the predicate if there is a match, Optional.empty() otherwise
     */
    public Optional<TaskSwitchPredicate> getPredicate(String keyword) {
        if (!keywordPredicateMapping.containsKey(keyword)) {
            return Optional.empty();
        }
        return Optional.of(keywordPredicateMapping.get(keyword));
    }
}
