package guitests;

import org.junit.Test;

/**
 * Dummy test. Lewis add tests here later
 */
public class SortCommandTest extends ToLuistGuiTest {
    @Test
    public void sort_byDefault() {
        commandBox.runCommand("sort by default");
    }

    @Test
    public void sort_byStartDate() {
        commandBox.runCommand("sort startdate");
    }

    @Test
    public void sort_enddate() {
        commandBox.runCommand("sort enddate");
    }

    @Test
    public void sort_priority() {
        commandBox.runCommand("sort priority");
    }

    @Test
    public void sort_description() {
        commandBox.runCommand("sort description");
    }

    @Test
    public void sort_multipleTimes() {}
}
