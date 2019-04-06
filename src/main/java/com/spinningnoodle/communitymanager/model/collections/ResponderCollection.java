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
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;

public abstract class ResponderCollection<T extends ResponderEntity> extends EntityCollection<T> {
    
    public ResponderCollection(String tableName){
        super(tableName);
    }
    
    public ResponderCollection(DataStorage dataStorage, String tableName){
        super(dataStorage, tableName);
    }

    public T getEntityByToken(String token) {
        for(T tokenEntity : getEntitiesValues()) {
            if(tokenEntity.getToken().equals(token)) {
                return tokenEntity;
            }
        }
        
        throw new IllegalArgumentException("Invalid Token: " + token);
    }
    
//    protected String convertResponseToText(Response response){
//        switch (response){
//            case ACCEPTED:
//                return "yes";
//            case DECLINED:
//                return "no";
//            default:
//                return "";
//        }
//    }

    private void updateToken(int primaryKey, String newToken) {
        dataStorageUpdate(this.getTableName(), Integer.toString(primaryKey), "token", newToken);
    }

    @Override
    public void update(ResponderEntity observable) {
        updateToken(observable.getPrimaryKey(),observable.getToken());
    }
}