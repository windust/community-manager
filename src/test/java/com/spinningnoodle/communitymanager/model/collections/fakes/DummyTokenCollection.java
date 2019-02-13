package com.spinningnoodle.communitymanager.model.collections.fakes;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.collections.TokenCollection;
import com.spinningnoodle.communitymanager.model.entities.IEntity;
import java.util.List;

public class DummyTokenCollection extends TokenCollection {
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
    public void fetchFromDataStorage(DataStorage dataStorage) {
    
    }
    
    @Override
    public void addToCollection(IEntity entity) {
    
    }
    
    @Override
    public IEntity getById(int venueId) throws EntityNotFoundException {
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
}
