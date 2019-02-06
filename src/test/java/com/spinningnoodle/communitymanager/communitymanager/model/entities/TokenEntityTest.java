package com.spinningnoodle.communitymanager.communitymanager.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class TokenEntityTest {

    private final String testName = "expedia";
    private final String tokenName = "Expedia";
    private final String testToken = "something";
    
    private TokenTestingClass tokenGenerator;
    private Map<String, String> fields;
    
    @BeforeEach
    public void initializeTokenGenerator(){
        tokenGenerator = new TokenTestingClass();
        fields = new HashMap<>();
        fields.put("name", testName);
        tokenGenerator = (TokenTestingClass) tokenGenerator.build(fields);
    }
    
    @Test
    public void TokenSetWhenProvided(){
        fields.put("token", "something");
        tokenGenerator = (TokenTestingClass) tokenGenerator.build(fields);
        
        Assertions.assertNotNull(ReflectionTestUtils.getField(tokenGenerator, "token"));
    }
    
    @Test
    public void TokenIsNullWhenNotProvided(){
        Assertions.assertNull(ReflectionTestUtils.getField(tokenGenerator, "token"));
    }
    
    @Test
    public void TokenCreatedWhenCalledButIsNull(){
        Assertions.assertNotNull(tokenGenerator.getToken());
    }
    
    @Test
    public void TokenReturnsStoredValueWhenPresent(){
        fields.put("token", "something");
        tokenGenerator = (TokenTestingClass) tokenGenerator.build(fields);
        
        Assertions.assertEquals(tokenGenerator.getToken(), testToken);
    }
    
    @Test
    public void TokenEndsWithEntityName(){
        String token = tokenGenerator.getToken();
        String name = token.substring(token.length() - 7);
        
        Assertions.assertEquals(tokenName, name);
    }
    
    @Test
    void getName() {
        assertEquals(testName, tokenGenerator.getName());
    }
    
    @Test
    void setName() {
        String newName = "Testing Coorp.";
        tokenGenerator.setName(newName);
        assertEquals(newName, tokenGenerator.getName());
    }
}