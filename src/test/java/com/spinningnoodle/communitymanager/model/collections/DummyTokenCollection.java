package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.collections.TokenCollection;
import com.spinningnoodle.communitymanager.model.entities.IEntity;
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
