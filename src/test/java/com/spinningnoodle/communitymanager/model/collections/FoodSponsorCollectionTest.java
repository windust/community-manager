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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

public class FoodSponsorCollectionTest {
    @Mock
    private FoodSponsorCollection foodSponsorCollection;

    @BeforeEach
    void setUp(){
        foodSponsorCollection = mock(FoodSponsorCollection.class);

        when(foodSponsorCollection.fetchFromDataStorage()).thenReturn(foodSponsorCollection);
    }

    
}
