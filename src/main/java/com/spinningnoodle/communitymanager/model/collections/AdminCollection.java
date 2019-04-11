package com.spinningnoodle.communitymanager.model.collections;
/*
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
import com.spinningnoodle.communitymanager.model.entities.Admin;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
@Repository(value = "admins")
public class AdminCollection extends EntityCollection<Admin> {

    public AdminCollection() {
        super("admins");
    }

    /**
     * @param dataStorage the data storage to use as a database
     */
    public AdminCollection(DataStorage dataStorage) {
        super(dataStorage, "admins");
    }

    /**
     * Gets all <T> from a DataStorage.
     */
    @Override
    public AdminCollection fetchFromDataStorage() {

        AdminCollection adminCollection = null;

        try {
            adminCollection = new AdminCollection(this.getDataStorage());

            for(Map<String, String> adminFields : getDataStorage().readAll(getTableName())) {
                Admin admin = new Admin();

                admin.build(adminFields);
                adminCollection.addToCollection(admin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adminCollection;
    }

    /**
     * Notifies this class a Observable it isa watching has an update
     *
     * @param observable the Observable who's state has updated
     */
    @Override
    public void update(Admin observable) {

    }
}
