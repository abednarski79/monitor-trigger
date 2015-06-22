package eu.appbucket.monitor.trigger;

import eu.appbucket.monitor.pojo.MessageBuilder;
import eu.appbucket.monitor.shared.pojo.Message;
import eu.appbucket.monitor.shared.queue.MessageHandler;
import eu.appbucket.monitor.shared.queue.MessageHandlerImpl;
import org.hsqldb.HsqlException;
import org.hsqldb.Trigger;

public class PersonsTableTrigger implements Trigger {

    @Override
    public void fire(int triggerType, String triggerName, String tableName, Object[] oldRow, Object[] newRow) throws HsqlException {
        Message currentDbOperationMessage =
                new MessageBuilder().withNewRow(newRow).withOldRow(oldRow).forTriggerType(triggerType).build();
        MessageHandler handler = new MessageHandlerImpl();
        handler.sendMessageToQueue(currentDbOperationMessage);
    }
}
