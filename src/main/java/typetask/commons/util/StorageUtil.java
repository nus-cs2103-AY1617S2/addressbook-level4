package typetask.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.Stack;


import typetask.commons.core.Config;



public class StorageUtil {

    private static Stack<Config> configHistory = new Stack<Config>();
    private static Stack<Config> redoConfigHistory = new Stack<Config>();
    private static Stack<OperationType> operationHistory = new Stack<OperationType>();
    private static Stack<OperationType> redoOperationHistory = new Stack<OperationType>();
    
    private static final int INDEX_DIRECTORY = 0;
    private static final int INDEX_FILE_NAME = 1;
    private static final int FILE_PATH_ARRAY_LENGTH = 2;

    public enum OperationType {
        CHANGE_DIRECTORY, OPEN_FILE
    }

    public static void storeConfig(OperationType storageOperation) {
            redoConfigHistory.clear();
            redoOperationHistory.clear();
    }

    //Clears redoConfigHistory, whenever a command, besides undo, is entered
    public static void undoConfig() {
        configHistory.pop();
    }
    //Clears redoConfigHistory, whenever a command, besides undo, is entered
    public static void clearRedoConfig() {
        redoConfigHistory.clear();
    }
    public static boolean isRedoConfigHistoryEmpty() {
        return redoConfigHistory.isEmpty();
    }
    
    //Returns true if configHistory is empty
    public static boolean isConfigHistoryEmpty() {
        return configHistory.isEmpty();
    }
    
}
