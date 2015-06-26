package eu.appbucket.monitor.pojo;

import eu.appbucket.monitor.shared.pojo.Message;

public interface MessageBuilder {

    MessageBuilder withNewRow(Object[] newRow);
    MessageBuilder withOldRow(Object[] oldRow);
    MessageBuilder forTriggerType(int triggerType);
    Message build();
}
