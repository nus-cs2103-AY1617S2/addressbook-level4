package seedu.ezdo.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.util.SampleDataUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Task.class, SampleDataUtil.class, EzDo.class})
public class SampleDataUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getSampleTasks_ive_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        PowerMockito.whenNew(Task.class).withAnyArguments().thenThrow(new IllegalValueException("ive"));
        SampleDataUtil.getSampleTasks();
    }

    @Test
    public void getSampleEzDo_duplicateTask_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        PowerMockito.whenNew(EzDo.class).withAnyArguments().thenThrow(new DuplicateTaskException());
        SampleDataUtil.getSampleEzDo();
    }

}
