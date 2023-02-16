package com.mauromidolo.windowsstartup.rest;

import com.mauromidolo.windowsstartup.Manager;
import com.mauromidolo.windowsstartup.NetworkManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/command")
public class HttpRest {

    private Manager networkManager;

    HttpRest(Manager networkManager) {
        this.networkManager = networkManager;
    }

    public HttpRest() {}

    @PostConstruct
    public void init(){
        networkManager = NetworkManager.getInstance();
    }


    @GetMapping("/start/{password}")
    public ResponseEntity<Object> startComputer(@PathVariable("password") String password) throws JsonProcessingException {
        networkManager.sendWakeOnLanPackage(password);
        return ResponseEntity.status(HttpStatus.OK).body(generateSuccessResponse());
    }

    @GetMapping("/start/{password}/{macAddress}")
    public ResponseEntity<Object> startComputer(@PathVariable("password") String password, @PathVariable("macAddress") String macAddress) throws JsonProcessingException {
        networkManager.sendWakeOnLanPackage(password, macAddress);
        return ResponseEntity.status(HttpStatus.OK).body(generateSuccessResponse());
    }


    @GetMapping("/status")
    public ResponseEntity<Object> checkServer() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(generateSuccessResponse());
    }

    private String generateSuccessResponse() throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        response.put("Status", "OK");
        return new ObjectMapper().writeValueAsString(response);
    }
}
