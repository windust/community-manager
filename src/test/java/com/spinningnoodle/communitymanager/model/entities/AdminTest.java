package com.spinningnoodle.communitymanager.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AdminTest {
    private Admin admin;
    private final int testPrimaryKey = 1;
    private final String testEmail = "jaredGoff@gmail.com";
    private final String testName = "J.G";

    @BeforeEach
    void setUp(){
        admin = new Admin(testPrimaryKey, testEmail, testName);
    }

    @Test
    void whenIHaveAnAdminICanGetPrimaryKey(){
        assertEquals(testPrimaryKey, admin.getPrimaryKey());
    }

    @Test
    void whenIHaveAnAdminICanSetThePrimaryKey(){
        int newPrimaryKey = testPrimaryKey + 1;
        admin.setPrimaryKey(newPrimaryKey);
        assertEquals(newPrimaryKey, admin.getPrimaryKey());
    }

    @Test
    void whenIHaveAnAdminEmailICanGetThatEmail(){
        assertEquals(testEmail, admin.getEmail());
    }

    @Test
    void whenIHaveAnAdminEmailICanSetThatEmail(){
        String newEmail = "AD@gmail.com";
        admin.setEmail(newEmail);
        assertEquals(newEmail, admin.getEmail());
    }

    @Test
    void whenIHaveAnAdminNameICanGetThatName(){
        assertEquals(testName, admin.getName());
    }

    @Test
    void whenIHaveAnAdminNameICanSetThatName(){
        String newName = "A.D";
        admin.setName(newName);
        assertEquals(newName, admin.getName());
    }

    @Nested
    class BuildAdminFromMapOfFieldsTest{
        private Admin buildAdmin;
        private Map<String, String> fields = new HashMap<>();

        @BeforeEach
        void setUp(){
            fields.put("primaryKey", Integer.toString(testPrimaryKey));
            fields.put("email", testEmail);
            fields.put("name", testName);

            try{
                buildAdmin = new Admin().build(fields);
            } catch (UnexpectedPrimaryKeyException e) {
                e.printStackTrace();
            }
        }

        @Test
        void whenIBuildAAdminThePrimaryKeyIsSet(){
            assertEquals(Integer.parseInt(fields.get("primaryKey")), admin.getPrimaryKey());
        }

        @Test
        void whenIBuildAAdminEmailIsSet(){
            assertEquals(fields.get("email"), admin.getEmail());
        }

        @Test
        void whenIBuildAAdminNameIsSet(){
            assertEquals(fields.get("name"), admin.getName());
        }

        @Nested
        class BuildAdminWithNoFields{
            private Admin admin;
            private Map<String, String> fields = new HashMap<>();

            @BeforeEach
            void setUp(){
                try{
                    admin = new Admin().build(fields);
                } catch (UnexpectedPrimaryKeyException e) {
                    e.printStackTrace();
                }
            }

            @Test
            void whenIBuildAnAdminWithNoFieldThenPrimaryKeyShouldBeNegative(){
                assertEquals(-1, admin.getPrimaryKey());
            }

            @Test
            void whenIBuildAnAdminWithNoFieldThenEmailShouldBeNull(){
                assertNull(admin.getEmail());
            }

            @Test
            void whenIBuildAnAdminWithNoFieldThenNameShouldBeNull(){
                assertNull(admin.getName());
            }
        }
    }
}