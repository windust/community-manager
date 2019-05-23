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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FoodSponsorTest {
    private FoodSponsor foodSponsor;
    private final int testPrimaryKey = 1;
    private final String testFoodSponsor = "Food Sponsor";
    private final String testAddress = "124 Bellevue Way";
    private final String testContactPerson = "Aaron Donald";
    private final String testContactEmail = "Adonald@yahoo.com";
    private final String testPhone = "(253) 123 -4353";
    private final String testAltPhone = "5349831234";
    private final LocalDate testReqeustedDate = LocalDate.of(2020, 6, 6);

    @BeforeEach
    void setUp(){
        foodSponsor = new FoodSponsor(testPrimaryKey, testFoodSponsor, testAddress, testContactPerson, testContactEmail,
            testPhone, testAltPhone, testReqeustedDate);
    }

    @Test
    void whenIHaveAFoodSponsorICanGetPrimaryKey(){
        assertEquals(testPrimaryKey, foodSponsor.getPrimaryKey());
    }

    @Test
    void whenIHaveASponsorICanSetPrimaryKey()throws UnexpectedPrimaryKeyException {
        int newPrimaryKey = 2;
        foodSponsor.setPrimaryKey(newPrimaryKey);
        assertEquals(2, foodSponsor.getPrimaryKey());
    }

    @Test
    void whenIHaveAFoodSponsorICanGetTheNameOfFoodSponsor(){
        assertEquals(testFoodSponsor, foodSponsor.getName());
    }

    @Test
    void whenIHaveANewFoodSponsorICanSetTheNameOfThatFoodSponsor(){
        String newFoodSponsor = "Pappa Murphy's";
        foodSponsor.setName(newFoodSponsor);
        assertEquals(newFoodSponsor, foodSponsor.getName());
    }

    @Test
    void whenIHaveAnAddressICanGetTheAddress(){
        assertEquals(testAddress, foodSponsor.getAddress());
    }

    @Test
    void whenIHaveAnAddressICanSetANewAddress(){
        String newAddress = "123 Seattle Bld";
        foodSponsor.setAddress(newAddress);
        assertEquals(newAddress, foodSponsor.getAddress());
    }

    @Test
    void whenIHaveAContactICanGetTheContact(){
        assertEquals(testContactPerson, foodSponsor.getContactPerson());
    }

    @Test
    void whenIHaveANewContactICanSetThatContact(){
        String newContact = "Jared Goff";
        foodSponsor.setContactPerson(newContact);
        assertEquals(newContact, foodSponsor.getContactPerson());
    }

    @Test
    void whenIHaveAContactEmailICanGetThatEmail(){
        assertEquals(testContactEmail, foodSponsor.getContactEmail());
    }

    @Test
    void whenIHaveANewContactEmailICanSetTheNewEmail(){
        String newEmail = "JGoff@msn.com";
        foodSponsor.setContactEmail(newEmail);
        assertEquals(newEmail, foodSponsor.getContactEmail());
    }

    @Test
    void whenIHaveAContactPhoneICanGetThatPhoneNumber(){
        assertEquals(testPhone, foodSponsor.getContactPhone());
    }

    @Test
    void whenIHaveANewContactPhoneICanSetTheNewPhoneNumber(){
        String newPhone = testAltPhone;
        foodSponsor.setContactPhone(newPhone);
        assertEquals(newPhone, foodSponsor.getContactPhone());
    }

    @Test
    void whenIHaveAnAltContactPhoneICanGetThatPhoneNumber(){
        assertEquals(testAltPhone, foodSponsor.getContactAltPhone());
    }

    @Test
    void whenIHaveANewAltContactPhoneICanSetTheNewALtPhoneNumber(){
        String newAltPhone = testPhone;
        foodSponsor.setContactAltPhone(newAltPhone);
        assertEquals(newAltPhone, foodSponsor.getContactAltPhone());
    }

    @Test
    void whenAFoodSponsorHasRequestedDateICanGetThatDate(){
        assertEquals(testReqeustedDate, foodSponsor.getRequestedDate());
    }

    @Test
    void whenAFoodSponsorRequestsANewDateICanSetThatNewDate(){
        LocalDate newDate = LocalDate.of(2019, 04, 13);
        foodSponsor.setRequestedDate(newDate);
        assertEquals(newDate, foodSponsor.getRequestedDate());
    }

    @Nested
    class BuildNewFoodSponsorFromMapOfFieldsTest{
        private FoodSponsor buildtFoodSponsor;
        private Map<String, String> fields = new HashMap<>();

        @BeforeEach
        void setUp(){
            fields.put("primaryKey", Integer.toString(testPrimaryKey));
            fields.put("name", testFoodSponsor);
            fields.put("address", testAddress);
            fields.put("contactPerson", testContactPerson);
            fields.put("contactEmail", testContactEmail);
            fields.put("contactPhone", testPhone);
            fields.put("contactAltPhone", testAltPhone);
            fields.put("requestedDate", testReqeustedDate.toString());

            try{
                buildtFoodSponsor =  new FoodSponsor().build(fields);
            }catch (UnexpectedPrimaryKeyException e) {
                e.printStackTrace();
            }
        }

        @Test
        void whenIBuildAFoodSponsorThenPrimaryKeyIsSet(){
            assertEquals(Integer.parseInt(fields.get("primaryKey")) , foodSponsor.getPrimaryKey());
        }

        @Test
        void whenIBuildAFoodSponsorThenNameIsSet(){
            assertEquals(fields.get("name"), buildtFoodSponsor.getName());
        }

        @Test
        void whenIBuildAFoodSponsorThenAddressIsSet(){
            assertEquals(fields.get("address"), buildtFoodSponsor.getAddress());
        }

        @Test
        void whenIBuildAFoodSponsorThenContactPersonIsSet(){
            assertEquals(fields.get("contactPerson"), buildtFoodSponsor.getContactPerson());
        }

        @Test
        void whenIBuildAFoodSponsorThenContactEmailIsSet(){
            assertEquals(fields.get("contactEmail"), buildtFoodSponsor.getContactEmail());
        }

        @Test
        void whenIBuildAFoodSponsorThenContactPhoneIsSet(){
            assertEquals(fields.get("contactPhone"), buildtFoodSponsor.getContactPhone());
        }

        @Test
        void whenIBuildAFoodSponsorThenContactAltPhoneIsSet(){
            assertEquals(fields.get("contactAltPhone"), buildtFoodSponsor.getContactAltPhone());
        }

        @Test
        void whenIBuildAFoodSponsorThenContactRequestedDateIsSet(){
            assertEquals(fields.get("requestedDate"), buildtFoodSponsor.getRequestedDate().toString());
        }

        @Nested
        class BuildFoodSponsorWithNoFieldsTest{
            private Map<String, String> fields = new HashMap<>();
            private FoodSponsor foodSponsor;

            @BeforeEach
            void SetUp(){
                try{
                    foodSponsor =  new FoodSponsor().build(fields);
                }catch (UnexpectedPrimaryKeyException e) {
                    e.printStackTrace();
                }
            }

            @Test
            void whenIBuildFoodSponsorWithNoFieldsThenPrimaryKeyShouldBeNegativeOne(){
                assertEquals(-1, foodSponsor.getPrimaryKey());
            }

            @Test
            void whenIBuildAFoodSponsorWithNoFieldsThenNameShouldBeNull(){
                assertNull(foodSponsor.getName());
            }

            @Test
            void whenIBuildAFoodSponsorWithNoFieldsThenAddressShouldBeNull(){
                assertNull(foodSponsor.getAddress());
            }

            @Test
            void whenIBuildAFoodSponsorWithNoFieldsThenContactPersonShouldBeNull(){
                assertNull(foodSponsor.getContactPerson());
            }

            @Test
            void whenIBuildAFoodSponsorWithNoFieldsThenContactEmailShouldBeNull(){
                assertNull(foodSponsor.getContactEmail());
            }

            @Test
            void whenIBuildAFoodSponsorWithNoFieldsThenPhoneShouldBeNull(){
                assertNull(foodSponsor.getContactPhone());
            }

            @Test
            void whenIBuildAFoodSponsorWithNoFieldsThenAltPhoneShouldBeNull(){
                assertNull(foodSponsor.getContactAltPhone());
            }

            @Test
            void whenIBuildAFoodSponsorWithNoFieldsThenRequestedDateShouldBeNull(){
                assertNull(foodSponsor.getRequestedDate());
            }
        }
    }
}