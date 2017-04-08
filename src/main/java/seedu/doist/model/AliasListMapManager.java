package seedu.doist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.doist.commons.core.ComponentManager;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.events.model.AliasListMapChangedEvent;
import seedu.doist.commons.util.CollectionUtil;

//@@author A0140887W
/**
 * Represents the in-memory model of the aliasListMap
 * All changes to any model should be synchronized.
 */
public class AliasListMapManager extends ComponentManager implements AliasListMapModel {
    private static final Logger logger = LogsCenter.getLogger(ConfigManager.class);
    private final AliasListMap aliasListMap;

    /**
     * Initializes a AliasListMapManager with the given aliasListMap object
     */
    public AliasListMapManager(ReadOnlyAliasListMap aliasListMap) {
        super();
        assert !CollectionUtil.isAnyNull(aliasListMap);

        logger.fine("Initializing AliasListMapManager with aliasListMap: " + aliasListMap);
        this.aliasListMap = new AliasListMap(aliasListMap);
    }

    public AliasListMapManager() {
        this(new AliasListMap());
    }

    @Override
    public ReadOnlyAliasListMap getAliasListMap() {
        return aliasListMap;
    }

    @Override
    public void setAlias(String alias, String commandWord) {
        aliasListMap.setAlias(alias, commandWord);
        indicateAliasListMapChanged();
    }

    @Override
    public void removeAlias(String alias) {
        aliasListMap.removeAlias(alias);
        indicateAliasListMapChanged();
    }

    /** Returns a list of aliases for the specified command word */
    @Override
    public List<String> getAliasList(String defaultCommandWord) {
        return aliasListMap.getAliasList(defaultCommandWord);
    }

    @Override
    public List<String> getValidCommandList(String defaultCommandWord) {
        List<String> list = new ArrayList<String>(aliasListMap.getAliasList(defaultCommandWord));
        list.add(defaultCommandWord);
        return list;
    }

    @Override
    public Set<String> getDefaultCommandWordSet() {
        return aliasListMap.getDefaultCommandWordSet();
    }

    @Override
    public void resetToDefaultCommandWords() {
        aliasListMap.setDefaultAliasListMapping();
    }

    /** Raises an event to indicate the alias list model has changed */
    private void indicateAliasListMapChanged() {
        raise(new AliasListMapChangedEvent(aliasListMap));
    }
}
