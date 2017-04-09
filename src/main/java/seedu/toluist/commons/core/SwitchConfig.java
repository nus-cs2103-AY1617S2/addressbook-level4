//@@author A0131125Y
package seedu.toluist.commons.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.TaskSwitchPredicate;

/**
 * Store the mapping for each tab & keyword
 */
public class SwitchConfig {
    private ArrayList<TaskSwitchPredicate> predicateArrayList = new ArrayList<>();
    private HashMap<String, TaskSwitchPredicate> keywordPredicateMapping = new HashMap<>();
    private static final String[] KEYWORDS_INCOMPLETE = new String[] { "incomplete", "1" };
    private static final String[] KEYWORDS_COMPLETE = new String[] { "completed", "4" };
    private static final String[] KEYWORDS_TODAY = new String[] { "today", "2" };
    private static final String[] KEYWORDS_NEXT_7_DAYS = new String[] { "next7days", "3" };
    private static final String[] KEYWORDS_ALL = new String[] { "all", "5" };

    public static SwitchConfig getDefaultSwitchConfig() {
        SwitchConfig switchConfig = new SwitchConfig();
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_INCOMPLETE, KEYWORDS_INCOMPLETE);
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_TODAY, KEYWORDS_TODAY);
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS, KEYWORDS_NEXT_7_DAYS);
        switchConfig.setPredicate(TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE, KEYWORDS_COMPLETE);
        switchConfig.setPredicate(TaskSwitchPredicate.SWITCH_PREDICATE_ALL, KEYWORDS_ALL);
        return switchConfig;
    }

    /**
     * Add predicate to config with matching keywords
     * The assumption is that keywords which are prefixes of each others won't be added
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
        Optional<String> matchingKey = keywordPredicateMapping.keySet().stream()
                .filter(key -> StringUtil.startsWithIgnoreCase(key, keyword))
                .findFirst();
        if (!matchingKey.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(keywordPredicateMapping.get(matchingKey.get()));
    }

    /**
     * Return lists of predicates
     */
    public List<TaskSwitchPredicate> getAllPredicates() {
        return predicateArrayList;
    }

    /**
     * Return set of keys
     */
    public Set<String> getAllKeys() {
        return keywordPredicateMapping.keySet();
    }
}
