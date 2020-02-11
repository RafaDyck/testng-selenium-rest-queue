package com.qarepo.api;

import com.rabbitmq.client.Channel;
import com.qarepo.messages.AMQPConfig;
import com.qarepo.messages.MessageSender;
import com.qarepo.models.Form;
import com.qarepo.services.FormService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/rabbit", produces = "application/json")
public class MessageController {
    private static final Logger LOGGER = LogManager.getLogger(MessageController.class);
    final String QUEUE_NAME = "banners_rpc_queue";

    @Autowired
    FormService formService;

    @PostMapping(path = "/queue")
    public Form executeBannerTestService(@RequestBody Form form) {
        LOGGER.info(" [*] Starting Execute Banner Test Service...");
        LOGGER.info(" [*] Saving banner to DB");
        formService.setForm(form);
        formService.saveFormToDB();
        form = formService.getFormFromDB(form.getId());
        Channel channel = AMQPConfig.getConnectionChannel();
        MessageSender publisher = new MessageSender();
        publisher.send(channel, "", QUEUE_NAME, form.toJson());
        //AMQPConfig.close(channel);
        return form;
    }
}
