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
import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.TokenEntity;
import java.util.List;

public class DummyTokenCollection extends TokenCollection {

    public DummyTokenCollection(DataStorage dataStorage) {
        super(dataStorage);
    }

    @Override
    public boolean validToken(String token) {
        if(token.equals("valid")){
            return true;
        }
        else {
            return false;
        }
    }
    
    @Override
    public void fetchFromDataStorage() {
    
    }
    
    @Override
    public void addToCollection(TokenEntity entity) {
    
    }

    @Override
    public TokenEntity getById(int venueId) throws EntityNotFoundException {
        return null;
    }
    
    @Override
    public List getAll() {
        return null;
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public void clear() {
    
    }

    @Override
    public String getTableName() {
        return null;
    }
}
