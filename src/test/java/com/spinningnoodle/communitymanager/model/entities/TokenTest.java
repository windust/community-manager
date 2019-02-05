package com.spinningnoodle.communitymanager.model.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {
    private String nullValue = null;
    private String valueInMap = "token1";

    @Test
    void tokenNotNull(){
        String token = "Test value";
        assertTrue(!token.equals(nullValue));
    }

    @Test
    void uniqueToke(){
        String token = "Test value";
        assertTrue(!token.equals(valueInMap));
    }
}
