package eu.appbucket.monitor.pojo;

import eu.appbucket.monitor.shared.pojo.Message;
import eu.appbucket.monitor.shared.pojo.MessageType;
import org.hsqldb.Trigger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageBuilderTest {

    private MessageBuilderImpl builder;

    @Before
    public void setup() {
        builder = new MessageBuilderImpl();
    }

    @Test
    public void buildMessageForInsertOperation() {
        Message expectedMessage = new Message(1, MessageType.INSERT);
        Message actualMessage = builder.forTriggerType(Trigger.INSERT_AFTER_ROW).withNewRow(new Object[]{1}).build();
        Assert.assertEquals(expectedMessage.getId(), actualMessage.getId());
        Assert.assertEquals(expectedMessage.getOperation(), actualMessage.getOperation());
    }

    @Test
    public void buildMessageForUpdateOperation() {
        Message expectedMessage = new Message(1, MessageType.UPDATED);
        Message actualMessage = builder.forTriggerType(Trigger.UPDATE_BEFORE_ROW).withNewRow(new Object[]{1}).build();
        Assert.assertEquals(expectedMessage.getId(), actualMessage.getId());
        Assert.assertEquals(expectedMessage.getOperation(), actualMessage.getOperation());
    }

    @Test
    public void buildMessageForDeleteOperation() {
        Message expectedMessage = new Message(1, MessageType.DELETE);
        Message actualMessage = builder.forTriggerType(Trigger.DELETE_BEFORE_ROW).withOldRow(new Object[]{1}).build();
        Assert.assertEquals(expectedMessage.getId(), actualMessage.getId());
        Assert.assertEquals(expectedMessage.getOperation(), actualMessage.getOperation());
    }
}
