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
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.observer.Observable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Represents a entity of the data-model
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class Entity extends Observable {
	private int primaryKey;
	
	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/**
	 * Entity default constructor
	 */
	public Entity() {}

	/**
	 * Entity constructor that takes in an int parameter
	 * primaryKey.
	 * @param primaryKey int
	 */
	public Entity(int primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * Factory method to make a Entity based on a map of fields mapped to the Entity's attributes
	 *
	 * @param fields A Key-Value map of Entity attributes where the key is an identifier for the
	 * attribute and the value is the value the attribute takes on
	 * @return A new entity with the values associated identified by the map
	 * @throws AttributeException An exception raised by invalid values for the specified key
	 */
	public abstract Entity build(Map<String, String> fields) throws AttributeException;

	/**
	 * Getter for primaryKey.
	 * @return int primaryKey
	 */
	public int getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Setter for primaryKey.
	 * @param primaryKey int
	 */
	public void setPrimaryKey(int primaryKey) {
		if(primaryKey < -1 || primaryKey == 0) {
			throw new UnexpectedPrimaryKeyException();
		}
		this.primaryKey = primaryKey;
	}

	/**
	 * convertDate method splits the date on / and
	 * parses the String to an int and returns the correct
	 * LocalDate format.
	 * @param date
	 * @return
	 */
	public static LocalDate convertDate(String date){
		int year, month, day;
		
		if(date != null && !date.equals("")){
			if(date.contains("/")){
				String[] dateComponents = date.split("/");
				
				year = Integer.parseInt(dateComponents[2]);
				month = Integer.parseInt(dateComponents[0]);
				day = Integer.parseInt(dateComponents[1]);
			}
			else{
				String[] dateComponents = date.split("-");
				
				year = Integer.parseInt(dateComponents[0]);
				month = Integer.parseInt(dateComponents[1]);
				day = Integer.parseInt(dateComponents[2]);
			}
			
			return LocalDate.of(year, month, day);
		}
		return null;
	}
}
