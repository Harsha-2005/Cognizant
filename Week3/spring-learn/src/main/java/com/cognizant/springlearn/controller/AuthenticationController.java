package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("START - authenticate");

        // The authHeader is in the format "Basic base64encoded(user:pwd)"
        String username = getUser(authHeader);
        LOGGER.debug("Decoded Username: {}", username);

        // Generate token
        String token = jwtUtil.generateToken(username);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        LOGGER.info("END - authenticate");
        return response;
    }

    private String getUser(String authHeader) {
        // Strip the "Basic " prefix
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        // Decode base64
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded);
        
        // credentials is in the format "user:password"
        final String[] values = credentials.split(":", 2);
        return values[0];
    }
}
