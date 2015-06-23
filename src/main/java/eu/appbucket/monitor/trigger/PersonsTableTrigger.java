package eu.appbucket.monitor.trigger;

import eu.appbucket.monitor.pojo.MessageBuilder;
import eu.appbucket.monitor.shared.pojo.Message;
import eu.appbucket.monitor.shared.queue.MessageHandler;
import eu.appbucket.monitor.shared.queue.MessageHandlerImpl;
import org.hsqldb.HsqlException;
import org.hsqldb.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: fix issue with the logging
public class PersonsTableTrigger implements Trigger {

    private MessageHandler handler;
    private static final Logger logger = LoggerFactory.getLogger(PersonsTableTrigger.class);

    public PersonsTableTrigger() {
        handler = newMessageHandler();
    }

    private MessageHandler newMessageHandler() {
        MessageHandler handler = null;
        try {
            handler = new MessageHandlerImpl();
        } catch (Exception e) {
            logger.error("Can't connect to the queue for operation:", e);
        }
        return handler;
    }

    @Override
    public void fire(int triggerType, String triggerName, String tableName, Object[] oldRow, Object[] newRow) throws HsqlException {
        Message currentDbOperationMessage =
                new MessageBuilder().withNewRow(newRow).withOldRow(oldRow).forTriggerType(triggerType).build();
        try {
            handler.sendMessageToQueue(currentDbOperationMessage);
            logger.info("Message sent to the queue: " + currentDbOperationMessage);
        } catch (Exception e) {
            logger.error("Can't send message to the queue for operation:" + currentDbOperationMessage, e);
        }
    }
}
