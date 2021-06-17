package model;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

@Data
public class SMTPTopicRecord {

    private String[] emails;
    private String message;

}
