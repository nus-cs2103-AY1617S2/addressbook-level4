package typetask.commons.util;


import java.util.Stack;


import typetask.commons.core.Config;



public class StorageUtil {

    private static Stack<Config> configHistory = new Stack<Config>();
    private static Stack<Config> redoConfigHistory = new Stack<Config>();
    private static Stack<OperationType> redoOperationHistory = new Stack<OperationType>();

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
