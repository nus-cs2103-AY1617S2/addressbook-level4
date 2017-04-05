# A0139845R-reused
###### \java\seedu\watodo\ui\StatusBarFooter.java
``` java
    /**
     * Updates the file path shown at the bottom of the UI on saveas command.
     * @param sfpce new config file created when command executed
     */
    @Subscribe
    public void handleStorageFilePathChangedEvent(StorageFilePathChangedEvent sfpce) {
      String newPath = sfpce.newConfig.getWatodoFilePath();
      setSaveLocation("./" + newPath);
    }

```
