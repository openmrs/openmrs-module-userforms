/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.userforms;

import org.springframework.stereotype.Component;

/**
 * Contains module's config.
 */
@Component("userforms.UserFormsConfig")
public class UserFormsConfig {

	public final static String ADD_USER_FORMS = "Add User Forms";
	public final static String EDIT_USER_FORMS = "Edit User Forms";
	public final static String DELETE_USER_FORMS = "Delete User Forms";
	public final static String VIEW_USER_FORMS = "View User Forms";
	public final static String ADD_USER_OBS = "Add User Obs";
	public final static String EDIT_USER_OBS = "Edit User Obs";
	public final static String DELETE_USER_OBS = "Delete User Obs";
	public final static String VIEW_USER_OBS = "View User Obs";
}
