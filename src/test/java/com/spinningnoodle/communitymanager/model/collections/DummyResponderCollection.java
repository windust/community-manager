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
import com.spinningnoodle.communitymanager.model.entities.DummyResponder;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;
import com.spinningnoodle.communitymanager.model.observer.Observable;
import java.io.IOException;
import java.util.Map;

public class DummyResponderCollection<T extends ResponderEntity> extends ResponderCollection {

    public DummyResponderCollection(DataStorage dataStorage) {
        super(dataStorage, "tokenTest");
    }
    
    @Override
    public DummyResponderCollection fetchFromDataStorage() {
        try{
            DummyResponderCollection responderCollection = new DummyResponderCollection(this.getDataStorage());
            
            for(Map<String, String> entity : getDataStorage().readAll("tokenTest")){
                responderCollection.addToCollection(new DummyResponder().build(entity));
            }
        
            return responderCollection;
        } catch (IOException e){
            System.out.println("datastorage error");
        }
        
        return null;
    }
    
    @Override
    public void update(Observable observable) {
    
    }
}
