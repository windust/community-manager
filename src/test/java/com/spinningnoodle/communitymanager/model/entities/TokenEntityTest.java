package com.spinningnoodle.communitymanager.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spinningnoodle.communitymanager.model.entities.fakes.DummyToken;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class TokenEntityTest {

    private final String testName = "Expedia";

    private DummyToken tokenEntity;
    private Map<String, String> fields;
    
    @BeforeEach
    public void initializeTokenGenerator(){
        tokenEntity = new DummyToken();
        fields = new HashMap<>();
        fields.put("name", testName);
        tokenEntity = (DummyToken) tokenEntity.build(fields);
    }
    
    @Test
    public void tokenSetWhenProvided(){
        fields.put("token", "valid");
        tokenEntity = (DummyToken) tokenEntity.build(fields);
        
        Assertions.assertNotNull(ReflectionTestUtils.getField(tokenEntity, "token"));
    }
    
    @Test
    public void tokenIsNullWhenNotProvided(){
        Assertions.assertNull(ReflectionTestUtils.getField(tokenEntity, "token"));
    }
    
    @Test
    public void tokenCreatedWhenCalledButIsNull(){
        Assertions.assertNotNull(tokenEntity.getToken());
    }
    
    @Test
    public void tokenReturnsStoredValueWhenPresent(){
        fields.put("token", "valid");
        tokenEntity = (DummyToken) tokenEntity.build(fields);

        String testToken = "valid";
        Assertions.assertEquals(tokenEntity.getToken(), testToken);
    }
    
    @Test
    public void tokenEndsWithEntityName(){
        String token = tokenEntity.getToken();
        String name = token.substring(token.length() - 7);

        String tokenName = "Expedia";
        Assertions.assertEquals(tokenName, name);
    }
    
    @Test
    void getName() {
        assertEquals(testName, tokenEntity.getName());
    }
    
    @Test
    void setName() {
        String newName = "Testing Coorp.";
        tokenEntity.setName(newName);
        assertEquals(newName, tokenEntity.getName());
    }
}
