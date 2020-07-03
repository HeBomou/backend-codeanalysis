package local.happysixplus.backendcodeanalysis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import local.happysixplus.backendcodeanalysis.entry.ChatEntry;
import local.happysixplus.backendcodeanalysis.service.ContactService;
import local.happysixplus.backendcodeanalysis.service.MessageService;
import local.happysixplus.backendcodeanalysis.service.UserService;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setMessageService(MessageService messageService, ContactService contactService, UserService userService) {
        ChatEntry.messageService = messageService;
        ChatEntry.contactService = contactService;
    }
}