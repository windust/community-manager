package com.spinningnoodle.communitymanager.modeltest.entities.fakes;

import com.spinningnoodle.communitymanager.model.entities.IEntity;
import com.spinningnoodle.communitymanager.model.entities.TokenEntity;
import java.util.Map;

/**
 * Class used strictly for testing TokenEntity abstract class
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class DummyToken extends TokenEntity {
    @Override
    public IEntity build(Map<String, String> fields){
        DummyToken temp = new DummyToken();
        
        temp.setName(fields.get("name"));
        temp.setToken(fields.get("token"));
        
        return temp;
    }
    
}
