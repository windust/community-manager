package com.spinningnoodle.communitymanager.datastorage;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DataStorageTest {

    private DataStorage testStorage;

    @BeforeEach
    void initializeDataBase()throws GeneralSecurityException {
        testStorage = new DummyStorage("123");
    }

    @Test
    void throwsGeneralSecurityExceptionWhenCantConnectToDataStorage() {
        Assertions.assertThrows(GeneralSecurityException.class, () -> new DummyStorage("133"));
    }

    @Test
    void whenIOpenDataStorageICanGetID() {
        assertEquals("123", testStorage.getStorageID());
    }

    @Test
    void whenIOpenDataStorageICanGetName(){
        assertEquals("123", testStorage.getName());
    }

    @Test
    void whenIRequestTableOfVenuesIShouldGetBackResults() throws IOException {
        List<Map<String, String>> expected;
        List<Map<String, String>> list = new ArrayList<>();
        Map<String,String> row = new HashMap<>();
        row.put("primaryKey", "1");
        row.put("name","Excellent");
        row.put("address","100 Nowhere St");
        row.put("capacity", "100");
        row.put("contactPerson", "Freddy");
        row.put("contactEmail", "freddy@excellent.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","123N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("name","Amazing");
        row.put("address","200 Nowhere St");
        row.put("capacity", "150");
        row.put("contactPerson", "Nimret");
        row.put("contactEmail", "nimret@amazing.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","143N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        expected = list;
        assertEquals(expected, testStorage.readAll("venues"));
    }

    @Test
    void whenIRequestNonExistentTableIGetIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> testStorage.readAll("sprinklers"));
    }
}
