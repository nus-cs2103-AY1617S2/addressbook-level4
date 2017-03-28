//@@author A0131125Y
package seedu.toluist.commons.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import seedu.toluist.model.TaskSwitchPredicate;

/**
 * Store the mapping for each tab & keyword
 */
public class SwitchConfig {
    private ArrayList<TaskSwitchPredicate> predicateArrayList = new ArrayList<>();
    private HashMap<String, TaskSwitchPredicate> keywordPredicateMapping = new HashMap<>();

    public static SwitchConfig getDefaultSwitchConfig() {
        SwitchConfig switchConfig = new SwitchConfig();
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_INCOMPLETE, "i", "1");
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_TODAY, "t", "2");
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS, "n", "3");
        switchConfig.setPredicate(TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE, "c", "4");
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_ALL, "a", "5");
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
            keywordPredicateMapping.put(keyword.toLowerCase(), predicate);
        }
    }

    /**
     * Return matching predicate for a keyword
     * @param keyword
     * @return a present optional of the predicate if there is a match, Optional.empty() otherwise
     */
    public Optional<TaskSwitchPredicate> getPredicate(String keyword) {
        String normalizedKeyword = keyword.toLowerCase();
        if (!keywordPredicateMapping.containsKey(normalizedKeyword)) {
            return Optional.empty();
        }
        return Optional.of(keywordPredicateMapping.get(normalizedKeyword));
    }

    /**
     * Return lists of predicates
     */
    public List<TaskSwitchPredicate> getAllPredicates() {
        return predicateArrayList;
    }
}
