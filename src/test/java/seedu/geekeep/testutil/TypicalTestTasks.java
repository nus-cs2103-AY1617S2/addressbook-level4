package seedu.geekeep.testutil;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask dance, japan, exercise, spain, hackathon, fishing, song, meeting, spend;

    public TypicalTestTasks() {
        try {
            dance = new TaskBuilder().withTitle("Dance Camp")
                    .withLocation("123, Jurong West Ave 6, #08-111").withStartDateTime("01-04-17 1630")
                    .withEndDateTime("01-05-17 1630")
                    .withTags("friends").build();
            japan = new TaskBuilder().withTitle("Trip to Japan").withLocation("Japan")
                    .withStartDateTime("01-04-17 1630").withEndDateTime("01-05-17 1630")
                    .withTags("owesMoney", "friends").build();
            exercise = new TaskBuilder().withTitle("Physical Exercise").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("wall street").build();
            spain = new TaskBuilder().withTitle("Trip to Spain").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("Spain").build();
            hackathon = new TaskBuilder().withTitle("Hackathon").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("silicon valley").build();
            fishing = new TaskBuilder().withTitle("Go Fishing").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("little tokyo").build();
            song = new TaskBuilder().withTitle("Compose Songs").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("4th street").build();

            // Manually added
            meeting = new TaskBuilder().withTitle("Group Meeting").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("little india").build();
            spend = new TaskBuilder().withTitle("Spend 30 Days").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadGeeKeepWithSampleData(GeeKeep ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            } catch (IllegalValueException ive) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{dance, japan, exercise, spain, hackathon, fishing, song};
    }

    public GeeKeep getTypicalGeeKeep() {
        GeeKeep ab = new GeeKeep();
        loadGeeKeepWithSampleData(ab);
        return ab;
    }
}
