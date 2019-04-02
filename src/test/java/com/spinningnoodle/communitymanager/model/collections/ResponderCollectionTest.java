package com.spinningnoodle.communitymanager.model.collections;
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

import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResponderCollectionTest {
    private DummyResponderCollection tokenCollection;
    private final String validToken = "valid";
    private final String invalidToken = "invalid";
    
    @BeforeEach
    public void initializeTokenCollection() throws GeneralSecurityException {
        DummyStorage dummyStorage = new DummyStorage("123");
        tokenCollection = new DummyResponderCollection(dummyStorage);
    }
    
    @Test
    @DisplayName("getEntityByToken() returns entity when token is valid")
    public void returnsEntityWhenTokenIsValid(){
        Assertions.assertNotNull(tokenCollection.getEntityByToken(validToken));
    }
    
    @Test
    @DisplayName("getEntityByToken() throws IllegalArgumentException when token isn't valid")
    public void throwsExceptionWhenTokenIsInvalid(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> tokenCollection.getEntityByToken(invalidToken));
    }
}
