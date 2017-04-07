package seedu.doist.model;

import java.nio.file.Path;

//@@author A0140887W
/**
 * The API of the ConfigModel component.
 */
public interface ConfigModel {
    /** Change absolute path in config */
    void changeConfigAbsolutePath(Path path);
}
