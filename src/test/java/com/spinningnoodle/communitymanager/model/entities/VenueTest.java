package com.spinningnoodle.communitymanager.model.entities;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void getPrimaryKey() {
        assertEquals(testPrimaryKey, venue.getPrimaryKey());
    }

    @Test
    void setPrimaryKey() {
        int newPrimaryKey = testPrimaryKey + 1;
        venue.setPrimaryKey(newPrimaryKey);
        assertEquals(newPrimaryKey, venue.getPrimaryKey());
    }

    @Test
    void getName() {
        assertEquals(testName, venue.getName());
    }

    @Test
    void setName() {
        String newName = "Testing Coorp.";
        venue.setName(newName);
        assertEquals(newName, venue.getName());
    }

    @Test
    void getAddress() {
        assertEquals(testAddress, venue.getAddress());
    }

    @Test
    void setAddress() {
        String newAddress = "123 New Ave.";
        venue.setAddress(newAddress);
        assertEquals(newAddress, venue.getAddress());
    }

    @Test
    void getVenueId() {
        assertTrue(venue.getVenueId() >= 1);
    }

    @Test
    void setVenueId() {
        int originalId = venue.getVenueId();
        venue.setVenueId();
        assertEquals(originalId + 1, venue.getVenueId());
    }

    @Test
    void eachVenueIncrementsId() {
        Venue newVenue = new Venue();
        assertEquals(newVenue.getVenueId(), venue.getVenueId() + 1);
    }

    @Test
    void getCapacity() {
        assertEquals(testCapacity, venue.getCapacity());
    }

    @Test
    void setCapacity() {
        int newCapacity = testCapacity * 2;
        venue.setCapacity(newCapacity);
        assertEquals(newCapacity, venue.getCapacity());
    }

    @Test
    void getContactPerson() {
        assertEquals(testContactPerson, venue.getContactPerson());
    }

    @Test
    void setContactPerson() {
        String newContactPerson = "Jane Doe";
        venue.setContactPerson(newContactPerson);
        assertEquals(newContactPerson, venue.getContactPerson());
    }

    @Test
    void getContactEmail() {
        assertEquals(testContactEmail, venue.getContactEmail());
    }

    @Test
    void setContactEmail() {
        String newContactEmail = "hr@company.com";
        venue.setContactEmail(newContactEmail);
        assertEquals(newContactEmail, venue.getContactEmail());
    }

    @Test
    void getContactPhone() {
        assertEquals(testContactPhone, venue.getContactPhone());
    }

    @Test
    void setContactPhone() {
        String newPhone = "(555)444-3333";
        venue.setContactPhone(newPhone);
        assertEquals(newPhone, venue.getContactPhone());
    }

    @Test
    void getContactAltPhone() {
        assertEquals(testContactAltPhone, venue.getContactAltPhone());
    }

    @Test
    void setContactAltPhone() {
        String newAltPhone = "(555)444-3333";
        venue.setContactAltPhone(newAltPhone);
        assertEquals(newAltPhone, venue.getContactAltPhone());
    }

    @Test
    void getRequestedHostingDate() {
        assertEquals(testRequestedHostingDate, venue.getRequestedHostingDate());
    }

    @Test
    void setRequestedHostingDate() {
        String newHostingDate = "1/2/1971";
        venue.setRequestedHostingDate(newHostingDate);
        assertEquals(newHostingDate, venue.getRequestedHostingDate());
    }

    @Nested
    class buildNewVenueFromMapOfFields {

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

            builtVenue = new Venue().build(fields);
        }

        @Test
        void primaryKeyIsSet() {
            assertEquals(Integer.parseInt(fields.get("primaryKey")), venue.getPrimaryKey());
        }

        @Test
        void nameIsSet() {
            assertEquals(fields.get("name"), builtVenue.getName());
        }

        @Test
        void addressIsSet() {
            assertEquals(fields.get("address"), builtVenue.getAddress());
        }

        @Test
        void capacityIsSet() {
            assertEquals(Integer.parseInt(fields.get("capacity")), builtVenue.getCapacity());
        }

        @Test
        void contactPersonIsSet() {
            assertEquals(fields.get("contactPerson"), builtVenue.getContactPerson());
        }

        @Test
        void contactEmailIsSet() {
            assertEquals(fields.get("contactEmail"), builtVenue.getContactEmail());
        }

        @Test
        void contactPhoneIsSet() {
            assertEquals(fields.get("contactPhone"), builtVenue.getContactPhone());
        }

        @Test
        void contactAltPhoneIsSet() {
            assertEquals(fields.get("contactAltPhone"), builtVenue.getContactAltPhone());
        }

        @Test
        void requestedHostingDateIsSet() {
            assertEquals(fields.get("requestedHostingDate"), builtVenue.getRequestedHostingDate());
        }


        @Nested
        class emptyFieldCheck {

            private Map<String, String> field = new HashMap<>();
            private Venue venue;
            private final String testingName = null;

            @BeforeEach
            void fieldSetup() {

                field.put("name", testingName);
                venue = new Venue().build(field);

            }

            @Test
            void emptyFieldCheck() {
                try {
                    assertEquals(venue.getName(), null);
                } catch (Exception e) {
                    assertThrows(e.getClass(), () -> doesntThrowException());
                }
            }

            private String doesntThrowException() {
                return "Empty name";
            }
        }
    }
}