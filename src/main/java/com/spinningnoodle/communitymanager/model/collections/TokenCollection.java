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
import com.spinningnoodle.communitymanager.model.entities.TokenEntity;

public abstract class TokenCollection extends EntityCollection<TokenEntity> {

    public TokenCollection(DataStorage dataStorage) {
        super(dataStorage);
    }

    public boolean validToken(String token) {
        return false;
    }

    private void updateToken(DataStorage dataStorage, int primaryKey, String newToken) {
        dataStorage.update(this.getTableName(), Integer.toString(primaryKey), "Token", newToken);
    }

    @Override
    public void update(TokenEntity observable) {
        updateToken(getDataStorage(), observable.getPrimaryKey(),observable.getToken());
    }
}