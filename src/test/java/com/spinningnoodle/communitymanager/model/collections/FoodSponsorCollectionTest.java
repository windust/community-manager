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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class FoodSponsorCollectionTest {
    private FoodSponsorCollection foodSponsorCollection;

    @Mock
    private FoodSponsor foodSponsor;

    @Mock
    private DataStorage dataStorage;

    @BeforeEach
    void setUp(){
        foodSponsorCollection = new FoodSponsorCollection();

        dataStorage = mock(GoogleSheets.class);
        foodSponsor = mock(FoodSponsor.class);

        foodSponsorCollection.dataStorage = dataStorage;
    }

    @Test
    void addingFoodSponsorToCollectionShouldUpdateTheSize(){
        FoodSponsor testFoodSponsor = new FoodSponsor();
        testFoodSponsor.setPrimaryKey(15);
        int intialSize = foodSponsorCollection.size();
        foodSponsorCollection.addToCollection(testFoodSponsor);

        assertEquals(intialSize + 1, foodSponsorCollection.size());
    }

    @Test
    void foodSponsorCanBeRetrievedByID() throws EntityNotFoundException {
        FoodSponsor testFoodSponsor = new FoodSponsor(1);
        foodSponsorCollection.addToCollection(testFoodSponsor);

        assertEquals(testFoodSponsor, foodSponsorCollection.getById(testFoodSponsor.getPrimaryKey()));
    }

    @Test
    void whenFoodSponsorDoesNotExistErrorShouldBeThrownWhenLookedUpByID(){
        FoodSponsor testFoodSponsor = new FoodSponsor(1);
        foodSponsorCollection.addToCollection(testFoodSponsor);
        assertThrows(EntityNotFoundException.class, () -> foodSponsorCollection.getById(-1));
    }

    @Test
    void whenFoodSponsorIsSetThenItReturnsTrue(){
        FoodSponsorCollection spyFoodSponsorCollection = Mockito.spy(foodSponsorCollection);
        ArrayList<FoodSponsor> mockFoodSponsorList = new ArrayList<>();
        mockFoodSponsorList.add(foodSponsor);
        Mockito.doReturn(mockFoodSponsorList).when(spyFoodSponsorCollection).getAll();
        when(foodSponsor.getName()).thenReturn("Pizza Hut");
        when(foodSponsor.getPrimaryKey()).thenReturn(1);
        when(dataStorage.update("foodSponsors", "1","foodResponse" , "yes")).thenReturn(true);
        assertTrue(spyFoodSponsorCollection.updateResponse("Pizza Hut", Response.ACCEPTED));
    }

    @Test
    void whenFoodSponsorIsIncorrectInSettingTheResponseThenItReturnsFalse() {
        FoodSponsorCollection spyFoodSponsorCollection = Mockito.spy(foodSponsorCollection);
        ArrayList<FoodSponsor> mockFoodSponsorList = new ArrayList<>();
        mockFoodSponsorList.add(foodSponsor);
        Mockito.doReturn(mockFoodSponsorList).when(spyFoodSponsorCollection).getAll();
        when(foodSponsor.getName()).thenReturn("Pizza Hut");
        when(foodSponsor.getPrimaryKey()).thenReturn(1);
        when(dataStorage.update("foodSponsors", "1", "foodResponse", "yes")).thenReturn(true);
        assertFalse(spyFoodSponsorCollection.updateResponse("Papa Murphy's", Response.ACCEPTED));
    }

    @Test
    void whenFoodSponsorRequestsDate(){
        LocalDate date = LocalDate.of(2019, 4, 13);
        FoodSponsorCollection spyFoodSponsorCollection = Mockito.spy(foodSponsorCollection);
        ArrayList<FoodSponsor> mockFoodSponsorList = new ArrayList<>();
        mockFoodSponsorList.add(foodSponsor);
        Mockito.doReturn(mockFoodSponsorList).when(spyFoodSponsorCollection).getAll();
        when(foodSponsor.getName()).thenReturn("Pizza Hut");
        when(foodSponsor.getPrimaryKey()).thenReturn(1);
        when(dataStorage.update("foodSponsors", "1", "requestedFoodDate", "04/13/2019")).thenReturn(true);
        assertTrue(spyFoodSponsorCollection.updateRequestedDate("Pizza Hut", date));
    }

    @Test
    void whenFoodSponsorInvalidNameRequestDateShouldReturnFalse(){
        LocalDate date = LocalDate.of(2020, 6, 6);
        FoodSponsorCollection spyFoodSponsorCollection = Mockito.spy(foodSponsorCollection);
        ArrayList<FoodSponsor> mockFoodSponsorList = new ArrayList<>();
        mockFoodSponsorList.add(foodSponsor);
        Mockito.doReturn(mockFoodSponsorList).when(spyFoodSponsorCollection).getAll();
        when(foodSponsor.getName()).thenReturn("Pizza Hut");
        when(foodSponsor.getPrimaryKey()).thenReturn(1);
        when(dataStorage.update("foodSponsors", "1", "requestedFoodDate", "06/06/2020")).thenReturn(true);
        assertFalse(spyFoodSponsorCollection.updateRequestedDate("Subway", date));
    }

    @Test
    void whenFoodSponsorIsLookedUpByPrimaryKey() throws EntityNotFoundException {
        FoodSponsorCollection spyFoodSponsorCollection = Mockito.spy(foodSponsorCollection);
        ArrayList<FoodSponsor> mockFoodSponsorList = new ArrayList<>();
        mockFoodSponsorList.add(foodSponsor);
        Mockito.doReturn(mockFoodSponsorList).when(spyFoodSponsorCollection).getEntitiesValues();
        when(foodSponsor.getPrimaryKey()).thenReturn(1);
        assertEquals(1, spyFoodSponsorCollection.getByPrimaryKey(1).getPrimaryKey());
    }

    @Test
    void whenFoodSponsorDoesNotExistLookedUpByPrimaryKey(){
        FoodSponsorCollection spyFoodSponsorCollect = Mockito.spy(foodSponsorCollection);
        ArrayList<FoodSponsor> mockFoodSponsorList = new ArrayList<>();
        mockFoodSponsorList.add(foodSponsor);
        Mockito.doReturn(mockFoodSponsorList).when(spyFoodSponsorCollect).getEntitiesValues();
        when(foodSponsor.getPrimaryKey()).thenReturn(1);
        assertThrows(EntityNotFoundException.class, () -> spyFoodSponsorCollect.getByPrimaryKey(-1));
    }
}
