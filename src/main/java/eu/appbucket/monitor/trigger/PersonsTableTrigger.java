package eu.appbucket.monitor.trigger;

import eu.appbucket.monitor.pojo.MessageBuilder;
import eu.appbucket.monitor.pojo.MessageBuilderImpl;
import eu.appbucket.monitor.shared.pojo.Message;
import eu.appbucket.monitor.shared.queue.MessageHandler;
import eu.appbucket.monitor.shared.queue.MessageHandlerImpl;
import org.hsqldb.HsqlException;
import org.hsqldb.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonsTableTrigger implements Trigger {

    private MessageHandler handler;
    private static final Logger logger = LoggerFactory.getLogger(PersonsTableTrigger.class);

    public PersonsTableTrigger() {
        handler = newMessageHandler();
    }

    protected MessageHandler newMessageHandler() {
        MessageHandler handler = null;
        try {
            handler = new MessageHandlerImpl();
        } catch (Exception e) {
            logger.error("Can't connect to the queue.", e);
        }
        return handler;
    }

    @Override
    public void fire(int triggerType, String triggerName, String tableName, Object[] oldRow, Object[] newRow) throws HsqlException {
        MessageBuilder builder = newMessageBuilder();
        Message operationMessage = builder.withNewRow(newRow).withOldRow(oldRow).forTriggerType(triggerType).build();
        try {
            handler.sendMessageToQueue(operationMessage);
            logger.info("Message sent to the queue: " + operationMessage);
        } catch (Exception e) {
            logger.error("Can't send message to the queue for operation: " + operationMessage, e);
        }
    }

    protected MessageBuilder newMessageBuilder() {
        return new MessageBuilderImpl();
    }
}
