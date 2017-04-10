package typetask.storage;

import java.io.IOException;

import typetask.commons.core.Config;
import typetask.commons.util.JsonUtil;

//@@author A0140010M
public class ModifyConfigData {

    private String configPath = new String("config.json");
    private Config config;

    public ModifyConfigData(Config config) {
        this.config = config;
    }

    public void setTaskManagerFilePath(String newPath) throws IOException {
        config.setTaskManagerFilePath(newPath);
        JsonUtil.saveJsonFile(config, configPath);
    }

    public String getTaskManagerFilePath() {
        return config.getTaskManagerFilePath();
    }
}
