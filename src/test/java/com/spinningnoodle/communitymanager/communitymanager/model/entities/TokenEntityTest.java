package com.spinningnoodle.communitymanager.communitymanager.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class TokenEntityTest {

    private final String testName = "Expedia";

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
    public void tokenSetWhenProvided(){
        fields.put("token", "something");
        tokenGenerator = (TokenTestingClass) tokenGenerator.build(fields);
        
        Assertions.assertNotNull(ReflectionTestUtils.getField(tokenGenerator, "token"));
    }
    
    @Test
    public void tokenIsNullWhenNotProvided(){
        Assertions.assertNull(ReflectionTestUtils.getField(tokenGenerator, "token"));
    }
    
    @Test
    public void tokenCreatedWhenCalledButIsNull(){
        Assertions.assertNotNull(tokenGenerator.getToken());
    }
    
    @Test
    public void tokenReturnsStoredValueWhenPresent(){
        fields.put("token", "something");
        tokenGenerator = (TokenTestingClass) tokenGenerator.build(fields);

        String testToken = "something";
        Assertions.assertEquals(tokenGenerator.getToken(), testToken);
    }
    
    @Test
    public void tokenEndsWithEntityName(){
        String token = tokenGenerator.getToken();
        String name = token.substring(token.length() - 7);

        String tokenName = "Expedia";
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
