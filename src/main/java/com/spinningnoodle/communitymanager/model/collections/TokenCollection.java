package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.model.entities.TokenEntity;

public abstract class TokenCollection extends Collection<TokenEntity> {

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
