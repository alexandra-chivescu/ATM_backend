package com.Whitebox.ATM.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisService {
    private static Map<Integer, String> clientTokens = new HashMap<>();

    public void addTokenForClient(int clientId, String token) {
        clientTokens.put(clientId, token);
    }

    public void removeTokenForClient(int clientId) {
        clientTokens.remove(clientId);
    }

    public boolean clientHasToken(int clientId) {
        return clientTokens.containsKey(clientId);
    }

    public String getTokenForClient(int clientId) {
        return clientTokens.get(clientId);
    }
}
