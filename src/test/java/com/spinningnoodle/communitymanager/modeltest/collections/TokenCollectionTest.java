package com.spinningnoodle.communitymanager.modeltest.collections;

import com.spinningnoodle.communitymanager.modeltest.collections.fakes.DummyTokenCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenCollectionTest {
    private DummyTokenCollection tokenCollection;
    private final String validToken = "valid";
    private final String invalidToken = "invalid";
    
    @BeforeEach
    public void initializeTokenCollection(){
        tokenCollection = new DummyTokenCollection();
    }
    
    @Test
    @DisplayName("validToken() return true when token is valid")
    public void validTokenReturnsTrueWhenValid(){
        Assertions.assertTrue(tokenCollection.validToken(validToken));
    }
    
    @Test
    @DisplayName("validToken() return false when token is invalid")
    public void validTokenReturnsFalseWhenInvalid(){
        Assertions.assertFalse(tokenCollection.validToken(invalidToken));
    }
    
    @Test
    @DisplayName("validToken() return false when token is empty")
    public void validTokenReturnsFalseWhenEmpty(){
        Assertions.assertFalse(tokenCollection.validToken(""));
    }
}
