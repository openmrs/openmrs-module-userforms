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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.User;

/**
 * Please note that a corresponding table schema must be created in
 * liquibase.xml.
 */
// Uncomment 2 lines below if you want to make the UserForm class persistable,
// see also UserFormsDaoTest and liquibase.xml
@Entity(name = "userforms.UserForm")
@Table(name = "userforms_item")
public class UserForm extends BaseOpenmrsData implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4116259395297170811L;

	@Id
	@GeneratedValue
	@Column(name = "user_form_id")
	private Integer userFormId;

	@ManyToOne
	@JoinColumn(name = "encounter_type")
	private EncounterType encounterType;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@Basic
	@Column(name = "form_datetime")
	private Date formDatetime;

	private Set<UserObs> userObs;

	@Basic
	@Column(name = "description", length = 255)
	private String description;

	/**
	 * @return the userFormId
	 */
	public Integer getUserFormId() {
		return userFormId;
	}

	/**
	 * @param userFormId
	 *            the userFormId to set
	 */
	public void setUserFormId(Integer userFormId) {
		this.userFormId = userFormId;
	}

	@Override
	public Integer getId() {
		return getUserFormId();
	}

	@Override
	public void setId(Integer id) {
		setUserFormId(id);
	}

	/**
	 * @return the encounterType
	 */
	public EncounterType getEncounterType() {
		return encounterType;
	}

	/**
	 * @param encounterType
	 *            the encounterType to set
	 */
	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the formDatetime
	 */
	public Date getFormDatetime() {
		return formDatetime;
	}

	/**
	 * @param formDatetime
	 *            the formDatetime to set
	 */
	public void setFormDatetime(Date formDatetime) {
		this.formDatetime = formDatetime;
	}

	/**
	 * Returns all UserObs where UserObs.userFormId = UserForm.userFormId In
	 * practice
	 * 
	 * @param includeVoided
	 *            specifies whether or not to include voided UserObs
	 * @return Returns the all UserObs.
	 * @should not return null with null obs set
	 */
	public Set<UserObs> getAllObs(boolean includeVoided) {
		if (includeVoided && userObs != null)
			return userObs;

		Set<UserObs> ret = new HashSet<UserObs>();
		if (this.userObs != null) {
			for (UserObs o : this.userObs) {
				if (includeVoided)
					ret.add(o);
				else if (!o.isVoided())
					ret.add(o);
			}
		}
		return ret;
	}

	/**
	 * Convenience method to call {@link #getAllObs(boolean)} with a false
	 * parameter
	 * 
	 * @return all non-voided obs
	 * @should not get voided obs
	 */
	public Set<UserObs> getAllObs() {
		return getAllObs(false);
	}

	/**
	 * @param userObs
	 */
	public void setUserObs(Set<UserObs> userObs) {
		this.userObs = userObs;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
