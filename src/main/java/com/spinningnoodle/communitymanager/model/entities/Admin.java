package com.spinningnoodle.communitymanager.model.entities;
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

import com.spinningnoodle.communitymanager.exceptions.AttributeException;
import java.util.Map;

/**
 * Admin Entity
 *
 * Admin stores all information associated with an admin in the application
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class Admin extends Entity {
    private String email;
    private String name;

    public Admin(){}

    public Admin(int primaryKey) {
        super(primaryKey);
    }

    /**
     * Create a new Admin with all fields
     *
     * @param primaryKey The key of the venue stored in the database
     * @param email The authorized email address & login of the admin
     * @param name The name of the admin
     */
    public Admin(int primaryKey, String email, String name) {
        super(primaryKey);
        this.email = email;
        this.name = name;
    }

    /**
     * Factory method to make a Entity based on a map of fields mapped to the Entity's attributes
     *
     * @param fields A Key-Value map of Entity attributes where the key is an identifier for the
     * attribute and the value is the value the attribute takes on
     * @return A new entity with the values associated identified by the map
     * @throws AttributeException An exception raised by invalid values for the specified key
     */
    @Override
    public Entity build(Map<String, String> fields) throws AttributeException {
        this.setPrimaryKey(Integer.parseInt(fields.getOrDefault("primaryKey", "-1")));
        this.setEmail(fields.getOrDefault("email", null));
        this.setName(fields.getOrDefault("name", null));

        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Admin{" +
            ", primaryKey=" + this.getPrimaryKey() +
            ", email='" + email + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
