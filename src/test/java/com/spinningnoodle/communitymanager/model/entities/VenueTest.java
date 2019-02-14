package com.spinningnoodle.communitymanager.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VenueTest {

    private Venue venue;
    private final int testPrimaryKey = 1;
    private final String testName = "Test Venue";
    private final String testAddress = "123 Testing St.";
    private final int testCapacity = 60;
    private final String testContactPerson = "John Smith";
    private final String testContactEmail = "jsmith@mail.com";
    private final String testContactPhone = "5555555555";
    private final String testContactAltPhone = "(555) 555 - 5555";
    private final String testRequestedHostingDate = "Jan. 1, 1970";

    @BeforeEach
    void setUp() {
        venue = new Venue(testPrimaryKey, testName, testAddress, testCapacity, testContactPerson,
            testContactEmail, testContactPhone, testContactAltPhone, testRequestedHostingDate);
    }

    @Test
    void whenIHaveAVenueThenICanGetPrimaryKey() {
        assertEquals(testPrimaryKey, venue.getPrimaryKey());
    }

    @Test
    void whenIHaveAVenueThenICanSetThePrimaryKey() throws UnexpectedPrimaryKeyException {
        int newPrimaryKey = testPrimaryKey + 1;
        venue.setPrimaryKey(newPrimaryKey);
        assertEquals(newPrimaryKey, venue.getPrimaryKey());
    }

    @Test
    void whenIAttemptToSetThePrimaryKeyToAnUnexpectedNumberThenIGetUnexpectedPrimaryKeyException() {
        int newPrimaryKey = -2;
        assertThrows(UnexpectedPrimaryKeyException.class, () -> venue.setPrimaryKey(newPrimaryKey));
    }

    @Test
    void whenIHaveAVenueThenICanGetAddress() {
        assertEquals(testAddress, venue.getAddress());
    }

    @Test
    void whenIHaveAVenueThenICanSetTheAddress() {
        String newAddress = "123 New Ave.";
        venue.setAddress(newAddress);
        assertEquals(newAddress, venue.getAddress());
    }

    @Test
    void whenIHaveAVenueICanGetItsVenueId() {
        assertTrue(venue.getEntityId() >= 1);
    }

    @Test
    void whenIHaveAVenueICanSetItsVenueIdToTheNextId() {
        int originalId = venue.getEntityId();
        venue.setEntityId();
        assertEquals(originalId + 1, venue.getEntityId());
    }

    @Test
    void eachVenueIncrementsIdByOne() {
        Venue newVenue = new Venue();
        assertEquals(newVenue.getEntityId(), venue.getEntityId() + 1);
    }

    @Test
    void whenIHaveAVenueThenICanGetCapacity() {
        assertEquals(testCapacity, venue.getCapacity());
    }

    @Test
    void whenIHaveAVenueThenICanSetItsCapacity() {
        int newCapacity = testCapacity * 2;
        venue.setCapacity(newCapacity);
        assertEquals(newCapacity, venue.getCapacity());
    }

    @Test
    void whenIHaveAVenueThenICanGetItsContactPerson() {
        assertEquals(testContactPerson, venue.getContactPerson());
    }

    @Test
    void whenIHaveAVenueThenICanSetTheContactPerson () {
        String newContactPerson = "Jane Doe";
        venue.setContactPerson(newContactPerson);
        assertEquals(newContactPerson, venue.getContactPerson());
    }

    @Test
    void whenIHaveAVenueThenICanGetTheContactEmail() {
        assertEquals(testContactEmail, venue.getContactEmail());
    }

    @Test
    void whenIHaveAVenueThenICanSetTheContactEmail() {
        String newContactEmail = "hr@company.com";
        venue.setContactEmail(newContactEmail);
        assertEquals(newContactEmail, venue.getContactEmail());
    }

    @Test
    void whenIHaveAVenueThenICanGetTheContactPhoneNumber() {
        assertEquals(testContactPhone, venue.getContactPhone());
    }

    @Test
    void whenIHaveAVenueThenICanSetTheContactPhoneNumber() {
        String newPhone = "(555)444-3333";
        venue.setContactPhone(newPhone);
        assertEquals(newPhone, venue.getContactPhone());
    }

    @Test
    void whenIHaveAVenueThenICanGetTheAltContactPhoneNumber() {
        assertEquals(testContactAltPhone, venue.getContactAltPhone());
    }

    @Test
    void whenIHaveAVenueThenICanSetTheAltContactPhoneNumber() {
        String newAltPhone = "(555)444-3333";
        venue.setContactAltPhone(newAltPhone);
        assertEquals(newAltPhone, venue.getContactAltPhone());
    }

    @Test
    void whenIHaveAVenueThenICanGetTheDateIRequestedTheVenueToHost() {
        assertEquals(testRequestedHostingDate, venue.getRequestedHostingDate());
    }

    @Test
    void whenIHaveAVenueThenICanSetTheDateIRequestThisVenueToHost() {
        String newHostingDate = "1/2/1971";
        venue.setRequestedHostingDate(newHostingDate);
        assertEquals(newHostingDate, venue.getRequestedHostingDate());
    }

    @Nested
    class BuildNewVenueFromMapOfFieldsTest {

        private Venue builtVenue;
        private Map<String, String> fields = new HashMap<>();

        @BeforeEach
        void setUp() {
            fields.put("primaryKey", Integer.toString(testPrimaryKey));
            fields.put("name", testName);
            fields.put("address", testAddress);
            fields.put("capacity", Integer.toString(testCapacity));
            fields.put("contactPerson", testContactPerson);
            fields.put("contactEmail", testContactEmail);
            fields.put("contactPhone", testContactPhone);
            fields.put("contactAltPhone", testContactAltPhone);
            fields.put("requestedHostingDate", testRequestedHostingDate);

            try {
                builtVenue = new Venue().build(fields);
            } catch (UnexpectedPrimaryKeyException e) {
                e.printStackTrace();
            }
        }

        @Test
        void whenIBuildAVenueThenPrimaryKeyIsSet() {
            assertEquals(Integer.parseInt(fields.get("primaryKey")), venue.getPrimaryKey());
        }

        @Test
        void whenIBuildAVenueThenNameIsSet() {
            assertEquals(fields.get("name"), builtVenue.getName());
        }

        @Test
        void whenIBuildAVenueThenAddressIsSet() {
            assertEquals(fields.get("address"), builtVenue.getAddress());
        }

        @Test
        void whenIBuildAVenueThenCapacityIsSet() {
            assertEquals(Integer.parseInt(fields.get("capacity")), builtVenue.getCapacity());
        }

        @Test
        void whenIBuildAVenueThenContactPersonIsSet() {
            assertEquals(fields.get("contactPerson"), builtVenue.getContactPerson());
        }

        @Test
        void whenIBuildAVenueThenContactEmailIsSet() {
            assertEquals(fields.get("contactEmail"), builtVenue.getContactEmail());
        }

        @Test
        void whenIBuildAVenueThenContactPhoneIsSet() {
            assertEquals(fields.get("contactPhone"), builtVenue.getContactPhone());
        }

        @Test
        void whenIBuildAVenueThenContactAltPhoneIsSet() {
            assertEquals(fields.get("contactAltPhone"), builtVenue.getContactAltPhone());
        }

        @Test
        void whenIBuildAVenueThenRequestedHostingDateIsSet() {
            assertEquals(fields.get("requestedHostingDate"), builtVenue.getRequestedHostingDate());
        }


        @Nested
        class BuildVenueWithNoFieldsTest {

            private Map<String, String> fields = new HashMap<>();
            private Venue venue;

            @BeforeEach
            void setUp() {
                try {
                    venue = new Venue().build(fields);
                } catch (UnexpectedPrimaryKeyException e) {
                    e.printStackTrace();
                }
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenPrimaryKeyShouldBeNegativeOne() {
                assertEquals(-1, venue.getPrimaryKey());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenNameShouldBeNull() {
                assertNull(venue.getName());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenAddressShouldBeNull() {
                assertNull(venue.getAddress());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenCapacityShouldBeZero() {
                assertEquals(0, venue.getCapacity());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenContactPersonShouldBeNull() {
                assertNull(venue.getContactPerson());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenContactEmailShouldBeNull() {
                assertNull(venue.getContactEmail());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenContactPhoneShouldBeNull() {
                assertNull(venue.getContactPhone());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenContactAltPhoneShouldBeNull() {
                assertNull(venue.getContactAltPhone());
            }

            @Test
            void whenIBuildAVenueWithNoFieldsThenRequestedHostingDateShouldBeNull() {
                assertNull(venue.getRequestedHostingDate());
            }
        }
    }
}