package sink;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.errors.ConnectException;

import model.SMTPTopicRecord;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.kafka.connect.sink.SinkTaskContext;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import static sink.SMTPSinkConnectorConfig.*;

@Slf4j
public class SMTPSinkTask extends SinkTask {

    private SMTPSinkConnectorConfig config;

    private static final Gson gson = new Gson();

    @Override
    public String version() {
        return "0.01";
    }

    @Override
    public void start(Map<String, String> map) {

    }

    @Override
    public void initialize(SinkTaskContext context) {
        super.initialize(context);
        try {
            config = new SMTPSinkConnectorConfig(context.configs());
        } catch (ConfigException e) {
            throw new ConnectException("Couldn't start SMTP sink connector due to configuration error", e);
        }

    }

    @Override
    public void put(Collection<SinkRecord> collection) {
        for (SinkRecord s : collection) {
            log.info("MESSAGE   " + s.topic() + "    " + s.value());
        }
        collection.forEach(
                record -> send((String)(record.value()),
                        config.getString(SMTP_FROM),
                        config.getString(SMTP_PASS),
                        config.getString(SMTP_HOST)
                )
        );
    }

    @Override
    public void stop() {

    }

    private void send(String record, String from, String pass, String host) {

        SMTPTopicRecord message = gson.fromJson(record, SMTPTopicRecord.class);

        String[] to = message.getEmails();
        String body = message.getMessage();
        String subject = "";

        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage mimeMessage = new MimeMessage(session);

        try(Transport transport = session.getTransport("smtp")) {
            mimeMessage.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (InternetAddress address : toAddress) {
                mimeMessage.addRecipient(Message.RecipientType.TO, address);
            }

            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);
            transport.connect(host, from, pass);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        } catch (MessagingException ae) {
            ae.printStackTrace();
        }
    }
}

