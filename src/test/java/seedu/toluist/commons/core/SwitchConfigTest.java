//@@author A0131125Y
package seedu.toluist.commons.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import seedu.toluist.model.TaskSwitchPredicate;

/**
 * Tests for SwitchConfig
 */
public class SwitchConfigTest {
    @Test
    public void testDefaultSwitchConfig() {
        SwitchConfig switchConfig = SwitchConfig.getDefaultSwitchConfig();

        ArrayList<TaskSwitchPredicate> expectedPredicates = new ArrayList<>(Arrays.asList(
            TaskSwitchPredicate.SWITCH_PREDICATE_INCOMPLETE,
            TaskSwitchPredicate.SWITCH_PREDICATE_TODAY,
            TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS,
            TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE,
            TaskSwitchPredicate.SWITCH_PREDICATE_ALL
        ));
        assertEquals(expectedPredicates, switchConfig.getAllPredicates());

        assertEquals(switchConfig.getPredicate("1"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_INCOMPLETE));
        assertEquals(switchConfig.getPredicate("2"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_TODAY));
        assertEquals(switchConfig.getPredicate("3"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS));
        assertEquals(switchConfig.getPredicate("4"),
                Optional.of(TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("5"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_ALL));

        assertEquals(switchConfig.getPredicate("i"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_INCOMPLETE));
        assertEquals(switchConfig.getPredicate("T"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_TODAY));
        assertEquals(switchConfig.getPredicate("N"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS));
        assertEquals(switchConfig.getPredicate("C"),
                Optional.of(TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("a"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_ALL));

        assertEquals(switchConfig.getPredicate("al"),
                Optional.of(TaskSwitchPredicate.SWITCH_PREDICATE_ALL));

        assertEquals(switchConfig.getPredicate("gibberish"), Optional.empty());
    }
}
