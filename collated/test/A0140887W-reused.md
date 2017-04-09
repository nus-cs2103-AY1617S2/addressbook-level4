# A0140887W-reused
###### \java\seedu\doist\storage\StorageManagerTest.java
``` java
    @Test
    public void handleAliasListMapChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTodoListStorage("dummy"),
                                             new XmlAliasListMapStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleAliasListMapChangedEvent(new AliasListMapChangedEvent(new AliasListMap()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }

```
###### \java\seedu\doist\storage\StorageManagerTest.java
``` java
    /**
     * A Stub class to throw an exception when the save method for alias list map is called
     */
    class XmlAliasListMapStorageExceptionThrowingStub extends XmlAliasListMapStorage {

        public XmlAliasListMapStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAliasListMap(ReadOnlyAliasListMap todoList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
```
