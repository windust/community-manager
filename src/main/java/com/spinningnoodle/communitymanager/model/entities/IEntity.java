package com.spinningnoodle.communitymanager.model.entities;

import java.util.Map;

public interface IEntity {
	public IEntity build(Map<String, String> fields);

}
