package com.qarepo.messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.qarepo.services.MessageService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Component
public class MessageReceiver {
    private static final Logger LOGGER = LogManager.getLogger(MessageReceiver.class);
    private StringWriter sw = new StringWriter();

    @Autowired
    private MessageService messageService;

    public void receive(Channel channel, String queueName) {
        try {
            channel.basicQos(1);
            LOGGER.info(" [*] Waiting for messages...");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                LOGGER.info(" [x] Received [" + message + "]");
                String response = "";
                try {
                    if (message != null && !message.isEmpty()) {
                        messageService.setMessage(message);
                        messageService.processMessage();
                        LOGGER.info(" [*] RESPONSE=" + message);
                    }
                } finally {
                    LOGGER.info(" [*] Done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
    }
}
