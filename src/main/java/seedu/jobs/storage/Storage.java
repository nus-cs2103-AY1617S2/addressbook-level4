package seedu.jobs.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.jobs.commons.events.model.TaskBookChangedEvent;
import seedu.jobs.commons.events.storage.DataSavingExceptionEvent;
import seedu.jobs.commons.exceptions.DataConversionException;
import seedu.jobs.model.LoginInfo;
import seedu.jobs.model.ReadOnlyTaskBook;
import seedu.jobs.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskBookStorage, UserPrefsStorage, LoginInfoStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    /**
     * Saves the current version of the Description Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBookChangedEvent(TaskBookChangedEvent abce);

    void saveLoginInfo(LoginInfo loginInfo) throws IOException;

    Optional<LoginInfo> readLoginInfo() throws DataConversionException, IOException;
}
