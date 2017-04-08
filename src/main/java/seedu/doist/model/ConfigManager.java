package seedu.doist.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.doist.commons.core.ComponentManager;
import seedu.doist.commons.core.Config;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.events.config.AbsoluteStoragePathChangedEvent;
import seedu.doist.commons.util.CollectionUtil;
import seedu.doist.commons.util.ConfigUtil;
import seedu.doist.commons.util.StringUtil;

//@@author A0140887W
/**
 * Represents the in-memory model of the config data
 * All changes to any model should be synchronized.
 */
public class ConfigManager extends ComponentManager implements ConfigModel {
    private static final Logger logger = LogsCenter.getLogger(ConfigManager.class);
    private final Config config;

    /**
     * Initializes a ConfigManager with the given config object
     */
    public ConfigManager(Config config) {
        super();
        assert !CollectionUtil.isAnyNull(config);

        logger.fine("Initializing ConfigManager with config: " + config);
        this.config = config;
    }

    public ConfigManager() {
        this(new Config());
    }

  //========== change absolute storage path =================================================
    @Override
    public void changeConfigAbsolutePath(Path path) {
        config.setAbsoluteStoragePath(path.toString());
        try {
            ConfigUtil.saveConfig(config, ConfigUtil.getConfigPath());
            indicateAbsoluteStoragePathChanged();
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
    }

    /** Raises an event to indicate the absolute storage path has changed */
    private void indicateAbsoluteStoragePathChanged() {
        raise(new AbsoluteStoragePathChangedEvent(config.getAbsoluteTodoListFilePath(),
                config.getAbsoluteAliasListMapFilePath(), config.getAbsoluteUserPrefsFilePath()));
    }
}
