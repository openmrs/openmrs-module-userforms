/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.userforms.dao;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.userforms.UserForm;
import org.openmrs.module.userforms.api.dao.UserFormsDao;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class UserFormsDaoTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	UserFormsDao dao;
	
	@Autowired
	UserService userService;
	
	@Test
	@Ignore("Unignore if you want to make the UserForm class persistable, see also UserForm and liquibase.xml")
	public void saveItem_shouldSaveAllPropertiesInDb() {
		//Given
		UserForm item = new UserForm();
		item.setDescription("some description");
		item.setUser(userService.getUser(1));
		
		//When
		dao.saveItem(item);
		
		//Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
		Context.flushSession();
		Context.clearSession();
		
		//Then
		UserForm savedItem = dao.getItemByUuid(item.getUuid());
		
		assertThat(savedItem, hasProperty("uuid", is(item.getUuid())));
		assertThat(savedItem, hasProperty("user", is(item.getUser())));
		assertThat(savedItem, hasProperty("description", is(item.getDescription())));
	}
}
