package eu.appbucket.monitor.pojo;

import eu.appbucket.monitor.shared.pojo.Message;
import eu.appbucket.monitor.shared.pojo.MessageType;
import org.hsqldb.Trigger;

public class MessageBuilderImpl implements MessageBuilder {

    private MessageType type;
    private Object[] newRow;
    private Object[] oldRow;

    @Override
    public MessageBuilder withNewRow(Object[] newRow) {
        this.newRow = newRow;
        return this;
    }

    @Override
    public MessageBuilder withOldRow(Object[] oldRow) {
        this.oldRow = oldRow;
        return this;
    }

    @Override
    public MessageBuilder forTriggerType(int triggerType) {
        if(triggerType == Trigger.INSERT_AFTER_ROW) {
            type = MessageType.INSERT;
        } else if(triggerType == Trigger.UPDATE_BEFORE_ROW) {
            type = MessageType.UPDATED;
        } else if(triggerType == Trigger.DELETE_BEFORE_ROW) {
            type = MessageType.DELETE;
        }
        return this;
    }

    @Override
    public Message build() {
        int id = 0;
        if(type == MessageType.INSERT || type == MessageType.UPDATED) {
            id = (Integer) newRow[0];
        } else {
            id = (Integer) oldRow[0];
        }
        return new Message(id, type);
    }
}
