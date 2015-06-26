package eu.appbucket.monitor.trigger;

import eu.appbucket.monitor.pojo.MessageBuilder;
import eu.appbucket.monitor.shared.pojo.Message;
import eu.appbucket.monitor.shared.queue.MessageHandler;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

public class PersonsTableTriggerTest {

    private PersonsTableTrigger trigger;
    private final Mockery context = new JUnit4Mockery();
    private MessageHandler messageHandlerMock;
    private MessageBuilder messageBuilderMock;

    @Before
    public void setup() {
        messageHandlerMock = context.mock(MessageHandler.class);
        messageBuilderMock = context.mock(MessageBuilder.class);
        trigger = new PersonsTableTrigger() {
            protected MessageBuilder newMessageBuilder() {
                return messageBuilderMock;
            }
            protected MessageHandler newMessageHandler() {
                return messageHandlerMock;
            }
        };
    }

    @Test
    public void testFire() {
        final Object[] objectArray = new Object[]{};
        final int triggerType = 0;
        final String triggerName = "TRIGGER_NAME";
        context.checking(new Expectations() {
            {
                exactly(1).of(messageBuilderMock).withNewRow(objectArray);
                will(returnValue(messageBuilderMock));
                exactly(1).of(messageBuilderMock).withOldRow(objectArray);
                will(returnValue(messageBuilderMock));
                exactly(1).of(messageBuilderMock).forTriggerType(triggerType);
                will(returnValue(messageBuilderMock));
                exactly(1).of(messageBuilderMock).build();
                exactly(1).of(messageHandlerMock).sendMessageToQueue(with(any(Message.class)));
            }
        });
        trigger.fire(triggerType, "triggerName", "tableName", objectArray, objectArray);
    }
}
