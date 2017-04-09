package seedu.doist.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.doist.commons.events.config.AbsoluteStoragePathChangedEvent;
import seedu.doist.commons.events.model.AliasListMapChangedEvent;
import seedu.doist.commons.events.model.TodoListChangedEvent;
import seedu.doist.commons.events.storage.DataSavingExceptionEvent;
import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.model.ReadOnlyAliasListMap;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TodoListStorage, UserPrefsStorage, AliasListMapStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTodoListFilePath();

    @Override
    Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException;

    @Override
    void saveTodoList(ReadOnlyTodoList todoList) throws IOException;

    /**
     * Saves the current version of the To-do List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTodoListChangedEvent(TodoListChangedEvent tlce);

    //@@author A0140887W
    @Override
    String getAliasListMapFilePath();

    @Override
    Optional<ReadOnlyAliasListMap> readAliasListMap() throws DataConversionException, IOException;

    @Override
    void saveAliasListMap(ReadOnlyAliasListMap aliasListMap) throws IOException;

    /**
     * Saves the current version of the Aliaslist Map to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAliasListMapChangedEvent(AliasListMapChangedEvent almce);

    /**
     * Edits the storage path for todolistStorage, userPrefsStorage, aliasMapStorage
     */
    void handleAbsoluteStoragePathChangedEvent(AbsoluteStoragePathChangedEvent aspce);
}
