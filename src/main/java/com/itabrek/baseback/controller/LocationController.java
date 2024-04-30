package com.itabrek.baseback.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class LocationController {
//
//    @MessageMapping("/location")
//    @SendTo("/topic/location")
//    public String broadcastLocation() {
//        return "locatioon";
//    }
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public String greeting(String message) throws Exception {
//        return message;
//    }
//}
