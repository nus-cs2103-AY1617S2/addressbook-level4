package typetask.storage;

import java.io.IOException;

import typetask.commons.core.Config;
import typetask.commons.util.JsonUtil;

public class ModifyConfigData {

    private String configPath = new String("config.json");
    private Config config;

//    public ModifyConfigData(String configPathName) throws DataConversionException {
//        Optional<Config> importConfig = JsonUtil.readJsonFile(configPathName, Config.class);
//        config = importConfig.orElse(new Config());
//    }
//
//    public ModifyConfigData() throws DataConversionException {
//        Optional<Config> importConfig = JsonUtil.readJsonFile(configPath, Config.class);
//        config = importConfig.orElse(new Config());
//    }
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
