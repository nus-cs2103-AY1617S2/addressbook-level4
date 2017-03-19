package seedu.ezdo.model.task;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.commons.util.DateUtil;
import seedu.ezdo.model.ModelManager;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.testutil.TaskBuilder;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateUtil.class)
public class ModelManagerTest {

    private ModelManager modelManager;

    @Before
    public void setUp() {
        modelManager = new ModelManager();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkDate_parseFail() throws Exception {
        ReadOnlyTask task = new TaskBuilder().build();
        PowerMockito.mockStatic(DateUtil.class);
        BDDMockito.given(DateUtil.isTaskDateValid(task))
                .willThrow(new ParseException("parse fail", 0));
        thrown.expect(DateException.class);
        modelManager.checkTaskDate(task);
    }

}
