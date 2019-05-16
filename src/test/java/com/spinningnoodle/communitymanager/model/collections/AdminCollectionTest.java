package com.spinningnoodle.communitymanager.model.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


public class AdminCollectionTest {
    private AdminCollection adminCollection;

    @Mock
    private Admin admin;

    @Mock
    private DataStorage dataStorage;

    @BeforeEach
    void setUp(){
        adminCollection = new AdminCollection();

        dataStorage = mock(GoogleSheets.class);
        admin = mock(Admin.class);

        adminCollection.dataStorage = dataStorage;
    }

    @Test
    void adminCanBeRetrievedById()throws EntityNotFoundException{
        Admin testAdmin = new Admin(1);
        adminCollection.addToCollection(testAdmin);
        assertEquals(testAdmin, adminCollection.getByPrimaryKey(testAdmin.getPrimaryKey()));
    }

    @Test
    void whenAdminDoesNotExistAnErrorShouldBeThrownWhenLookedUpByID(){
        Admin testAdmin = new Admin(1);
        adminCollection.addToCollection(testAdmin);
        assertThrows(EntityNotFoundException.class, () -> adminCollection.getByPrimaryKey(-1));
    }
}
