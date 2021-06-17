package sink;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class SMTPSinkConnectorConfig extends AbstractConfig {

    public static final String SMTP_FROM = "smtpclient.from";
    public static final String SMTP_PASS = "smtpclient.pass";
    public static final String SMTP_HOST = "smtpclient.host";

    public static ConfigDef baseConfigDef() {
        return new ConfigDef()
                .define( SMTP_FROM, ConfigDef.Type.STRING,
                        "", ConfigDef.Importance.HIGH,
                        "sender email address")
                .define(SMTP_PASS, ConfigDef.Type.STRING,
                        "", ConfigDef.Importance.HIGH,
                        "sender password")
                .define(SMTP_HOST, ConfigDef.Type.STRING,
                        "", ConfigDef.Importance.HIGH,
                        "smtp server address");

    }

    public static final ConfigDef CONFIG_DEF = baseConfigDef();

    public SMTPSinkConnectorConfig(Map<String, String> props) {
        super(CONFIG_DEF, props);
    }
}
