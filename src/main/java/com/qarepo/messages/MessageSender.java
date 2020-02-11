package com.qarepo.messages;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class MessageSender {
    private static final Logger LOGGER = LogManager.getLogger(MessageSender.class);
    private StringWriter sw = new StringWriter();

    public MessageSender() {
    }

    public void send(Channel channel, String exchangeName, String queueName, String msg) {
        try {
            channel.basicPublish(exchangeName, queueName, new AMQP.BasicProperties().builder()
                                                                                    .contentType("application/json")
                                                                                    .priority(0)
                                                                                    .deliveryMode(2)
                                                                                    .build(),
                    msg.getBytes(StandardCharsets.UTF_8));
            LOGGER.info(" [*] Sending Message [" + msg + "]");
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
    }
}
