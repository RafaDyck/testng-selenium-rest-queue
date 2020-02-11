package com.qarepo;

import org.springframework.web.filter.CorsFilter;
import com.rabbitmq.client.Channel;
import com.qarepo.messages.AMQPConfig;
import com.qarepo.messages.MessageReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class App {
    static final String QUEUE_NAME = "banners_rpc_queue";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public MessageReceiver receiver() throws IOException, TimeoutException {
        Channel channel = AMQPConfig.connect();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        MessageReceiver receiver = new MessageReceiver();
        receiver.receive(channel, QUEUE_NAME);
        return receiver;
    }
}
