package sink;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMTPSinkConnector extends SinkConnector {

    private Map<String, String> smtpSinkConnectorConfigMap;

    @Override
    public void start(Map<String, String> map) {
        smtpSinkConnectorConfigMap = map;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return SMTPSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        Map<String, String> config = new HashMap<>(smtpSinkConnectorConfigMap);
        for (int i = 0; i < maxTasks; i++) {
            configs.add(config);
        }
        return configs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return SMTPSinkConnectorConfig.CONFIG_DEF;
    }

    @Override
    public String version() {
        return "0.01";
    }
}
