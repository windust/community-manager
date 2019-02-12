package com.spinningnoodle.communitymanager.model.collections;

public abstract class TokenCollection implements ICollection{
    
    public boolean validToken(String token) {
        return false;
    }
}
