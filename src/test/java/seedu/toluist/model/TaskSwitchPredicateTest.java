//@@author A0131125Y
package seedu.toluist.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * Tests for TaskSwitchPredicate
 */
public class TaskSwitchPredicateTest {
    @Test
    public void todaySwitchPredicate() {
        assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime.now(), TaskSwitchPredicate.SWITCH_PREDICATE_TODAY, true);
        assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime.now().minusDays(1), TaskSwitchPredicate
                        .SWITCH_PREDICATE_TODAY,
                false);
        assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime.now().plusDays(1), TaskSwitchPredicate
                        .SWITCH_PREDICATE_TODAY,
                false);
    }

    @Test
    public void next7DaysSwitchPredicate() {
        assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime.now(),
                TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS, false);
        assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime.now().plusDays(7),
                TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS, true);
        assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime.now().plusDays(1),
                TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS, true);
        assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime.now().plusDays(8),
                TaskSwitchPredicate.SWITCH_PREDICATE_NEXT_7_DAYS, false);
    }


    /**
     * Helper method to check that a predicate works correctly
     * @param deadline a deadline to check
     * @param predicate a TaskSwitchPredicate instance
     * @param isSatisfied should the predicate be satisfied
     */
    private void assertTaskWithDeadlineSatisfiesPredicate(LocalDateTime deadline, TaskSwitchPredicate predicate,
                                                          boolean isSatisfied) {
        Task task = new Task("task", deadline);
        assertEquals(predicate.getPredicate().test(task), isSatisfied);
    }
}
