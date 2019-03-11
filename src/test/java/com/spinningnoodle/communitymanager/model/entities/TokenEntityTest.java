package com.spinningnoodle.communitymanager.model.entities;
/**
 *  LICENSE
 *  Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther, Jhakon Pappoe, and Tyler Roemer.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  END OF LICENSE INFORMATION
 */
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class TokenEntityTest {

    private final String testName = "Expedia";

    private DummyToken tokenEntity;
    private Map<String, String> fields;
    
    @BeforeEach
    void initializeTokenGenerator(){
        tokenEntity = new DummyToken();
        fields = new HashMap<>();
        fields.put("name", testName);
        fields.put("token", tokenEntity.generateNewToken());
        tokenEntity = (DummyToken) tokenEntity.build(fields);
        tokenEntity.setToken(tokenEntity.generateNewToken());
    }
    
    @Test
    void tokenSetWhenProvided(){
        fields.put("token", tokenEntity.getOrGenerateToken());
        tokenEntity = (DummyToken) tokenEntity.build(fields);
        
        Assertions.assertNotNull(ReflectionTestUtils.getField(tokenEntity, "token"));
    }
    
    @Test
    void tokenIsEmptyStringWhenNotProvided() {
        TokenEntity newTokenEntity = new DummyToken();
        Assertions.assertEquals("", ReflectionTestUtils.getField(newTokenEntity, "token"));
    }
    
    @Test
    void tokenCreatedWhenCalledButIsNull(){
        Assertions.assertNotNull(tokenEntity.getToken());
    }
    
    @Test
    void tokenReturnsStoredValueWhenPresent(){
        Assertions.assertTrue(tokenEntity.getToken().length() >= 1);
    }
    
    @Test
    void tokenStartsWithEntityName(){
        String token = tokenEntity.getToken();
        String name = token.substring(0, testName.length());

        String tokenName = "Expedia";
        Assertions.assertEquals(tokenName, name);
    }

    @Test
    void invalidTokenIsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,  () -> tokenEntity.setToken("invalid token"));
    }

    @Test
    void whitespaceInNameIsRemovedInToken() {
        tokenEntity.setName("two words");
        tokenEntity.setToken(tokenEntity.generateNewToken());

        assertAll(() -> {
            assertTrue(tokenEntity.getToken().toLowerCase().contains("two"));
            assertTrue(tokenEntity.getToken().toLowerCase().contains("words"));
            assertFalse(tokenEntity.getToken().contains(" "));
        });
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
