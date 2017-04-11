package seedu.opus.commons.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import seedu.opus.commons.exceptions.FileDeletionException;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    //@@author A0148081H
    public static void deleteFile(String filePath) throws FileDeletionException {
        assert StringUtil.isValidPathToFile(filePath);

        File file = new File(filePath);
        if (!file.delete()) {
            throw new FileDeletionException("Unable to delete file at: " + filePath);
        }
    }

    /**
     * Creates and deletes an empty file at the path.
     * @param path must be a valid file path
     * @return true if the path is exists and user has sufficient privileges.
     */
    public static boolean isPathAvailable(String path) {

        File file = new File(path);
        boolean exists = file.exists();

        try {
            createParentDirsOfFile(file);
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }

        if (!exists) { // prevent deleting an existing file
            file.delete();
        }
        return true;
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return fileExists(file);
    }
    //@@author

    public static boolean fileExists(File file) {
        return file.exists() && file.isFile();
    }

    public static void createIfMissing(File file) throws IOException {
        if (!fileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir the directory to be created; assumed not null
     * @throws IOException if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    /**
     * Converts a string to a platform-specific file path
     * @param pathWithForwardSlash A String representing a file path but using '/' as the separator
     * @return {@code pathWithForwardSlash} but '/' replaced with {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        assert pathWithForwardSlash != null;
        assert pathWithForwardSlash.contains("/");
        return pathWithForwardSlash.replace("/", File.separator);
    }

}
