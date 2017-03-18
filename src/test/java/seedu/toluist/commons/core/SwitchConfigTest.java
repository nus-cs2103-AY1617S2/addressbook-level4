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
            TaskSwitchPredicate.INCOMPLETE_SWITCH_PREDICATE,
            TaskSwitchPredicate.TODAY_SWITCH_PREDICATE,
            TaskSwitchPredicate.WITHIN_7_DAYS_SWITCH_PREDICATE,
            TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE,
            TaskSwitchPredicate.ALL_SWITCH_PREDICATE
        ));
        assertEquals(expectedPredicates, switchConfig.getAllPredicates());

        assertEquals(switchConfig.getPredicate("1"),
                Optional.of(TaskSwitchPredicate.INCOMPLETE_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("2"),
                Optional.of(TaskSwitchPredicate.TODAY_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("3"),
                Optional.of(TaskSwitchPredicate.WITHIN_7_DAYS_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("4"),
                Optional.of(TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("5"),
                Optional.of(TaskSwitchPredicate.ALL_SWITCH_PREDICATE));

        assertEquals(switchConfig.getPredicate("i"),
                Optional.of(TaskSwitchPredicate.INCOMPLETE_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("T"),
                Optional.of(TaskSwitchPredicate.TODAY_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("W"),
                Optional.of(TaskSwitchPredicate.WITHIN_7_DAYS_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("C"),
                Optional.of(TaskSwitchPredicate.COMPLETED_SWITCH_PREDICATE));
        assertEquals(switchConfig.getPredicate("a"),
                Optional.of(TaskSwitchPredicate.ALL_SWITCH_PREDICATE));

        assertEquals(switchConfig.getPredicate("a"),
                Optional.of(TaskSwitchPredicate.ALL_SWITCH_PREDICATE));

        assertEquals(switchConfig.getPredicate("gibberish"), Optional.empty());
    }
}
