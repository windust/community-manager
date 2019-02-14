package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.model.entities.TokenEntity;
import com.spinningnoodle.communitymanager.model.observer.IObserver;
import com.spinningnoodle.communitymanager.model.observer.Observable;

public abstract class TokenCollection extends ICollection<TokenEntity> {

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
        updateToken(this.dataStorage, observable.getPrimaryKey(),observable.getToken());
    }
}
