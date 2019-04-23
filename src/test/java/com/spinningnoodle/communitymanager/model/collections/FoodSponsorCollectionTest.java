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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
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
}
